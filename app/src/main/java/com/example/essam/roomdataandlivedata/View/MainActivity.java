package com.example.essam.roomdataandlivedata.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.essam.roomdataandlivedata.Adapter.NoteAdapter;
import com.example.essam.roomdataandlivedata.Interfaces.OnItemClick;
import com.example.essam.roomdataandlivedata.Model.Note;
import com.example.essam.roomdataandlivedata.R;
import com.example.essam.roomdataandlivedata.ViewModel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnItemClick {
    private NoteViewModel noteViewModel;
    private List<Note> allNote;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onResume() {
        super.onResume();
        noteViewModel.getAllNote();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        allNote = new ArrayList<>();

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNote().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                allNote.clear();
                allNote = notes;
                setupAdapter();
            }
        });
        initView();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));

            }
        }).attachToRecyclerView(recyclerView);

    }

    private void setupAdapter() {
        adapter = new NoteAdapter(this);
        adapter.submitList(allNote);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycle_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton = findViewById(R.id.add_note);
        floatingActionButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_note) {
            goToAddActivity();
        }
    }

    private void goToAddActivity() {
        Intent intent = new Intent(this, NewNode.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            int priority = data.getIntExtra("priority", -1);

            addNode(title, description, priority);

        } else if (requestCode == 2 && resultCode == 1) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            int priority = data.getIntExtra("priority", -1);
            int id = data.getIntExtra("id", -1);
            if (id != -1)
                updateNode(title, description, priority, id);
            else {
                Toast.makeText(this, "Node Cant  Updated ", Toast.LENGTH_SHORT).show();
                return;
            }

        }
    }

    private void updateNode(String title, String description, int priority, int id) {
        Note note = new Note(title, description, priority);
        note.setId(id);
        noteViewModel.update(note);
        Toast.makeText(this, "Node Updated ", Toast.LENGTH_SHORT).show();
    }

    private void addNode(String title, String description, int priority) {
        Note note = new Note(title, description, priority);
        noteViewModel.insert(note);
        Toast.makeText(this, "Node Save ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Note noteCliked = adapter.getNote(position);
        goToEdit(noteCliked);

    }

    private void goToEdit(Note noteCliked) {
        Intent intent = new Intent(this, NewNode.class);
        intent.putExtra("id", noteCliked.getId());
        intent.putExtra("title", noteCliked.getTitle());
        intent.putExtra("description", noteCliked.getDescription());
        intent.putExtra("priority", noteCliked.getPriority());
        startActivityForResult(intent, 2);
    }
}
