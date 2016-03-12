package com.cs160.unzi.represent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private Location current_location;
    private String latitude;
    private String longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mGoogleApiClient.connect();

        final EditText location_input = (EditText) findViewById(R.id.location_input);
        location_input.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        location_input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String location_string = location_input.getText().toString();
                    Intent intent = new Intent(mContext, RetrieveContent.class);
                    intent.putExtra("LOCATION", location_string);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    mContext.startService(intent);
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i("WE", "CONNECTED");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            current_location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if (current_location != null) {
            Log.i("CURRENT_LOCATION", current_location.toString());
            latitude = String.valueOf(current_location.getLatitude());
            longitude = String.valueOf(current_location.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}
