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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends WearableActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (mSensor != null) {
            Log.i("ACCELEROMETER", "IS WORKINNN");
            Intent intent = new Intent(getBaseContext(), CongressionalViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.i("WATCHLISTENER", "92123");
            intent.putExtra("LOCATION", "92123");
            startActivity(intent);
        }
//        Log.i("ACCELEROMETER", "IS WORKINNN");
//        Intent intent = new Intent(getBaseContext(), CongressionalViewActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Log.i("WATCHLISTENER", "92123");
//        intent.putExtra("LOCATION", "92123");
//        startActivity(intent);
        // Do something with this sensor value.

    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        @Override
        public final void onSensorChanged(SensorEvent event) {
                // Do something with this sens
                // or value.

        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

        @Override
        protected void onResume() {
            super.onResume();
            mSensorManager.registerListener(mSensorListener,  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }

    @Override
        protected void onPause() {
            mSensorManager.unregisterListener(mSensorListener);
            super.onPause();
        }
}
