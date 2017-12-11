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

import com.finalproject.cs4518.freebees.database.DatabaseController;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private RecyclerView mEventRecyclerView;
    private eventAdapter mAdapter;

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

        mEventRecyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();

        DatabaseController.getInstance();
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
        //TODO: where do we get the list of events?
        List<Event> events = new ArrayList<Event>();
        //TODO: make the event list not dummy
        Date testDate = new Date(2015, 7, 23, 4, 05);
        events.add(new Event(1, "Ed's lumber fiesta", "Ed's lumber yard", "Come to my lumber yard", testDate, testDate, new LatLng(0.0, 0.0)));
        events.add(new Event(2, "Jim's hat fiesta", "Jim's hat yard", "Come to my hat yard", testDate, testDate, new LatLng(0.0, 0.0)));
        events.add(new Event(3, "Don's dog fiesta", "Don's dog yard", "Come to my dog yard", testDate, testDate, new LatLng(0.0, 0.0)));

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
            // TODO FIX THIS getLocation() call
            //mEventLocationTextView.setText(mEvent.getLocation());

            // format the date
            SimpleDateFormat sdfD = new SimpleDateFormat("MM-dd");
            String dateStr = sdfD.format(mEvent.getStartDate());
            mEventDateTextView.setText(dateStr);

            // format the time
            SimpleDateFormat sdfT = new SimpleDateFormat("HH:mm");
            String timeStartStr = sdfT.format(mEvent.getStartDate());
            String timeEndStr = sdfT.format(mEvent.getEndDate());
            String timeDuration = timeStartStr + " - " + timeEndStr;
            mEventTimeTextView.setText(timeDuration);
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
