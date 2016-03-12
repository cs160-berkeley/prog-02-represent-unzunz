package com.cs160.unzi.represent;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import io.fabric.sdk.android.Fabric;
public class RetrieveContent extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private final String GeocodeKey = "AIzaSyDNoKsb7a8ue3TUDQ9ueCrGcVpUN9H0Ndo";
    private final String sunlightKey = "946f65d6df5c4ae2b5f9ddb58fd867f5";
    private final String twitterConsumerKey = "Y2kIN6M8Z8kc0T5MZMaPPwhTx";
    private final String twitterConsumerSecret = "V9qxwhzlP1XAQX6rKjdToXoC6XA4olURktLc0PNwkTyTlRiBKq";
    private String bearerToken = "";

    private final String zipCodeSearch = "search-type-zip-code";
    private final String latLongSearch = "search-type-lat-long";

    String search_type;
    private String state;
    private String county;
    private String zipCode;
    private Double latitude;
    private Double longitude;
    private String[] presResults;
    private boolean fromShake;
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private GeoApiContext mGeoContext;

    private HashMap<String, String> watchContent;
    private HashMap<String, String> repPics;

    public RetrieveContent() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(twitterConsumerKey, twitterConsumerSecret);
        Fabric.with(this, new Twitter(authConfig));
        mContext = this;

        RetrieveTwitterBearerToken TwitterAsyncTask = new RetrieveTwitterBearerToken(new RetrieveTwitterBearerToken.AsyncResponse() {
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
                    .build();
        }

        mGoogleApiClient.connect();

        mGeoContext = new GeoApiContext().setApiKey(GeocodeKey);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        final String location_string = extras.getString("LOCATION");
        if (extras.getString("latitude") != null && extras.getString("longitude") != null) {
            latitude = Double.parseDouble(extras.getString("latitude"));
            longitude = Double.parseDouble(extras.getString("longitude"));
        }

        fromShake = extras.getBoolean("fromShake");
        if (location_string.equals("")) {
            search_type = latLongSearch;
        } else {
            search_type = zipCodeSearch;
            zipCode = location_string;
        }

        setLocation(search_type);
        return START_STICKY;

    }


    public void retrieveRepresentatives() throws IOException {
        String url_string;
        if (search_type == zipCodeSearch) {
            url_string = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=" + zipCode + "&apikey=" + sunlightKey;
        } else {
            url_string = "https://congress.api.sunlightfoundation.com/legislators/locate?latitude=" +
                    latitude + "&longitude=" + longitude + "&apikey=" + sunlightKey;
        }
        RetrieveRepresentatives asyncTask = new RetrieveRepresentatives(this.getBaseContext(), bearerToken, presResults, fromShake);
        asyncTask.execute(url_string);
    }

    public void setLocation(String search_type) {
        GeocodingApiRequest geocode_request;
        if (search_type == zipCodeSearch) {
            geocode_request = GeocodingApi.geocode(mGeoContext, zipCode);
        } else {
            LatLng location_input = new LatLng(latitude, longitude);
            geocode_request = GeocodingApi.reverseGeocode(mGeoContext, location_input);
        }
        geocode_request.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
            @Override
            public void onResult(GeocodingResult[] result) {
                Log.i("0", result[0].addressComponents.toString());
                for (AddressComponent component : result[0].addressComponents) {
                    AddressComponentType[] component_types = component.types;
                    for (int i = 0; i < component_types.length; i++) {
                        AddressComponentType component_type = component_types[i];
                        Log.i("PLS", String.valueOf(component_type));
                        if (component_type.equals(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1)) {
                            state = component.shortName;
                            Log.i("STATE", state);
                        } else if (component_type.equals(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2)) {
                            county = component.longName;
                            Log.i("county", county);
                        }
                    }
                }
                Log.i("COUNTY", county);
                Log.i("STATE", state);
                presResults = getPresidentialResults(state, county);
                if (presResults == null) {
                    if (fromShake) {
                        Log.i("1HERE", " ???");
                        Intent needLocation = new Intent(mContext, PhoneListenerService.class);
                        startService(needLocation);
                    } else {
                        Log.i("HERE", "??? ");
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mContext.startActivity(intent);
                    }
                }
                try {
                    retrieveRepresentatives();
                } catch (IOException e) {
                    Log.e("ERROR GEO", e.getMessage());

                }
            }

            @Override
            public void onFailure(Throwable e) {
                Log.i("HUH", "JU");
                Log.i("ERROR ONCONNECTED", e.getMessage());
                Intent needLocation = new Intent(mContext, PhoneListenerService.class);
                mContext.startService(needLocation);
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    private String[] getPresidentialResults(String state, String county) {
        Log.i("GETTING", "THE PRES RESULTS");
        Log.i("COUNTY", county);
        Log.i("STATE", state);
        JSONArray json_array = retrieveJSONfile();
        try {
            for (int i = 0; i < json_array.length(); i++) {
                JSONObject info = (JSONObject) json_array.getJSONObject(i);
                if (state.equals(info.get("state-postal")) && (county.equals(info.get("county-name") + " " + "County"))) {
                    String[] result = {state, county, info.get("obama-percentage").toString(),
                            info.get("romney-percentage").toString()};
                    Log.i("PRES", String.valueOf(result[0]));
                    Log.i("PRES", String.valueOf(result[1]));
                    Log.i("PRES", String.valueOf(result[2]));
                    return result;
                }
            }
        } catch (JSONException e) {
            Log.i("ERROR", "PRESIDENTS NOT RETRIEVED");
        }
        return null;
    }

    private JSONArray retrieveJSONfile() {
        String json_string = "";
        JSONArray jsonArray;

        try {

            InputStream input_stream = getAssets().open("election-county-2012.json");
            int size = input_stream.available();
            byte[] byte_buffer = new byte[size];
            input_stream.read(byte_buffer);
            input_stream.close();

            json_string = new String(byte_buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            jsonArray = new JSONArray(json_string);
            return jsonArray;
        } catch (JSONException e) {

        }
        return null;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
