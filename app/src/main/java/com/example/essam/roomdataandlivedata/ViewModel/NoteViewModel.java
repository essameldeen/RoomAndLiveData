package com.example.essam.roomdataandlivedata.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.example.essam.roomdataandlivedata.Model.Note;
import com.example.essam.roomdataandlivedata.Repository.NoteRepo;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepo noteRepo ;
    private LiveData<List<Note>> allNote;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepo = new NoteRepo(application);
        allNote=noteRepo.getAllNote();
    }

    public void  insert(Note note){
        noteRepo.insert(note);
    }
    public void  update(Note note){
        noteRepo.update(note);
    }
   public void  delete(Note note){
        noteRepo.deleteNote(note);
    }
    public void  deleteAll( ){
       noteRepo.deleteAll();
    }

   public LiveData<List<Note>> getAllNote(){
        return allNote;
   }
}
