package com.kite.joco.citieshome1.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

/**
 * Created by Joco on 2015.05.01..
 */
public class PostCode extends SugarRecord<PostCode> {

    @Expose
    @SerializedName("post code")
    String postcode;
    @Expose
    String country;
    @Expose
    @SerializedName("country abbreviation")
    String country_abbreviation;
    //@Expose
    //Place place;

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @Ignore
    @Expose
    List<Place> places;

    public PostCode() {
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String post_code) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_abbreviation() {
        return country_abbreviation;
    }

    public void setCountry_abbreviation(String country_abbreviation) {
        this.country_abbreviation = country_abbreviation;
    }

    /*public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }*/
}
/*
* "post code": "90210",
   "country": "United States",
   "country abbreviation": "US",
   "places": [
 */