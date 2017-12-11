package com.finalproject.cs4518.freebees;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.location.places.Place;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventCreationActivity extends FragmentActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
    Event mEvent; // the event
    EditText mNameEntry;
    EditText mOrganizationEntry;
    Button mDatePicker;
    Button mLocationPicker;
    Button mStartTimePicker;
    Button mEndTimePicker;
    EditText mDescriptionEntry;
    Button mPostButton;

    Place mPlace;

    final int START_TIME_CODE = 0;
    final int END_TIME_CODE = 1;
    int current_code = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        // Create an event to be continuously updated and then pushed
        mEvent = new Event();

        // setup the widgets
        mNameEntry = findViewById(R.id.eventNameEntry);
        mOrganizationEntry = findViewById(R.id.eventOrganizationEntry);
        mDatePicker = findViewById(R.id.datePicker);
        mLocationPicker = findViewById(R.id.locPicker);
        mStartTimePicker = findViewById(R.id.startTimePicker);
        mEndTimePicker = findViewById(R.id.endTimePicker);
        mDescriptionEntry = findViewById(R.id.Description);
        mPostButton = findViewById(R.id.postIt);

        // grab the current date and time
        // setup the buttons
        Calendar currentDateTime = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                "MMMM dd yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat(
                "hh:mm a");

        // setup the name entry
        mNameEntry.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mNameEntry.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mEvent.setTitle("No Title");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                mEvent.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // setup the organization entry
        mOrganizationEntry.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mOrganizationEntry.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mEvent.setOrganization("No Organization");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                mEvent.setOrganization(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // setup the description entry
        mDescriptionEntry.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mDescriptionEntry.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mEvent.setDescription("No Description");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                mEvent.setDescription(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void showStartTimePickerDialog(View view) {
        current_code = 0;
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "Select Start Time");
    }

    public void showEndTimePickerDialog(View view) {
        current_code = 1;
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "Select End Time");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d("TIME", "TIME RECEIVED!");
        Calendar dateCalendar = Calendar.getInstance();
        // check for which time we're adjusting
        switch(current_code){
            case START_TIME_CODE:
                dateCalendar.setTimeInMillis(mEvent.getStartDateMillis()); // set the calendar to whatever it originally was
                dateCalendar.set(Calendar.HOUR, hourOfDay);
                dateCalendar.set(Calendar.MINUTE, minute);
                mEvent.setStartDateMillis(dateCalendar.getTimeInMillis()); // update the event
                mStartTimePicker.setText("START TIME: " + hourOfDay + ":" + minute); // update the time
                break;
            case END_TIME_CODE:
                dateCalendar.setTimeInMillis(mEvent.getEndDateMillis()); // set the calendar to whatever it originally was
                dateCalendar.set(Calendar.HOUR, hourOfDay);
                dateCalendar.set(Calendar.MINUTE, minute);
                mEvent.setEndDateMillis(dateCalendar.getTimeInMillis()); // update the event
                mEndTimePicker.setText("END TIME: " + hourOfDay + ":" + minute); // update the time
                break;
        }
    }

    public void showDatePickerDialog(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "Select Event Date");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(mEvent.getStartDateMillis()); // set the calendar to whatever the starting date is
        c.set(Calendar.MONTH, month); // update the month and day
        c.set(Calendar.DAY_OF_MONTH, day);
        mEvent.setStartDateMillis(c.getTimeInMillis()); // update the date
        String formatMonth = c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
        mDatePicker.setText("DATE: " + formatMonth + " " + day); // update the button to reflect this

    }

    public void showLocPickerDialog(View view) {

    }

    public void PostIt(View view) {

    }
}



