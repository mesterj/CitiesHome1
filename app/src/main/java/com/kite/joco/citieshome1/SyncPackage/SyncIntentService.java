package com.kite.joco.citieshome1.SyncPackage;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SyncIntentService extends Service {

    private static MySyncAdapter mySyncAdapter = null;
    public static final Object mySyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        synchronized (mySyncAdapterLock){
            if (mySyncAdapter==null){
              mySyncAdapter = new MySyncAdapter(getApplicationContext(),true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mySyncAdapter.getSyncAdapterBinder();
    }
}
