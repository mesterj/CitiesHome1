package com.kite.joco.citieshome1.SyncPackage;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

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
// Ha a lekerni adatok üres akkor vége while...
        // Ha nincs kapcsolat a szerverrel akkor kilép? Van olyan, hogy nincs kapcsolat?

        // Lekérés menete
        // Kiszedi a következõ adatot, lekéri, elmenti a helyére, amikor kész (tehát sikeresen lekérte) akkor kitörli a lekerni
        // táblából, jön a következõ, ha már nincs több (üres a tábla) akkor vége.


    }
}
