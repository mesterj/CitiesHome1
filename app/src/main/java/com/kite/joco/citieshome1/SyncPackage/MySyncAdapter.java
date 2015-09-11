package com.kite.joco.citieshome1.SyncPackage;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by Mester J�zsef on 2015.09.11..
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
// Ha a lekerni adatok �res akkor v�ge while...
        // Ha nincs kapcsolat a szerverrel akkor kil�p? Van olyan, hogy nincs kapcsolat?

        // Lek�r�s menete
        // Kiszedi a k�vetkez� adatot, lek�ri, elmenti a hely�re, amikor k�sz (teh�t sikeresen lek�rte) akkor kit�rli a lekerni
        // t�bl�b�l, j�n a k�vetkez�, ha m�r nincs t�bb (�res a t�bla) akkor v�ge.


    }
}
