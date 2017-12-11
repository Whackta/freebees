package com.finalproject.cs4518.freebees;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Henry on 12/10/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    int mMonth;
    int mDay;
    private Activity mActivity;
    private DatePickerDialog.OnDateSetListener mListener;
    private DatePickerDialog datePicker;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mListener = (DatePickerDialog.OnDateSetListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePicker = new DatePickerDialog(mActivity, mListener, year, month, day);
        // Create a new instance of TimePickerDialog and return it
        return datePicker;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        mMonth = month;
        mDay = day;
    }
}
