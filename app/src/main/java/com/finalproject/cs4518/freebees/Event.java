package com.finalproject.cs4518.freebees;

import android.graphics.Bitmap;

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
    private String mTitle;
    private String mOrganization;
    private String mDescription;
    private String mLocation;
    private Place mPlace;
    private Date mStartDate;
    private Date mEndDate;
    private LatLng mLatLng;
    private long mStartDateAsMillis;
    private long mEndDateAsMillis;

    //TODO: image storing?
    private Bitmap mImage;

    /**
     * Simplest constructor for event, sets components to default values
     */
    public Event(){
        mTitle = "Dummy title";
        mOrganization = "Dummy organization";
        mDescription = "Dummy description";
        mLocation = "123 Imaginary lane";
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
    public Event(String title, String organization, String description, Date startDate, Date endDate, String location){
        mTitle = title;
        mOrganization = organization;
        mDescription = description;
        mStartDate = startDate;
        mEndDate = endDate;
        mLocation = location;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getOrganization(){
        return mOrganization;
    }

    public String getDescription(){
        return mDescription;
    }

    public Date getStartDate(){
        return mStartDate;
    }

    public Date getEndDate(){
        return mEndDate;
    }

    public String getLocation(){return mLocation;}

    public Place getPlace(){return mPlace;}

    public LatLng getLatLng(){return mLatLng;}

    public long getStartDateMillis(){return mStartDateAsMillis;}

    public long getEndDateMillis(){return mEndDateAsMillis;}

    public void setTitle(String title){
        mTitle = title;
    }

    public void setOrganization(String org){
        mOrganization = org;
    }

    public void setDescription(String desc){
        mDescription = desc;
    }

    public void setStartDate(Date date){
        mStartDate = date;
    }

    public void setEndDate(Date date){
        mEndDate = date;
    }

    public void setLocation(String loc){mLocation = loc;}

    public void setPlace(Place place){mPlace = place;}

    public void setLatLng(LatLng latlng){mLatLng = latlng;}

    public void setStartDateMillis(long sd){
        mStartDateAsMillis = sd;
    }
    public void setEndDateMillis(long sd){
        mEndDateAsMillis = sd;
    }
}
