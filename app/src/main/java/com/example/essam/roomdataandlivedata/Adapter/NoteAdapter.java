package com.example.essam.roomdataandlivedata.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.essam.roomdataandlivedata.Interfaces.OnItemClick;
import com.example.essam.roomdataandlivedata.Model.Note;
import com.example.essam.roomdataandlivedata.R;
public class NoteAdapter extends ListAdapter<Note, NoteAdapter.ViewHolder> {

    private OnItemClick listener;

    public NoteAdapter(OnItemClick listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            if (oldItem.getId() == newItem.getId())
                return true;
            else return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            if (oldItem.getId() == newItem.getId() && oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDescription().equals(newItem.getDescription()) && oldItem.getPriority() == newItem.getPriority())
                return true;
            else
                return false;
        }
    };


    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder viewHolder, int i) {
        Note item = getItem(i);
        viewHolder.title.setText(item.getTitle());
        viewHolder.priority.setText(String.valueOf(item.getPriority()));
        viewHolder.description.setText(item.getDescription());

    }


    public Note getNote(int adapterPosition) {
        return getItem(adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView priority;
        TextView description;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            priority = itemView.findViewById(R.id.priority);
            description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });

        }
    }
}
