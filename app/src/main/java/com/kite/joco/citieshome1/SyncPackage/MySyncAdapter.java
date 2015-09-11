package com.kite.joco.citieshome1.SyncPackage;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.kite.joco.citieshome1.pojos.Lekerni;
import com.kite.joco.citieshome1.pojos.Place;
import com.kite.joco.citieshome1.pojos.PostCode;
import com.kite.joco.citieshome1.retrofit.ZippoClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mester József on 2015.09.11..
 */
public class MySyncAdapter extends AbstractThreadedSyncAdapter {


    ContentResolver mContentResolver;

    public MySyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    public MySyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        List<Lekerni> lekerniList = Lekerni.listAll(Lekerni.class);
        if (lekerniList.size()>0 && lekerniList != null){
            for (final Lekerni l: lekerniList) {
                ZippoClient.get().getByPostalCode("HU", l.getIrsz(), new Callback<PostCode>() {
                    @Override
                    public void success(PostCode postCode, Response response) {
                        postCode.save();
                        Place p = postCode.getPlaces().get(0);
                        p.save();
                        l.delete();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("CITIESHOME:SYNCADAPTER:ONPERFORMSYNC","Error"+ error.getLocalizedMessage());
                    }
                });
            }
        }
// Ha a lekerni adatok üres akkor vége while...
        // Ha nincs kapcsolat a szerverrel akkor kilép? Van olyan, hogy nincs kapcsolat?

        // Lekérés menete
        // Kiszedi a következő adatot, lekéri, elmenti a helyére, amikor kész (tehát sikeresen lekérte) akkor kitörli a lekerni
        // táblából, jön a következő, ha már nincs több (üres a tábla) akkor vége.


    }
}
