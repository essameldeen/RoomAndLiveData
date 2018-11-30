package com.example.essam.roomdataandlivedata.View;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.essam.roomdataandlivedata.R;

public class NewNode extends AppCompatActivity {

    private EditText title;
    private EditText descriptoin;
    private NumberPicker proi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_node);
        initView();

        proi.setMinValue(1);
        proi.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.exit_icon);

        if (getIntent().hasExtra("id")) {
            setTitle("Update Note");
            title.setText(getIntent().getStringExtra("title"));
            descriptoin.setText(getIntent().getStringExtra("description"));
            proi.setValue(getIntent().getIntExtra("priority", 1));
        } else
            setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String valueTitle = title.getText().toString();
        String valueDescription = descriptoin.getText().toString();
        int priority = proi.getValue();

        if (TextUtils.isEmpty(valueTitle)) {
            Toast.makeText(this, "Please Enter The Title", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(valueDescription)) {
            Toast.makeText(this, "Please Enter The Description", Toast.LENGTH_SHORT).show();
        } else {
            gotToMainActivityForData(valueTitle, valueDescription, priority);
        }
    }

    private void gotToMainActivityForData(String valueTitle, String valueDescription, int priority) {
        Intent intent = new Intent();
        intent.putExtra("title", valueTitle);
        intent.putExtra("description", valueDescription);
        intent.putExtra("priority", priority);

        int id = getIntent().getIntExtra("id",-1);
        if(id!=-1){
            intent.putExtra("id",id);
        }
        setResult(1, intent);
        finish();
    }

    private void initView() {
        title = findViewById(R.id.title);
        descriptoin = findViewById(R.id.description);
        proi = findViewById(R.id.priority);
    }
}
