package com.kite.joco.citieshome1.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

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
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ROOT).setConverter(new GsonConverter(gson)).build();

        //RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ROOT).build();



        ZIPPO_CLIENT = restAdapter.create(Api.class);
    }
}

/*
.setEndpoint(ROOT)
.setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
 */