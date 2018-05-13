package com.example.android.voicenotes;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends WearableActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    displaySpeechInputScreen();
                }
                else {
                    Note note = (Note) parent.getItemAtPosition(position);

                    Intent intent = new Intent(getApplicationContext(), DeleteActivity.class);
                    intent.putExtra("id", note.getId());

                    startActivity(intent);
                }
            }
        });

        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        ArrayList<Note> notes = NotesHelper.getAllNotes(this);

        notes.add(0, new Note("", "Fuck you!"));

        listView.setAdapter(new ListViewAdapter(this, 0, notes));

    }

    public void displaySpeechInputScreen() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What is the title?");
        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1001 && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String message = results.get(0);

            Note note = new Note(null, message);

            NotesHelper.saveNote(note, this);
            NotesHelper.displayConfirmation("Notes saved!", this);

            updateUI();
        }
    }
}
