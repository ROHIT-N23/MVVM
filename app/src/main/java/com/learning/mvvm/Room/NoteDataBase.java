package com.learning.mvvm.Room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance;

    public abstract NoteDataAccessObject noteDataAccessObject();

    // We Cannot Get Instance Of Abstract Class So We Call Room.databaseBuilder//
    public static synchronized NoteDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDataBase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Populating Some Notes When The Data Base Is First Time Created //
            new PopulateNotes(instance).execute();
            Log.d("cif", "RoomDatabase: Done !");
        }
    };

    private static class PopulateNotes extends AsyncTask<Void, Void, Void>{

        private NoteDataAccessObject noteDataAccessObject;

        public PopulateNotes(NoteDataBase db) {
            noteDataAccessObject = db.noteDataAccessObject();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDataAccessObject.insert(new Note("Title 1", "Description 1", 1));
            noteDataAccessObject.insert(new Note("Title 2", "Description 3", 2));
            noteDataAccessObject.insert(new Note("Title 3", "Description 3", 3));
            Log.d("cif", "doInBackground: Done !");
            return null;
        }
    }

}
