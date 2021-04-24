package com.learning.mvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.learning.mvvm.RecyclerViewAdapter.NoteAdapter;
import com.learning.mvvm.Room.Note;
import com.learning.mvvm.ViewModel.NoteViewModel;
import com.learning.mvvm.ViewModel.NoteViewModelFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    // Intent Request codes//
    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;

    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting Up RecyclerView//
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        //Setting Adapter//
        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);


        // Deleting Notes By Swiping theme //
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        //Getting Note Object To Update Note
        noteAdapter.setOnNoteClick(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {

                // Starting Activity For Result And Getting Final Value In onActivityResult //
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });


        // Observing Data Change In Room//
        noteViewModel = new ViewModelProvider(this, new NoteViewModelFactory(this.getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setAllNotes(notes);
            }
        });
    }


    // Opening New Activity To Save Note//
    public void addNote(View view) {
        Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
        startActivityForResult(intent, ADD_REQUEST_CODE);
    }



    //Getting Result From AddEditActivity//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == ADD_REQUEST_CODE){
            if (resultCode == RESULT_OK || data != null){

                // Getting Value From Another Activity
                noteViewModel.insert(new Note(
                        data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE),
                        data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION),
                        data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1))
                );
                Toast.makeText(MainActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();

            // If We Come Back From Activity Without Saving //
            }else {
                Toast.makeText(MainActivity.this, "Note Not Saved", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == EDIT_REQUEST_CODE){
            if (resultCode == RESULT_OK || data != null){

                // Getting Value From Another Activity

                Note note = new Note(
                        data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE),
                        data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION),
                        data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)
                );
                int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
                note.setId(id);

                if (id == -1) {
                    Toast.makeText(MainActivity.this, "Note Could Not Be Updated", Toast.LENGTH_SHORT).show();
                    return;
                }
                noteViewModel.update(note);
                Toast.makeText(MainActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();

                // If We Come Back From Activity Without Saving //
            }else {
                Toast.makeText(MainActivity.this, "Note Not Updated", Toast.LENGTH_SHORT).show();
            }
        // If The Code Is Not ADD_REQUEST_CODE or _REQUEST_CODE //
        }else {
            Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

    }


    // Inflating menu //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_deleteall, menu);
        return true;
    }


    // Handling On Click On Menu //
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteallnotes) {
            noteViewModel.deleteAll();
            Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}