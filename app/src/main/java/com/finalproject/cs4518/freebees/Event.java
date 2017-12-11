package com.finalproject.cs4518.freebees;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Henry on 12/8/2017.
 */

public class Event implements Serializable{
    private int mEventID;
    private String mTitle;
    private String mOrganization;
    private String mDescription;
    private Date mStartDate;
    private Date mEndDate;
    private LatLng mLatLng;
    private long mStartDateAsMillis;
    private long mEndDateAsMillis;

    /**
     * Simplest constructor for event, sets components to default values
     */
    public Event(){
        mEventID = -1;
        mTitle = "Dummy title";
        mOrganization = "Dummy organization";
        mDescription = "Dummy description";
        mStartDate = new Date();
        mEndDate = new Date();
    }

    /**
     * Constructor for an event, includes all major components
     * @param title
     * @param organization
     * @param description
     * @param startDate
     * @param endDate
     */
    public Event(int id, String title, String organization, String description, Date startDate, Date endDate, LatLng latlng){
        mEventID = id;
        mTitle = title;
        mOrganization = organization;
        mDescription = description;
        mLatLng = latlng;
        mStartDate = startDate;
        mEndDate = endDate;
    }

    public int getEventID(){ return mEventID; }

    public String getTitle(){return mTitle;}

    public String getOrganization(){return mOrganization;}

    public String getDescription(){return mDescription;}

    public Date getStartDate(){return mStartDate;}

    public Date getEndDate(){return mEndDate;}

    public LatLng getLatLng(){return mLatLng;}

    public long getStartDateMillis(){return mStartDateAsMillis;}

    public long getEndDateMillis(){return mEndDateAsMillis;}

    public void setTitle(String title){
        mTitle = title;
    }

    public void setOrganization(String org){mOrganization = org;}

    public void setDescription(String desc){mDescription = desc;}

    public void setStartDate(Date date){mStartDate = date;}

    public void setEndDate(Date date){mEndDate = date;}

    public void setLatLng(LatLng latlng){mLatLng = latlng;}

    public void setStartDateMillis(long sd){
        mStartDateAsMillis = sd;
    }
    public void setEndDateMillis(long sd){
        mEndDateAsMillis = sd;
    }
    public void setEventID(int id){ mEventID = id; }
}
