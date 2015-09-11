package com.kite.joco.citieshome1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvKiir = (TextView) findViewById(R.id.tvKiir);
        etZip = (EditText) findViewById(R.id.etZip);
    }

    public void onClick(View v){
        // Keresés off line módban.
        // Ha itt nincs meg csak akkor kell net.

        String zipcode = etZip.getText().toString();
        Log.d(CITIESHOME_DB_TAG," a keresett irsz: "+ zipcode);
        //List<PostCode> existingPostcodeList = PostCode.find(PostCode.class, "postcode = ?", zipcode);
        List<PostCode> existingPostcodeList = Select.from(PostCode.class).where(Condition.prop("postcode").eq(zipcode)).list();
        //Log.d(CITIESHOME_DB_TAG," ujfajta select eredménye: "+ nowhasPostcode.size());
        if (existingPostcodeList.size() >0 && existingPostcodeList != null ) {
            try {
                Long placeid = existingPostcodeList.get(0).getId();
                Place place = Select.from(Place.class).where(Condition.prop("id").eq(placeid)).first();
                tvKiir.setText(place.getPlace_name());
                //tvKiir.setText(existingPostcodeList.get(0).getPlaces().get(0).getPlace_name());
                Log.d(CITIESHOME_DB_TAG, "Megtaláltam az adatbázisban, nem kellett internetes keresés");
            }
            catch (Exception ex){
                Log.d(CITIESHOME_DB_TAG," error : " + ex.getLocalizedMessage());
            }
        }
        else {

            ZippoClient.get().getByPostalCode("hu", zipcode, new Callback<PostCode>() {
                @Override
                public void success(PostCode postCode, Response response) {
                    irsz = postCode.getPlaces().get(0).getPlace_name();
                    postCode.save();
                    Place p = postCode.getPlaces().get(0);
                    p.save();
                    Log.d(CITIESHOME_DB_TAG,"Place saved");
                    List<PostCode> postCodeList = PostCode.listAll(PostCode.class);
                    List<Place> places = Place.listAll(Place.class);
                    int placessize = places.size();
                    int postcodesize = postCodeList.size();
                    Log.d(CITIESHOME_DB_TAG, " A  PostCode lista elemeinek száma: " + postcodesize);
                    Log.d(CITIESHOME_DB_TAG, " A  Places lista elemeinek száma: " + placessize);
                    tvKiir.setText(irsz);
                    for (PostCode pc:postCodeList){
                        Log.d(CITIESHOME_DB_TAG, " Postcode id: " + pc.getId() + " country "+ pc.getCountry() + " irsz " + pc.getPostcode());
                    }
                    for (Place pl:places){
                        Log.d(CITIESHOME_DB_TAG, " place id: " + pl.getId()+ " name: "  + pl.getPlace_name());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    tvKiir.setText(error.getLocalizedMessage());
                }
            });
        }
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
}
