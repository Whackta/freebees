package com.finalproject.cs4518.freebees.database;

import com.finalproject.cs4518.freebees.Event;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Kyle on 12/10/2017.
 */
public class DatabaseController {

    private static DatabaseController dbControllerInstance = null;  // Singleton instance
    private DatabaseReference database;                             // Reference to the Firebase database
    private List<Event> eventList;                                  // List of Event objects obtained from Firebase

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
        database = FirebaseDatabase.getInstance().getReference();
        eventList = new ArrayList(8);

        Date testDate = new Date(2015, 7, 23, 4, 05);
        final Event event1 = new Event("Ed's lumber fiesta", "Ed's lumber yard", "Come to my lumber yard", testDate, testDate, new LatLng(-22.3657, 123.432));
        final Event event2 = new Event("Jim's hat fiesta", "Jim's hat yard", "Come to my hat yard", testDate, testDate, new LatLng(42.3657, 5.7888));
        final Event event3 = new Event("Don's dog fiesta", "Don's dog yard", "Come to my dog yard", testDate, testDate, new LatLng(0.5432, -54.22));

        writeEvent(event1);
        writeEvent(event2);
        writeEvent(event3);

        /**
         * Keep listening to Firebase "events" tag
         */
        FirebaseDatabase.getInstance().getReference("events").addValueEventListener(new ValueEventListener() {
            /**
             * Performs database read on initialization and continuously updates the eventList with any changes in Firebase
             * @param dataSnapshot Data received from Firebase
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Parse read data for Event objects and store in List
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    System.out.println("### " + snapshot.getValue());

                    Event retrievedEvent = new Event();
                    Double lat = snapshot.child("latLng").child("latitude").getValue(Double.class);
                    Double lon = snapshot.child("latLng").child("longitude").getValue(Double.class);
                    retrievedEvent.setEventID((String) snapshot.child("eventID").getValue());
                    retrievedEvent.setTitle((String) snapshot.child("title").getValue());
                    retrievedEvent.setOrganization((String) snapshot.child("organization").getValue());
                    retrievedEvent.setDescription((String) snapshot.child("description").getValue());
                    retrievedEvent.setStartDateMillis(((Long) snapshot.child("startDateMillis").getValue()).intValue());
                    retrievedEvent.setEndDateMillis(((Long) snapshot.child("endDateMillis").getValue()).intValue());
                    retrievedEvent.setLatLng(new LatLng(lat, lon));

                    eventList.add(retrievedEvent);
                }

                // Print out Event list titles
                for (int i = 0; i < eventList.size(); i++) {
                    System.out.println("### " + eventList.get(i).getLatLng().toString());
                }

            }

            // Failed to read value
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("### FAILED TO READ VALUE " + databaseError.toException());
            }
        });
    }

    /**
     * @param id eventID of desired event
     * @return Event with the matching eventID if exists, else null
     */
    public Event getEventByID(String id){
        for(int i = 0; i < eventList.size(); i++){
            if(eventList.get(i).getEventID().compareTo(id) == 0){
                return eventList.get(i);
            }
        }
        return null;
    }

    /**
     * @return Most up-to-date list of Events from Firebase
     */
    public List<Event> getEventList(){
        return this.eventList;
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
