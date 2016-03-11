package com.cs160.unzi.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class MainActivity extends Activity implements
        DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("hello", "im connected");
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.i("HERE", "DATA");
        for (DataEvent event : dataEvents) {
            String eventUri = event.getDataItem().getUri().toString();
            if (eventUri.contains("/watchContent")) {
                DataMapItem dataItem = DataMapItem.fromDataItem(event.getDataItem());
                DataMap dataMap = dataItem.getDataMap();
                HashMap<String, String[]> watchContent = new HashMap<String, String[]>();
                for (String key : dataMap.keySet()) {
                    watchContent.put(key, dataMap.getStringArray(key));
                }
                if (!watchContent.isEmpty()) {
                    setupGrid(watchContent);
                }
            }

        }
    }

    private void setupGrid(HashMap<String, String[]> watch_content) {
        //state, county, obama, romney
        Log.i("HERE", "GRID");
        String[] pres_results = watch_content.get("pres_results");
        watch_content.remove("pres_results");
        int size = watch_content.size();

        String[] name_row = new String[size];
        String[] party_row = new String[size];
        String[] image_row = new String[size];
        String[] term_end_row = new String[size];
        String[] bioguide_row = new String[size];

        int index = 0;
        for (String key : watch_content.keySet()) {
            String[] info = watch_content.get(key);
            name_row[index] = key;
            bioguide_row[index] = info[0];
            party_row[index] = info[1];
            term_end_row[index] = info[2];
            image_row[index] = info[3];
            index++;
        }

        //County, State
        String[][] rep_names = {name_row, {pres_results[1] + ", " + pres_results[0]}};
        String[][] rep_parties = {party_row, {pres_results[2] + "%"}};
        String[][] rep_images = {image_row, {pres_results[3] + "%"}};
        String[][] bioguide_ids = {bioguide_row, {"bio nothing"}};
        String[][] term_end_dates = {term_end_row, {"end date nothing"}};

        Intent intent = new Intent(this, CongressionalViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("repNames", rep_names);
        intent.putExtra("repParties", rep_parties);
        intent.putExtra("repImages", rep_images);
        intent.putExtra("bioguideIds", bioguide_ids);
        intent.putExtra("termEndDates", term_end_dates);

        startActivity(intent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}