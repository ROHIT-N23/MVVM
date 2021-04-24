package com.learning.mvvm.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.learning.mvvm.Room.Note;
import com.learning.mvvm.Room.NoteDataAccessObject;
import com.learning.mvvm.Room.NoteDataBase;

import java.util.List;

public class NoteRepository {
    private NoteDataAccessObject noteDataAccessObject;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        //Getting Instance of NoteDataBase For Further Operations //
        NoteDataBase dataBase = NoteDataBase.getInstance(application);


        noteDataAccessObject = dataBase.noteDataAccessObject();

        // initializing Notes Saved in DataBase to allNotes //
        allNotes = noteDataAccessObject.getAllNotes();
    }

    public void insert(Note note){
        new insertAsyncTask(noteDataAccessObject).execute(note);
    }

    public void update(Note note){
        new updateAsyncTask(noteDataAccessObject).execute(note);

    }

    public void delete(Note note){
        new deleteAsyncTask(noteDataAccessObject).execute(note);

    }

    public void deleteAll(){
        new deleteAllAsyncTask(noteDataAccessObject).execute();

    }

    public LiveData<List<Note>> getAllNotes(){
        Log.d(Note.Success, "getAllNotes: Success");
        return allNotes;
    }


            /*

                Performing AsyncTask For All Operations

            */

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDataAccessObject noteDataAccessObject;

        public insertAsyncTask(NoteDataAccessObject noteDataAccessObject) {
            this.noteDataAccessObject = noteDataAccessObject;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDataAccessObject.insert(notes[0]);
            Log.d(Note.Success, "Insert: Success");
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDataAccessObject noteDataAccessObject;

        public deleteAllAsyncTask(NoteDataAccessObject noteDataAccessObject) {
            this.noteDataAccessObject = noteDataAccessObject;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDataAccessObject.deleteAll();
            Log.d(Note.Success, "deleteAll: Success");
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDataAccessObject noteDataAccessObject;

        public updateAsyncTask(NoteDataAccessObject noteDataAccessObject) {
            this.noteDataAccessObject = noteDataAccessObject;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDataAccessObject.update(notes[0]);
            Log.d(Note.Success, "Update: Success");
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDataAccessObject noteDataAccessObject;

        public deleteAsyncTask(NoteDataAccessObject noteDataAccessObject) {
            this.noteDataAccessObject = noteDataAccessObject;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDataAccessObject.delete(notes[0]);
            Log.d(Note.Success, "Delete: Success");
            return null;
        }
    }
}
