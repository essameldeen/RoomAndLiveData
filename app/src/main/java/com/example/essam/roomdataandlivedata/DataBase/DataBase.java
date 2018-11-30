package com.example.essam.roomdataandlivedata.DataBase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.essam.roomdataandlivedata.Model.Note;
import com.example.essam.roomdataandlivedata.Model.NoteDao;


@Database(entities = {Note.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    private static DataBase instance;

    public static synchronized DataBase getInstaance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , DataBase.class, "Note_dataBase")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;
    }


    // init the dao
    public abstract NoteDao noteDao();
}
