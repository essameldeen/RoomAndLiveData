package com.example.essam.roomdataandlivedata.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void inser(Note note);

    @Update
    void update(Note update);

    @Delete
    void deleteNote(Note note);

    @Query("select * from note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNote();

    @Query("delete from note_table")
    void deleteAllNote();

}
