package com.finalproject.cs4518.freebees;

import android.graphics.Bitmap;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Henry on 12/8/2017.
 */

public class Event {
    private String mTitle;
    private String mOrganization;
    private String mDescription;
    private Date mStartDate;
    private Date mEndDate;

    //TODO: image storing?
    private Bitmap mImage;

    /**
     * Simplest constructor for event, sets components to default values
     */
    public Event(){
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
    public Event(String title, String organization, String description, Date startDate, Date endDate){
        mTitle = title;
        mOrganization = organization;
        mDescription = description;
        mStartDate = startDate;
        mEndDate = endDate;
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
}
