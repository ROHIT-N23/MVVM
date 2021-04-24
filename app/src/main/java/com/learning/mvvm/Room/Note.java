package com.learning.mvvm.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NOTE")
public class Note {

    public static final String Success = "Success";

    private String title;

    private String description;

    private int priority;

    // Automatically generates iterative ID's //
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public int getId() {
        return id;
    }
}
