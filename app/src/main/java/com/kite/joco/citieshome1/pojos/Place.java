package com.kite.joco.citieshome1.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by Joco on 2015.05.01.. próba
 */
public class Place extends SugarRecord<Place>{

    @Expose
    @SerializedName("place name")
    String place_name;
    @Expose
    double longitude;
    @Expose
    double latitude;
    @Expose
    String state;
    @Expose
    @SerializedName("state abbreviation")
    String state_abbreviation;

    public Place() {
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState_abbreviation() {
        return state_abbreviation;
    }

    public void setState_abbreviation(String state_abbreviation) {
        this.state_abbreviation = state_abbreviation;
    }
}
/*
{
   "post code": "90210",
   "country": "United States",
   "country abbreviation": "US",
   "places": [
       {
           "place name": "Beverly Hills",
           "longitude": "-118.4065",
           "state": "California",
           "state abbreviation": "CA",
           "latitude": "34.0901"
       }
   ]
}
 */

