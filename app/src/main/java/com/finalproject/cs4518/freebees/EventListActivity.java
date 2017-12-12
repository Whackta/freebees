package com.finalproject.cs4518.freebees;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventListActivity extends AppCompatActivity {

    private RecyclerView mEventRecyclerView;
    private eventAdapter mAdapter;
    private List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EventCreationActivity.class);
                startActivity(intent);
            }
        });

        events = new ArrayList<>();
        mEventRecyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                events.clear();

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

                    events.add(retrievedEvent);
                }

                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {  // Failed to read value
                System.out.println("### FAILED TO READ VALUE " + databaseError.toException());
            }
        });

        System.out.println("### FINISHED CREATE LIST ACTIVITY");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Updates the UI, mostly the recyclerview
     */
    private void updateUI(){
        if(mAdapter == null){
            mAdapter = new eventAdapter(events);
            mEventRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setEvents(events);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class eventHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Event mEvent;
        private TextView mEventNameTextView;
        private TextView mEventLocationTextView;
        private TextView mEventDateTextView;
        private TextView mEventTimeTextView;

        public eventHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_event, parent, false));
            itemView.setOnClickListener(this);
            mEventNameTextView = (TextView) itemView.findViewById(R.id.eventName);
            mEventLocationTextView = (TextView) itemView.findViewById(R.id.eventLocation);
            mEventDateTextView = (TextView) itemView.findViewById(R.id.eventDate);
            mEventTimeTextView = (TextView) itemView.findViewById(R.id.eventTime);
        }

        public void bind(Event theEvent){
            mEvent = theEvent;
            mEventNameTextView.setText(mEvent.getTitle());
            mEventLocationTextView.setText(mEvent.getLocation());

            // format the date
            //SimpleDateFormat sdfD = new SimpleDateFormat("MM-dd");
            //String dateStr = sdfD.format(mEvent.getStartDate());
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(mEvent.getStartDateMillis()); // set the calendar to whatever the starting date is
            String formatMonth = c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            mEventDateTextView.setText(formatMonth + " " + day);

            // format the time
            //SimpleDateFormat sdfT = new SimpleDateFormat("HH:mm");
            //String timeStartStr = sdfT.format(mEvent.getStartDate());
            //String timeEndStr = sdfT.format(mEvent.getEndDate());
            //String timeDuration = timeStartStr + " - " + timeEndStr;
            int startHour = c.get(Calendar.HOUR);
            int startMinute = c.get(Calendar.MINUTE);
            c.setTimeInMillis(mEvent.getEndDateMillis());
            int endHour = c.get(Calendar.HOUR);
            int endMinute = c.get(Calendar.MINUTE);
            mEventTimeTextView.setText(startHour + ":" + startMinute + " to " + endHour + ":" + endMinute);
        }

        @Override
        public void onClick(View view){
            Intent intent = new Intent(itemView.getContext(), EventDetailActivity.class);
            intent.putExtra("Event", mEvent);
            startActivity(intent);
        }
    }

    private class eventAdapter extends RecyclerView.Adapter<eventHolder>{
        private List<Event> mEvents;

        public eventAdapter(List<Event> events){
            mEvents = events;
        }

        @Override
        public eventHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new eventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(eventHolder holder, int position){
            Event event = mEvents.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount(){
            return mEvents.size();
        }

        public void setEvents(List<Event> events){
            mEvents = events;
        }
    }
}
