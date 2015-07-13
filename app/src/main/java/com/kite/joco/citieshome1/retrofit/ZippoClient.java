package com.kite.joco.citieshome1.retrofit;

import retrofit.RestAdapter;

/**
 * Created by Joco on 2015.05.01..
 */
public class ZippoClient {
    private static Api ZIPPO_CLIENT;
    private static String ROOT="http://api.zippopotam.us";

    static {
        setupZippoClient();
    }

    public static Api get(){
        return ZIPPO_CLIENT;
    }

    private static void setupZippoClient(){

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ROOT).build();



        ZIPPO_CLIENT = restAdapter.create(Api.class);
    }
}

/*
.setEndpoint(ROOT)
.setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
 */