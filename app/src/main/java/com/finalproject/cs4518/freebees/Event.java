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
    private String mEventID;
    private String mTitle;
    private String mOrganization;
    private String mDescription;
    private String mLocation;
    private Date mStartDate;
    private Date mEndDate;
    private double mLat;
    private double mLong;
    private long mStartDateAsMillis;
    private long mEndDateAsMillis;

    /**
     * Simplest constructor for event, sets components to default values
     */
    public Event(){
        mEventID = "Dummy ID";
        mTitle = "Dummy title";
        mOrganization = "Dummy organization";
        mDescription = "Dummy description";
        mStartDate = new Date();
        mEndDate = new Date();
        mLocation = "123 Street Ave.";
    }

    /**
     * Constructor for an event, includes all major components
     * @param title
     * @param organization
     * @param description
     * @param startDate
     * @param endDate
     */
    public Event(String title, String organization, String description, Date startDate, Date endDate, LatLng latlng){
        mTitle = title;
        mOrganization = organization;
        mDescription = description;
        mLat = latlng.latitude;
        mLong = latlng.longitude;
        mStartDate = startDate;
        mEndDate = endDate;
    }

    public String getEventID(){ return mEventID; }

    public String getLocation(){return mLocation;}

    public String getTitle(){return mTitle;}

    public String getOrganization(){return mOrganization;}

    public String getDescription(){return mDescription;}

    public Date getStartDate(){return mStartDate;}

    public Date getEndDate(){return mEndDate;}

    public LatLng getLatLng(){return new LatLng(mLat, mLong);}

    public long getStartDateMillis(){return mStartDateAsMillis;}

    public long getEndDateMillis(){return mEndDateAsMillis;}

    public void setLocation(String loc){
        if(loc.indexOf(",") != -1){
            mLocation = loc.substring(0, loc.indexOf(","));
        }
        else{
            mLocation = loc;
        }
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public void setOrganization(String org){mOrganization = org;}

    public void setDescription(String desc){mDescription = desc;}

    public void setStartDate(Date date){mStartDate = date;}

    public void setEndDate(Date date){mEndDate = date;}

    public void setLatLng(LatLng latlng){mLat = latlng.latitude; mLong = latlng.longitude;}

    public void setStartDateMillis(long sd){mStartDateAsMillis = sd;}

    public void setEndDateMillis(long sd){
        mEndDateAsMillis = sd;
    }

    public void setEventID(String id){ mEventID = id; }
}
