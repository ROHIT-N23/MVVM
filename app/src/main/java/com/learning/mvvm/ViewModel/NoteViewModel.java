package com.learning.mvvm.ViewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.learning.mvvm.Repository.NoteRepository;
import com.learning.mvvm.Room.Note;

import java.util.List;

public class NoteViewModel extends ViewModel {


    private NoteRepository repository;
    private LiveData<List<Note>> allnotes;

    public NoteViewModel(Application application) {


        repository = new NoteRepository(application);

        // initializing allNotes LiveData to saved Notes //
        allnotes = repository.getAllNotes();
    }



    public void insert(Note note){
        repository.insert(note);
    }

    public void update(Note note){
        repository.update(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allnotes;
    }
}
