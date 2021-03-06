package com.kite.joco.citieshome1;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kite.joco.citieshome1.pojos.Lekerni;
import com.kite.joco.citieshome1.pojos.Place;
import com.kite.joco.citieshome1.pojos.PostCode;
import com.kite.joco.citieshome1.retrofit.ZippoClient;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity {

    public static final String CITIESHOME_DB_TAG = "CITIESHOME:DB:TAG";
    String irsz;
    TextView tvKiir;
    EditText etZip;
    ContentResolver mResolver;
    public static final String AUTHORITY = "com.kite.joco.citieshome1.SyncPackage.provider";
    public static final String ACCOUNT_TYPE = "com.kite.joco.citieshome1.SyncPackage";
    public static final String ACCOUNT = "dummyaccount";
    Account myAccount;
    SimpleCursorAdapter cityAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvKiir = (TextView) findViewById(R.id.tvKiir);
        etZip = (EditText) findViewById(R.id.etZip);
        // Create the account type and default account
        myAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);
// If the account already exists no harm is done but
// a warning will be logged.
        if (accountManager.addAccountExplicitly(myAccount, null, null)) {
            Log.d("CITIESHOME:AS:MAC", "Made account");
        } else {
            Log.d("CITIESHOME:AS:MAC", "Error while create account.");
        }
        mResolver = getContentResolver();
        //ContentResolver.addPeriodicSync(new Account("sync","basicsyncaccount"),"com.kite.joco.dummyprovider",Bundle.EMPTY,1L);
        //ContentResolver.addPeriodicSync(myAccount,AUTHORITY,Bundle.EMPTY,120L);
        ContentResolver.setSyncAutomatically(myAccount, AUTHORITY, true);
        // for testing 120L or lower recommended
        ContentResolver.addPeriodicSync(myAccount, AUTHORITY, Bundle.EMPTY, 1200000L);

        Button btnSync = (Button) findViewById(R.id.btnSync);
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(CITIESHOME_DB_TAG, " sync started...");
                Bundle settingsBundle = new Bundle();
                settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

                ContentResolver.requestSync(myAccount, AUTHORITY, settingsBundle);
            }
        });

       /* Bundle periodicBundle = new Bundle();
        periodicBundle.putBoolean(ContentResolver.SYNC_EXTRAS_INITIALIZE,true);*/

        AutoCompleteTextView actvCity = (AutoCompleteTextView) findViewById(R.id.actvCityname);

        List<Place> places = Select.from(Place.class).list();

        String [] citiesArray = { "PLACENAME", "LONGITUDE", "LATITUDE", "STATE", "STATEABBREVIATION"};
        int [] toArray = new int[]{android.R.id.text1};

        //cityAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,getCityCursor(),citiesArray,toArray);

        cityAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,null,citiesArray,toArray);
        actvCity.setAdapter(cityAdapter);

        cityAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                return getCityCursor(charSequence);
            }
        });

        cityAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            @Override
            public CharSequence convertToString(Cursor cursor) {
                //int j = cursor.getColumnIndex("PLACENAME");
                int j = cursor.getColumnIndex("LONGITUDE");
                return cursor.getString(j);
            }
        });



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public Cursor getCityCursor(CharSequence str) {
        Cursor citiCursor = Select.from(Place.class).orderBy("PLACENAME").where(Condition.prop("PLACENAME").like(str+"%")).getCursor();
        return citiCursor;
        // TODO kisbetű, nagybetű vizsgálat
        // Ezt kell megcsinálni sugarrecord.getCursor-ral.
        // Ha a select-tel létrehozott halmazra csinálom a cursort akkor így nem kell megadni a feltételeket, mert azokat
        // már a select-ben megadtam
    }

    public Cursor getCityCursor(){
        Cursor citiCursor = Select.from(Place.class).getCursor();
        return citiCursor;

    }

    public void onClick(View v) {
        // Keresés off line módban.
        // Ha itt nincs meg csak akkor kell net.

        final String zipcode = etZip.getText().toString();
        Log.d(CITIESHOME_DB_TAG, " a keresett irsz: " + zipcode);
        //List<PostCode> existingPostcodeList = PostCode.find(PostCode.class, "postcode = ?", zipcode);
        List<PostCode> existingPostcodeList = Select.from(PostCode.class).where(Condition.prop("postcode").eq(zipcode)).list();
        //Log.d(CITIESHOME_DB_TAG," ujfajta select eredménye: "+ nowhasPostcode.size());
        if (existingPostcodeList.size() > 0 && existingPostcodeList != null) {
            try {
                Long placeid = existingPostcodeList.get(0).getId();
                //Place place = Select.from(Place.class).where(Condition.prop("id").eq(placeid)).first();
                Place place = Place.findById(Place.class, placeid);
                tvKiir.setText(place.getPlace_name());
                //tvKiir.setText(existingPostcodeList.get(0).getPlaces().get(0).getPlace_name());
                Log.d(CITIESHOME_DB_TAG, "Megtaláltam az adatbázisban, nem kellett internetes keresés");
            } catch (Exception ex) {
                Log.d(CITIESHOME_DB_TAG, " error : " + ex.getLocalizedMessage());
            }
        } else {

            try {
                ZippoClient.get().getByPostalCode("hu", zipcode, new Callback<PostCode>() {
                    @Override
                    public void success(PostCode postCode, Response response) {
                        irsz = postCode.getPlaces().get(0).getPlace_name();
                        postCode.save();
                        Place p = postCode.getPlaces().get(0);
                        p.save();
                        Log.d(CITIESHOME_DB_TAG, "Place saved");
                        List<PostCode> postCodeList = PostCode.listAll(PostCode.class);
                        List<Place> places = Place.listAll(Place.class);
                        int placessize = places.size();
                        int postcodesize = postCodeList.size();
                        Log.d(CITIESHOME_DB_TAG, " A  PostCode lista elemeinek száma: " + postcodesize);
                        Log.d(CITIESHOME_DB_TAG, " A  Places lista elemeinek száma: " + placessize);
                        tvKiir.setText(irsz);
                        for (PostCode pc : postCodeList) {
                            Log.d(CITIESHOME_DB_TAG, " Postcode id: " + pc.getId() + " country " + pc.getCountry() + " irsz " + pc.getPostcode());
                        }
                        for (Place pl : places) {
                            Log.d(CITIESHOME_DB_TAG, " place id: " + pl.getId() + " name: " + pl.getPlace_name());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(CITIESHOME_DB_TAG, " Hiba történt a kapcsolat során");//error.getLocalizedMessage());
                        tvKiir.setText("Nincs internet-kapcsolat");
                        zipment(zipcode);
                    }
                });
            } catch (Exception ex) {
                zipment(zipcode);
            }
        }
    }

    public void zipment(String zipcode) {
        Lekerni l = new Lekerni();
        l.setIrsz(zipcode);
        List<Lekerni> mindenLekerendo = Lekerni.listAll(Lekerni.class);
        if (!mindenLekerendo.contains(l)) {
            l.save();
        } else {
            Log.d(CITIESHOME_DB_TAG, "Már van ilyen keresendő");
        }
        Log.d(CITIESHOME_DB_TAG, "Nem volt net , mentettem");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.kite.joco.citieshome1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.kite.joco.citieshome1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
