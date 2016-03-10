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
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        for (DataEvent event : dataEvents) {

            Log.i("[DEBUG]", event.getDataItem().getUri().toString());

            String eventUri = event.getDataItem().getUri().toString();
            Log.i("WTF", "UG?????H");
            if (eventUri.contains("/myapp/myevent")) {
                Log.i("WTF", "UGH");
                DataMapItem dataItem = DataMapItem.fromDataItem(event.getDataItem());
                String[] data = dataItem.getDataMap().getStringArray("contents");
                for (int i = 0; i < 2; i++ ) {
                    Log.i("dfklajsdfkl", data[i]);
                }
                Log.i("OMGGG", data.toString());
                Log.i("[DEBUG]", "DeviceService - onDataChanged");

//                myListener.onDataReceived(data);
            }

        }
    }
//    private SensorManager mSensorManager;
//    private Sensor mSensor;
//
//    @Override
//    public final void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
//        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        if (mSensor != null) {
//            Log.i("ACCELEROMETER", "IS WORKINNN");
//            Intent intent = new Intent(getBaseContext(), CongressionalViewActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Log.i("WATCHLISTENER", "92123");
//            intent.putExtra("LOCATION", "92123");
//            startActivity(intent);
//        }
////        Log.i("ACCELEROMETER", "IS WORKINNN");
////        Intent intent = new Intent(getBaseContext(), CongressionalViewActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        Log.i("WATCHLISTENER", "92123");
////        intent.putExtra("LOCATION", "92123");
////        startActivity(intent);
//        // Do something with this sensor value.
//
//    }
//
//    private final SensorEventListener mSensorListener = new SensorEventListener() {
//
//        @Override
//        public final void onSensorChanged(SensorEvent event) {
//                // Do something with this sens
//                // or value.
//
//        }
//
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        }
//    };
//
//        @Override
//        protected void onResume() {
//            super.onResume();
//            mSensorManager.registerListener(mSensorListener,  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
//        }
//
//    @Override
//        protected void onPause() {
//            mSensorManager.unregisterListener(mSensorListener);
//            super.onPause();
//        }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}