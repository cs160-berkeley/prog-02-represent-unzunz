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
        Log.i("hello", "please");
        for (DataEvent event : dataEvents) {

            Log.i("[DEBUG]", event.getDataItem().getUri().toString());

            String eventUri = event.getDataItem().getUri().toString();
            Log.i("WTF", "UG?????H");
            if (eventUri.contains("/myapp/myevent")) {
                Log.i("WTF", "UGH");
                DataMapItem dataItem = DataMapItem.fromDataItem(event.getDataItem());

                DataMap dataMap = dataItem.getDataMap();
//                String[] data = dataItem.getDataMap().getStringArray("contents");

                HashMap<String, String[]> hashMap = new HashMap<String, String[]>();
                for (String key : dataMap.keySet()) {
                    hashMap.put(key, dataMap.getStringArray(key));
                    Log.i("KEY", key);
                    Log.i("Value", dataMap.getStringArray(key).toString());
                }
                Log.i("IDKSF", "Dasdfasdf");

//                myListener.onDataReceived(data);
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}