package com.finalproject.cs4518.freebees.database;

import android.util.Log;

import com.finalproject.cs4518.freebees.Event;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * Created by Kyle on 12/10/2017.
 */

public class DatabaseController {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DatabaseController(){
        Date testDate = new Date(2015, 7, 23, 4, 05);
        Event event = new Event(1, "Ed's lumber fiesta", "Ed's lumber yard", "Come to my lumber yard", testDate, testDate, "123 Lumber Way", new LatLng(0.0, 0.0));
        database.child("events").setValue(event);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {   // This method is called once with the initial value and again whenever data at this location is updated.
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    System.out.println("###" + messageSnapshot.getValue());

                    Event retrievedEvent = new Event();
                    retrievedEvent.setEventID(((Long) messageSnapshot.child("eventID").getValue()).intValue());
                    retrievedEvent.setTitle((String) messageSnapshot.child("title").getValue());
                    retrievedEvent.setOrganization((String) messageSnapshot.child("organization").getValue());
                    retrievedEvent.setDescription((String) messageSnapshot.child("description").getValue());

                    Log.d("events", "### event: " + retrievedEvent.getTitle());
                    Log.d("events", "### event: " + retrievedEvent.getEventID());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("events", "Failed to read value.", error.toException());
            }
        });
    }

}
