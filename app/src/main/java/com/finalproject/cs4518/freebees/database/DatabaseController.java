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
     * Initialize Firebase listeners
     */
    protected DatabaseController() {
//        Date testDate = new Date(2015, 7, 23, 4, 05);
//        final Event event1 = new Event("Ed's lumber fiesta", "Ed's lumber yard", "Come to my lumber yard", testDate, testDate, new LatLng(-22.3657, 123.432));
//        final Event event2 = new Event("Jim's hat fiesta", "Jim's hat yard", "Come to my hat yard", testDate, testDate, new LatLng(42.3657, 5.7888));
//        final Event event3 = new Event("Don's dog fiesta", "Don's dog yard", "Come to my dog yard", testDate, testDate, new LatLng(0.5432, -54.22));
//        writeEvent(event1);
//        writeEvent(event2);
//        writeEvent(event3);
    }

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
