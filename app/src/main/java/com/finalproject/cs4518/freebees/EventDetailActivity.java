package com.finalproject.cs4518.freebees;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventDetailActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    Event mEvent; // the event
    TextView mEventNameTextView;
    TextView mEventOrganizationTextView;
    TextView mEventDateTextView;
    TextView mEventTimeTextView;
    TextView mEventDescriptionTextView;
    Place mPlace;

    private PlaceDetectionClient mPlaceDetectionClient; // entry point into the place api

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the passed event
        mEvent = (Event) getIntent().getSerializableExtra("Event");
        if(mEvent == null){ // set to dummy values just in case
            mEvent = new Event();
        }

        mEventNameTextView = findViewById(R.id.eventName2);
        mEventOrganizationTextView = findViewById(R.id.eventOrganization);
        mEventDateTextView = findViewById(R.id.eventDate2);
        mEventTimeTextView = findViewById(R.id.eventTime2);
        mEventDescriptionTextView = findViewById(R.id.eventDesc);

        // set the text views to have the event's info
        mEventNameTextView.setText(mEvent.getTitle());
        mEventOrganizationTextView.setText(mEvent.getOrganization());

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

        mEventDescriptionTextView.setText(mEvent.getDescription());

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // get the place
        mPlace = mEvent.getPlace();
        // if place is null, set it to Sidney, Australia (for fun)
        if(mPlace == null){
            // leave this minimized, everything must be overridden
            mPlace = new Place() {
                @Override
                public String getId() {
                    return null;
                }

                @Override
                public List<Integer> getPlaceTypes() {
                    return null;
                }

                @Override
                public CharSequence getAddress() {
                    return null;
                }

                @Override
                public Locale getLocale() {
                    return null;
                }

                @Override
                public CharSequence getName() {
                    return null;
                }

                @Override
                public LatLng getLatLng() {
                    return new LatLng(-33.852, 151.211);
                }

                @Override
                public LatLngBounds getViewport() {
                    return null;
                }

                @Override
                public Uri getWebsiteUri() {
                    return null;
                }

                @Override
                public CharSequence getPhoneNumber() {
                    return null;
                }

                @Override
                public float getRating() {
                    return 0;
                }

                @Override
                public int getPriceLevel() {
                    return 0;
                }

                @Override
                public CharSequence getAttributions() {
                    return null;
                }

                @Override
                public Place freeze() {
                    return null;
                }

                @Override
                public boolean isDataValid() {
                    return false;
                }
            };
        }

        // setup the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng loc = mPlace.getLatLng();
        googleMap.addMarker(new MarkerOptions().position(loc)
                .title(mEvent.getTitle()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        LatLng loc = mPlace.getLatLng();
        Double latitude = loc.latitude;
        Double longitude = loc.longitude;

        // launch google maps using the above latlng
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
        return false;
    }
}
