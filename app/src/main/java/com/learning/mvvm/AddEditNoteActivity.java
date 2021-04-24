package com.learning.mvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    // Keys for intent //
    public static final String EXTRA_TITLE = "com.learning.mvvm.title";
    public static final String EXTRA_DESCRIPTION = "com.learning.mvvm.description";
    public static final String EXTRA_PRIORITY = "com.learning.mvvm.priority";
    public static final String EXTRA_ID = "com.learning.mvvm.ID";

    private EditText edittext_title, edittext_description;
    private NumberPicker numberPicker_priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        edittext_title = findViewById(R.id.edit_text_title);
        edittext_description = findViewById(R.id.edit_text_description);
        numberPicker_priority = findViewById(R.id.number_picker);

        //Setting Min and Max Values For NumberPicker //
        numberPicker_priority.setMinValue(1);
        numberPicker_priority.setMaxValue(10);

        // Setting Activity Title As Per MainActivity Call //
        Intent intent = getIntent();

        // Intent Contains A Extra Argument ID Then Set title "Edit Note" //
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            edittext_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            edittext_title.setText(intent.getStringExtra(EXTRA_TITLE));
            numberPicker_priority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));

        // Else, Just "Add Note"
        }else {
            setTitle("Add Note");
        }


    }

    // If Floating Action Button Clicked //
    public void saveEditNote(View view) {


        String title = edittext_title.getText().toString();
        String description = edittext_description.getText().toString();
        int priority = numberPicker_priority.getValue();

        // If title Or Description Is Empty If It is return (Further Code Will Not Execute) //
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(AddEditNoteActivity.this, "Please Enter Title And Description First", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sending Intent With User Entered Values //w
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_TITLE, title);
        resultIntent.putExtra(EXTRA_DESCRIPTION, description);
        resultIntent.putExtra(EXTRA_PRIORITY, priority);

        //Sending id To Update Note
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            resultIntent.putExtra(EXTRA_ID, id);
        }

        // Setting Result Ok
        setResult(RESULT_OK, resultIntent);
        finish();

    }



}