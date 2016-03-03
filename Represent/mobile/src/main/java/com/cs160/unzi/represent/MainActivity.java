package com.cs160.unzi.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText location_input;
    public final static String REPRESENTATIVES = "com.represent.REPRESENTATIVES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location_input = (EditText) findViewById(R.id.location_input);
        location_input.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        location_input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String location_string = location_input.getText().toString();
                    Log.i("LOCATION: ", location_string);
                    retrieveReps(location_string);
                    return true;
                }
                return false;
            }
        });
    }

    public ArrayList<String> retrieveRepNames(String location) {
        ArrayList<String> reps = new ArrayList<>();
        if (location.equals("94704")) {
            reps.add("Barbara Lee");
            reps.add("Jerry McNerney");
            reps.add("Loni Hancock");
        } else {
            reps.add("Duncan L. Hunter");
            reps.add("Ducnan D. Hunter");
            reps.add("Scott Peters");
        }
        return reps;
    }

    public ArrayList<String> retrieveRepParties(String location) {
        ArrayList<String> parties = new ArrayList<>();
        if (location.equals("94704")) {
            parties.add("Democratic");
            parties.add("Democratic");
            parties.add("Democratic");
        } else {
            parties.add("Republican");
            parties.add("Republican");
            parties.add("Democratic");
        }
        return parties;
    }

    public ArrayList<String> retrievePresResults(String location) {
        ArrayList<String> results = new ArrayList<>();
        if (location.equals("94704")) {
            results.add("59.3%");
            results.add("38.3%");
        } else {
            results.add("52.6");
            results.add("45.0");
        }
        return results;
    }

    public ArrayList<String> retrieveRepEmails(String location) {
        ArrayList<String> emails = new ArrayList<>();
        if (location.equals("94704")) {
            emails.add("barbs@gmail.com");
            emails.add("jerry@gmail.com");
            emails.add("loni@gmail.com");
        } else {
            emails.add("duncanl@gmail.com");
            emails.add("duncand@gmail.com");
            emails.add("scottpeters@gmail.com");
        }
        return emails;
    }

    public ArrayList<String> retrieveRepWebs(String location) {
        ArrayList<String> webs = new ArrayList<>();
        if (location.equals("94704")) {
            webs.add("http://lee.house.gov");
            webs.add("http://mcnerney.house.gov");
            webs.add("http://hancock.house.gov");
        } else {
            webs.add("http://duncanl.house.gov");
            webs.add("http://duncand.house.gov");
            webs.add("http://scottpeters.house.gov");
        }
        return webs;
    }

    public ArrayList<String> retrieveRepTweets(String location) {
        ArrayList<String> tweets = new ArrayList<>();
        tweets.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        tweets.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        tweets.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        return tweets;
    }
    public void retrieveReps(String location) {
        ArrayList<String> rep_names = retrieveRepNames(location);
        ArrayList<String> rep_parties = retrieveRepParties(location);
        ArrayList<String> rep_emails = retrieveRepEmails(location);
        ArrayList<String> rep_webs = retrieveRepWebs(location);
        ArrayList<String> rep_tweets = retrieveRepTweets(location);
        ArrayList<String> presidential_results = retrievePresResults(location);



        Intent toCongressional = new Intent(this, CongressionalViewActivity.class);
        toCongressional.putStringArrayListExtra("REPRESENTATIVES", rep_names);
        toCongressional.putStringArrayListExtra("PARTIES", rep_parties);
        toCongressional.putStringArrayListExtra("EMAILS", rep_emails);
        toCongressional.putStringArrayListExtra("WEBS", rep_webs);
        toCongressional.putStringArrayListExtra("TWEETS", rep_tweets);
        toCongressional.putStringArrayListExtra("PRESIDENTIAL", presidential_results);
        startActivity(toCongressional);

//        Intent toWatch = new Intent(getBaseContext(), PhoneToWatchService.class);
//        toWatch.putStringArrayListExtra("REPRESENTATIVES", rep_names);
//        toWatch.putStringArrayListExtra("PARTIES", rep_parties);
//        toWatch.putStringArrayListExtra("PRESIDENTIAL", presidential_results);
//        startService(toWatch);
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
