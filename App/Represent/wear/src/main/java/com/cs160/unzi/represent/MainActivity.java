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
        for (DataEvent event : dataEvents) {
            String eventUri = event.getDataItem().getUri().toString();
            if (eventUri.contains("/watchContent")) {
                DataMapItem dataItem = DataMapItem.fromDataItem(event.getDataItem());
                DataMap dataMap = dataItem.getDataMap();

                HashMap<String, String[]> watchContent = new HashMap<String, String[]>();
                for (String key : dataMap.keySet()) {
                    watchContent.put(key, dataMap.getStringArray(key));
                    Log.i("KEY", key);
                    Log.i("Value", dataMap.getStringArray(key).toString());
                }
                if (!watchContent.isEmpty()) {
                    setupGrid(watchContent);
                }
            }

        }
    }

    private void setupGrid(HashMap<String, String[]> watch_content) {
        int size = watch_content.size();
        String[][] rep_names = new String[2][size];
        String[][] rep_parties = new String[2][size];
        String[][] rep_images = new String[2][size];
        String[] bioguide_ids = new String[size];
        String[] end_term_dates = new String[size];

        int index = 0;
        for (String key : watch_content.keySet()) {
            String[] info = watch_content.get(key);
            rep_names[0][index] = key;
            bioguide_ids[index] = info[0];
            rep_parties[0][index] = info[1];
            end_term_dates[index] = info[2];
            rep_images[0][index] = info[3];
            index++;
        }

        rep_names[1][0] = "California";
        rep_parties[1][0] = "Obama - 52.6%";
        rep_images[1][0] = "Romney - 45%";

        Intent intent = new Intent(this, CongressionalViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("repNames", rep_names);
        intent.putExtra("repParties", rep_parties);
        intent.putExtra("repImages", rep_images);
        intent.putExtra("bioguideIds", bioguide_ids);
        intent.putExtra("endTermDates", end_term_dates);

        startActivity(intent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}