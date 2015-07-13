package com.kite.joco.citieshome1.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Joco on 2015.05.01..
 */
public class PostCode {

    @SerializedName("post code")
    String postcode;
    String country;
    @SerializedName("country abbreviation")
    String country_abbreviation;
    Place place;

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
/*
* "post code": "90210",
   "country": "United States",
   "country abbreviation": "US",
   "places": [
 */