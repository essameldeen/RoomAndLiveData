package com.example.essam.roomdataandlivedata.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.essam.roomdataandlivedata.DataBase.DataBase;
import com.example.essam.roomdataandlivedata.Model.Note;
import com.example.essam.roomdataandlivedata.Model.NoteDao;

import java.util.List;

public class NoteRepo {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNote;

    public NoteRepo(Application application) {
        DataBase db = DataBase.getInstaance(application);
        noteDao = db.noteDao();
        allNote = noteDao.getAllNote();
    }

   public void insert(Note note) {
        new InsertNodeAsy(noteDao).execute(note);

    }

    public  void update(Note note) {
        new UpdateNodeAsy(noteDao).execute(note);

    }

    public  void deleteNote(Note note) {
        new DeleteNodeAsy(noteDao).execute(note);
    }

    public  void deleteAll() {
        new DeleteAllNodeAsy(noteDao).execute();

    }

    public  LiveData<List<Note>> getAllNote() {
        return allNote;
    }

    private static class InsertNodeAsy extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNodeAsy(NoteDao note) {
            this.noteDao = note;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.inser(notes[0]);
            return null;
        }
    }

    private static class UpdateNodeAsy extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNodeAsy(NoteDao note) {
            this.noteDao = note;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNodeAsy extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNodeAsy(NoteDao note) {
            this.noteDao = note;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteNote(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNodeAsy extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNodeAsy(NoteDao note) {
            this.noteDao = note;
        }

        @Override
        protected Void doInBackground(Void... Void) {
            noteDao.deleteAllNote();
            return null;
        }
    }

}
