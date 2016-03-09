package com.cs160.unzi.represent;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
import com.squareup.okhttp.OkHttpClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import io.fabric.sdk.android.Fabric;



//public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
public class MainActivity extends AppCompatActivity {

    private String consumerKey = "Y2kIN6M8Z8kc0T5MZMaPPwhTx";
    private String consumerSecret = "V9qxwhzlP1XAQX6rKjdToXoC6XA4olURktLc0PNwkTyTlRiBKq";

    private EditText location_input;
    private final static String REPRESENTATIVES = "com.represent.REPRESENTATIVES";
//    private final static String URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    private final static String GEOCODE_KEY = "AIzaSyC-dGcJdgGc7KZzPBTFlH0dOMJtdyYxjHE";

    private Location CURRENT_LOCATION = null;
    private String mLatitudeText = null;
    private String mLongitudeText = null;
    private GoogleApiClient mGoogleApiClient;
    private GeoApiContext mGeoContext;
    private TwitterLoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(consumerKey, consumerSecret);
        Fabric.with(this, new Twitter(authConfig));

//        try {
//            TwitterLoginActivity.requestBearerToken();
//        } catch (Exception e) {
//            Log.i("ERROR", e.toString());
//        }
        new RetrieveTweets().execute("UGH");


//        try {
//            Log.i("HELLO?", "WHY NOT");
//            printCongressmen();
//        } catch (IOException e) {
//        }
    }

//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build();
//        }
//        mGeoContext = new GeoApiContext().setApiKey(GEOCODE_KEY);
//
//        location_input = (EditText) findViewById(R.id.location_input);
//        location_input.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
//        location_input.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View view, int keyCode, KeyEvent event) {
//            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                String location_string = location_input.getText().toString();
//                Log.i("LOCATION: ", location_string);
//                retrieveReps(location_string);
//                return true;
//            }
//                return false;
//            }
//        });
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

    public void printCongressmen() throws IOException {
        Log.i("HELLO", "CALL ME PLS");
        String sunlight_key = "946f65d6df5c4ae2b5f9ddb58fd867f5";
        String url_string = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=94704&apikey=" + sunlight_key;
        Log.i("HELLO", "GOODBYE");
        new RetrieveRepresentatives().execute(url_string);
    }



//    @Override
//    public void onConnected(Bundle bundle) {
//        Log.i("Retriving: ", "LOCATION??");
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            CURRENT_LOCATION = LocationServices.FusedLocationApi.getLastLocation(
//                    mGoogleApiClient);
//
//        } else {
//            Log.i("FOUND", "NOTHING");
//        }
//
//        if (CURRENT_LOCATION != null) {
//            LatLng location_input = new LatLng(CURRENT_LOCATION.getLatitude(), CURRENT_LOCATION.getLongitude());
//            Log.i("LATITUDE", String.valueOf(CURRENT_LOCATION.getLatitude()));
//            Log.i("LONGITUDE", String.valueOf(CURRENT_LOCATION.getLongitude()));
//            GeocodingApiRequest req = GeocodingApi.reverseGeocode(mGeoContext, location_input);
//            req.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
//                @Override
//                public void onResult(GeocodingResult[] result) {
//                    Log.i("COUNTY: ", result[0].addressComponents[3].longName);
//                }
//
//                @Override
//                public void onFailure(Throwable e) {
//                    Log.i("darn", e.getMessage());
//                }
//            });
//        }
//    }


//    @Override
//    public void onConnectionSuspended(int i) {

//    }

//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//    }
//
//
//    public ArrayList<String> retrieveRepNames(String location) {
//        ArrayList<String> reps = new ArrayList<>();
//        if (location.equals("94704")) {
//            reps.add("Barbara Lee");
//            reps.add("Jerry McNerney");
//
//            reps.add("Loni Hancock");
//        } else {
//            reps.add("Duncan L. Hunter");
//            reps.add("Ducnan D. Hunter");
//            reps.add("Scott Peters");
//        }
//        return reps;
//    }
//
//
//    public ArrayList<String> retrieveRepParties(String location) {
//        ArrayList<String> parties = new ArrayList<>();
//        if (location.equals("94704")) {
//            parties.add("D");
//            parties.add("D");
//            parties.add("D");
//        } else {
//            parties.add("R");
//            parties.add("R");
//            parties.add("D");
//        }
//        return parties;
//    }
//
//    public ArrayList<String> retrievePresResults(String location) {
//        ArrayList<String> results = new ArrayList<>();
//        if (location.equals("94704")) {
//            results.add("94794");
//            results.add("59.3%");
//            results.add("38.3%");
//        } else {
//            results.add("92123");
//            results.add("52.6");
//            results.add("45.0");
//        }
//        return results;
//    }
//
//    public ArrayList<String> retrieveRepEmails(String location) {
//        ArrayList<String> emails = new ArrayList<>();
//        if (location.equals("94704")) {
//            emails.add("barbs@gmail.com");
//            emails.add("jerry@gmail.com");
//            emails.add("loni@gmail.com");
//        } else {
//            emails.add("duncanl@gmail.com");
//            emails.add("duncand@gmail.com");
//            emails.add("scottpeters@gmail.com");
//        }
//        return emails;
//    }
//
//    public ArrayList<String> retrieveRepWebs(String location) {
//        ArrayList<String> webs = new ArrayList<>();
//        if (location.equals("94704")) {
//            webs.add("http://lee.house.gov");
//            webs.add("http://mcnerney.house.gov");
//            webs.add("http://hancock.house.gov");
//        } else {
//            webs.add("http://duncanl.house.gov");
//            webs.add("http://duncand.house.gov");
//            webs.add("http://scottpeters.house.gov");
//        }
//        return webs;
//    }
//
//    public ArrayList<String> retrieveRepTweets(String location) {
//        ArrayList<String> tweets = new ArrayList<>();
//        tweets.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
//        tweets.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
//        tweets.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
//        return tweets;
//    }
//    public void retrieveReps(String location) {
//        ArrayList<String> rep_names = retrieveRepNames(location);
//        ArrayList<String> rep_parties = retrieveRepParties(location);
//        ArrayList<String> rep_emails = retrieveRepEmails(location);
//        ArrayList<String> rep_webs = retrieveRepWebs(location);
//        ArrayList<String> rep_tweets = retrieveRepTweets(location);
//        ArrayList<String> presidential_results = retrievePresResults(location);
//
//        TwitterApiClient twitterApiClient = TwitterLoginActivity.getTwitterApiClient();
//        Intent toCongressional = new Intent(this, CongressionalViewActivity.class);
//        toCongressional.putStringArrayListExtra("REPRESENTATIVES", rep_names);
//        toCongressional.putStringArrayListExtra("PARTIES", rep_parties);
//        toCongressional.putStringArrayListExtra("EMAILS", rep_emails);
//        toCongressional.putStringArrayListExtra("WEBS", rep_webs);
//        toCongressional.putStringArrayListExtra("TWEETS", rep_tweets);
//        toCongressional.putStringArrayListExtra("PRESIDENTIAL", presidential_results);
//        startActivity(toCongressional);
//
//        Intent toWatch = new Intent(getBaseContext(), PhoneToWatchService.class);
////        toWatch.putStringArrayListExtra("REPRESENTATIVES", rep_names);
////        toWatch.putStringArrayListExtra("PARTIES", rep_parties);
////        toWatch.putStringArrayListExtra("PRESIDENTIAL", presidential_results);
//        toWatch.putExtra("LOCATION", location);
//        startService(toWatch);
//    }

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
