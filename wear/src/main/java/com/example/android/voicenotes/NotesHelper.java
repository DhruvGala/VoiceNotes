package com.example.android.voicenotes;
/**
 * created by Dhruv on 04/24/2018
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Map;

public class NotesHelper {

    /**
     * the method is used to save a particular note to the database
     * might need to implement AsyncTasks to avoid delays in the UI
     *
     * @param note
     * @param context
     * @return
     */
    private static String saveNote(Note note, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String id = String.valueOf(System.currentTimeMillis());
        editor.putString(id, note.getTitle());

        editor.commit();

        return id;
    }


    /**
     * this method gets all the notes that are stored in the database
     *
     * @param context
     * @return
     */
    private static ArrayList<Note> getAllNotes(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        ArrayList<Note> notes = new ArrayList<>();

        Map<String, ?> key = sharedPreferences.getAll();

        for(Map.Entry<String, ?> entry : key.entrySet()) {
            String saveData = (String) entry.getValue();

            if (saveData != null) {
                Note note = new Note(entry.getKey(), saveData);

                notes.add(note);
            }
        }

        return notes;
    }

    /**
     * Delete a particular note of the given id from the database
     * 
     * @param id
     * @param context
     */
    public static void removeNote(String id, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(id);

        editor.commit();
    }
}
