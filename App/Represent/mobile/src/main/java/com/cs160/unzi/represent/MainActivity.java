package com.cs160.unzi.represent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final String GeocodeKey = "AIzaSyCifyP8sNEZFA4tKYSwsgPx1ftk-1zVpxk";
    private final String sunlightKey = "946f65d6df5c4ae2b5f9ddb58fd867f5";
    private final String twitterConsumerKey = "Y2kIN6M8Z8kc0T5MZMaPPwhTx";
    private final String twitterConsumerSecret = "V9qxwhzlP1XAQX6rKjdToXoC6XA4olURktLc0PNwkTyTlRiBKq";
    private String bearerToken = "";

    private final String zipCodeSearch = "search-type-zip-code";
    private final String latLongSearch = "search-type-lat-long";

    private String latitude = "";
    private String longitude = "";
    private String county = "";

    private Location current_location = null;
    private GoogleApiClient mGoogleApiClient;
    private GeoApiContext mGeoContext;

    private HashMap<String, String> watchContent;
    private HashMap<String, String> repPics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(twitterConsumerKey, twitterConsumerSecret);
        Fabric.with(this, new Twitter(authConfig));

        RetrieveTwitterBearerToken TwitterAsyncTask =new RetrieveTwitterBearerToken(new RetrieveTwitterBearerToken.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                bearerToken = output;
                Log.i("TOKEN", output);
            }
        });
        TwitterAsyncTask.execute();


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Wearable.API)
                    .build();
        }

        mGeoContext = new GeoApiContext().setApiKey(GeocodeKey);

        final EditText location_input = (EditText) findViewById(R.id.location_input);
        location_input.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        location_input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String location_string = location_input.getText().toString();
                    String search_type = "";
                    Log.i("HELLO", location_string);
                    if (location_string == "") {
                        search_type = latLongSearch;
                    } else {
                        search_type = zipCodeSearch;
                    }

                    try {
                        retrieveRepresentatives(search_type);
                    } catch (IOException e) {
                        Log.e("ERROR GEO", e.getMessage());
                    }
                    return true;
                }
                return false;
            }
        });
    }


    public void retrieveRepresentatives(String search_type) throws IOException {
        String url_string;
        if (search_type == zipCodeSearch) {
            url_string = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=94704&apikey=" + sunlightKey;
        } else {
            url_string = "congress.api.sunlightfoundation.com/legislators/locate?latitude=" +
                         latitude + "&longitude=" + longitude + "&apikey=946f65d6df5c4ae2b5f9ddb58fd867f5";
        }

        RetrieveRepresentatives asyncTask = new RetrieveRepresentatives(new RetrieveRepresentatives.AsyncResponse(){

            @Override
            public void processFinish(HashMap<String, String> watch_content, HashMap<String, String> rep_pics) {
                watchContent = watch_content;
                repPics = rep_pics;
            }
        }, this.getBaseContext(), bearerToken);

        asyncTask.execute(url_string);
    }



    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            current_location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        if (current_location != null) {
            Log.i("LOCATION", current_location.toString());
            LatLng location_input = new LatLng(current_location.getLatitude(), current_location.getLongitude());
            GeocodingApiRequest geocode_request = GeocodingApi.reverseGeocode(mGeoContext, location_input);

            latitude = String.valueOf(current_location.getLatitude());
            longitude = String.valueOf(current_location.getLongitude());

            geocode_request.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
                @Override
                public void onResult(GeocodingResult[] result) {
                    county = result[0].addressComponents[3].longName;
                }

                @Override
                public void onFailure(Throwable e) {
                    Log.i("ERROR ONCONNECTED", e.getMessage());
                }
            });
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
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
