package com.kite.joco.citieshome1.retrofit;

import com.kite.joco.citieshome1.pojos.PostCode;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Joco on 2015.05.01..
 * api.zippopotam.us REST servicéhez készítek API elérést.
 * a minta api.zippopotam.us/hu/4251 (Hajdusámson, Hajdu-Bihar county, Magyarorszag)
 * vagy us esetén api.zippopotam.us/us/90210 (Beverly Hills, California state, Los Angeles county)
 * Szóval az ország kódot is változtatni kell az API-nak es mögötte a ZIP kódot is.
 */
public interface Api {
    // ZIP alapjan kerek infot
    @GET("/{country}/{postcode}")
    void getByPostalCode(@Path("country") String countrycode,@Path("postcode") String postcode, Callback<PostCode> callback);


}
