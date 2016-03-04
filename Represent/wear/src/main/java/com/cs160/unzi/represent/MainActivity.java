package com.cs160.unzi.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (mSensor != null) {
            Log.i("ACCELEROMETER", "IS WORKINNN");
            Intent intent = new Intent(this, CongressionalViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.i("WATCHLISTENER", "92123");
            intent.putExtra("LOCATION", "92123");
            startActivity(intent);
        }
    }
}
