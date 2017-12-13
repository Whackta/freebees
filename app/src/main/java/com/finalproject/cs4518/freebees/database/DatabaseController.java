package com.finalproject.cs4518.freebees.database;

import com.finalproject.cs4518.freebees.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Kyle on 12/10/2017.
 */
public class DatabaseController {

    private static DatabaseController dbControllerInstance = null;                          // Singleton instance
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();     // Reference to the Firebase database

    /**
     * @return Singleton instance of the DatabaseController
     */
    public static DatabaseController getInstance() {
        if(dbControllerInstance == null) {
            dbControllerInstance = new DatabaseController();
        }
        return dbControllerInstance;
    }

    /**
     * Initialize DatabaseController Singleton
     */
    protected DatabaseController() {}

    /**
     * Writes an Event object to Firebase under the "events" tag after generating a unique ID
     * @param event Event object to be saved to Firebase
     */
    public void writeEvent(Event event){
        String key = database.child("events").push().getKey();
        event.setEventID(key);
        database.child("events").child(key).setValue(event);
    }

}
