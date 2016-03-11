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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import io.fabric.sdk.android.Fabric;

//public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
public class MainActivity extends AppCompatActivity {
//    private final String GeocodeKey = "AIzaSyDNoKsb7a8ue3TUDQ9ueCrGcVpUN9H0Ndo";
//    private final String sunlightKey = "946f65d6df5c4ae2b5f9ddb58fd867f5";
//    private final String twitterConsumerKey = "Y2kIN6M8Z8kc0T5MZMaPPwhTx";
//    private final String twitterConsumerSecret = "V9qxwhzlP1XAQX6rKjdToXoC6XA4olURktLc0PNwkTyTlRiBKq";
//    private String bearerToken = "";
//
//    private final String zipCodeSearch = "search-type-zip-code";
//    private final String latLongSearch = "search-type-lat-long";
//
//    String search_type;
//    private String state;
//    private String county;
//    private String zipCode;
//    private String latitude = "";
//    private String longitude = "";
//    private String[] presResults;
//
//    private Location current_location = null;
//    private GoogleApiClient mGoogleApiClient;
//    private GeoApiContext mGeoContext;
//
//    private HashMap<String, String> watchContent;
//    private HashMap<String, String> repPics;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(twitterConsumerKey, twitterConsumerSecret);
//        Fabric.with(this, new Twitter(authConfig));

//        RetrieveTwitterBearerToken TwitterAsyncTask =new RetrieveTwitterBearerToken(new RetrieveTwitterBearerToken.AsyncResponse() {
//            @Override
//            public void processFinish(String output) {
//                bearerToken = output;
//                Log.i("TOKEN", output);
//            }
//        });
//        TwitterAsyncTask.execute();

//
//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build();
//        }

//        mGeoContext = new GeoApiContext().setApiKey(GeocodeKey);

        final EditText location_input = (EditText) findViewById(R.id.location_input);
        location_input.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        location_input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String location_string = location_input.getText().toString();
//                    search_type = "";
//                    Log.i("HELLO", location_string);
//                    if (location_string == "") {
//                        search_type = latLongSearch;
//                    } else {
//                        search_type = zipCodeSearch;
//                        zipCode = location_string;
//                    }
//                    setLocation(search_type);
                    Intent intent = new Intent(mContext, RetrieveContent.class);
                    intent.putExtra("LOCATION", location_string);
                    mContext.startService(intent);

                    return true;
                }
                return false;
            }
        });
    }


//    public void retrieveRepresentatives() throws IOException {
//        String url_string;
//        if (search_type == zipCodeSearch) {
//            url_string = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=" + zipCode + "&apikey=" + sunlightKey;
//        } else {
//            url_string = "congress.api.sunlightfoundation.com/legislators/locate?latitude=" +
//                         latitude + "&longitude=" + longitude + "&apikey=" + sunlightKey;
////            url_string = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=" + "94704" + "&apikey=" + sunlightKey;
//        }
//        RetrieveRepresentatives asyncTask = new RetrieveRepresentatives(this.getBaseContext(), bearerToken, presResults);
//        asyncTask.execute(url_string, zipCodeSearch);
//    }
//
//    public void setLocation(String search_type) {
//        GeocodingApiRequest geocode_request;
//        if (search_type == zipCodeSearch) {
//            geocode_request = GeocodingApi.geocode(mGeoContext, zipCode);
//        } else {
//            LatLng location_input = new LatLng(current_location.getLatitude(), current_location.getLongitude());
//            geocode_request = GeocodingApi.reverseGeocode(mGeoContext, location_input);
//        }
//        geocode_request.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
//            @Override
//            public void onResult(GeocodingResult[] result) {
//                county = result[0].addressComponents[3].longName;
//                state = result[0].addressComponents[4].shortName;
//                presResults = getPresidentialResults(state, county);
//                try {
//                    retrieveRepresentatives();
//                } catch (IOException e) {
//                    Log.e("ERROR GEO", e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable e) {
//                Log.i("ERROR ONCONNECTED", e.getMessage());
//            }
//        });
//    }
//
//    @Override
//    public void onConnected(Bundle bundle) {
//        Log.i("WE", "CONNECTED");
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            current_location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        }
//        if (current_location != null) {
//            Log.i("CURRENT_LOCATION", current_location.toString());
//            latitude = String.valueOf(current_location.getLatitude());
//            longitude = String.valueOf(current_location.getLongitude());
//        }
//    }
//
//    private String[] getPresidentialResults(String state, String county) {
//        Log.i("GETTING", "THE PRES RESULTS");
//        JSONArray json_array = retrieveJSONfile();
//        try {
//            for (int i = 0; i < json_array.length(); i++) {
//                JSONObject info = (JSONObject) json_array.getJSONObject(i);
//                if (state.equals(info.get("state-postal")) && (county.equals(info.get("county-name") + " " + "County"))) {
//                    String[] result = {state, county, info.get("obama-percentage").toString(),
//                                       info.get("romney-percentage").toString()};
//                    return result;
//                }
//            }
//        } catch (JSONException e) {
//
//        }
//        return null;
//    }
//
//    private JSONArray retrieveJSONfile() {
//        String json_string = "";
//        JSONArray jsonArray;
//
//        try {
//
//            InputStream input_stream = getAssets().open("election-county-2012.json");
//            int size = input_stream.available();
//            byte[] byte_buffer = new byte[size];
//            input_stream.read(byte_buffer);
//            input_stream.close();
//
//            json_string = new String(byte_buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//
//        try {
//            jsonArray = new JSONArray(json_string);
//            return jsonArray;
//        } catch (JSONException e) {
//
//        }
//        return null;
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//    }
//
//    protected void onStart() {
//        mGoogleApiClient.connect();
//        super.onStart();
//    }
//
//    protected void onStop() {
//        mGoogleApiClient.disconnect();
//        super.onStop();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
