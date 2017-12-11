package com.finalproject.cs4518.freebees;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ThemedSpinnerAdapter;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Henry on 12/10/2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    int hour;
    int mMinute;
    private Activity mActivity;
    private TimePickerDialog.OnTimeSetListener mListener;
    private TimePickerDialog timePicker;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mListener = (TimePickerDialog.OnTimeSetListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        timePicker = new TimePickerDialog(mActivity, mListener, hour, minute,
                DateFormat.is24HourFormat(mActivity));
        // Create a new instance of TimePickerDialog and return it
        return timePicker;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour = hourOfDay;
        mMinute = minute;
        Log.d("TIME", "TIME SET!");
    }
}
