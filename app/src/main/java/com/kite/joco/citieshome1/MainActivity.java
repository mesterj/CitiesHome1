package com.kite.joco.citieshome1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kite.joco.citieshome1.pojos.PostCode;
import com.kite.joco.citieshome1.retrofit.ZippoClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity {

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
        String zipcode = etZip.getText().toString();
        ZippoClient.get().getByPostalCode("hu",zipcode, new Callback<PostCode>() {
            @Override
            public void success(PostCode postCode, Response response) {
                irsz = postCode.getPlaces().get(0).getPlace_name();
                tvKiir.setText(irsz);
            }

            @Override
            public void failure(RetrofitError error) {
                tvKiir.setText(error.getLocalizedMessage());
            }
        });
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
