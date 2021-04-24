package com.learning.mvvm.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDataAccessObject {

    // Assigning Operations To Methods//

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM NOTE")
    void deleteAll();

    @Query("SELECT * FROM NOTE ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();
}
