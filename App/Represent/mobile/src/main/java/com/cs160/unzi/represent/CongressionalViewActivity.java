package com.cs160.unzi.represent;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.HashMap;


public class CongressionalViewActivity extends AppCompatActivity {

//    private HashMap<String, String> twitterIds = new HashMap<String, String>();
//    private HashMap<String, String> mostRecentTweets = new HashMap<String, String>();
//    private HashMap<String, String> repPictures = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);
        ArrayList<HashMap<String, String>> repsInfo = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("repsInfo");
        HashMap<String, String> mostRecentTweets = (HashMap<String, String>) getIntent().getSerializableExtra("recentTweets");
        HashMap<String, String> repPictures = (HashMap<String, String>) getIntent().getSerializableExtra("repPictures");

        Log.i("WE HEREEE", repsInfo.toString());
        Log.i("WE HEREEE", mostRecentTweets.toString());
        Log.i("WE HEREEE", repPictures.toString());
//        Intent intent = getIntent();
//        ArrayList<String> rep_names = intent.getStringArrayListExtra("REPRESENTATIVES");
//        ArrayList<String> rep_parties = intent.getStringArrayListExtra("PARTIES");
//        ArrayList<String> rep_emails = intent.getStringArrayListExtra("EMAILS");
//        ArrayList<String> rep_webs = intent.getStringArrayListExtra("WEBS");
//        ArrayList<String> rep_tweets = intent.getStringArrayListExtra("TWEETS");
//        ArrayList<String> presidential_results = intent.getStringArrayListExtra("REPRESENTATIVES");


//        String full_name;
//        for (HashMap<String, String> rep : reps_info) {
//            full_name = rep.get("first_name") + " " + rep.get("last_name");
//            mostRecentTweets.put(full_name, "");
//            repPictures.put(full_name, "");
//            twitterIds.put(full_name, rep.get("twitter_id"));
//        }
//        if (!twitterIds.isEmpty()) {
//            RetrieveTweets.AsyncResponse async_response = new RetrieveTweets.AsyncResponse() {
//                @Override
//                public void processFinish(ArrayList<HashMap<String, String>> output) {
//                    mostRecentTweets = output.get(0);
//                    Log.i("TWEETS", mostRecentTweets.keySet().toString());
//                    repPictures = output.get(1);
//                }
//            };
//            RetrieveTweets tweetsAsync = new RetrieveTweets(async_response, bearerToken, twitterIds, mostRecentTweets, repPictures);
//            tweetsAsync.execute();
//        }

        LinearLayout congressionalLayout = (LinearLayout) findViewById(R.id.congressional_content);
        String full_name;
        if (repsInfo != null) {
            for (HashMap<String, String> rep : repsInfo) {
                full_name = rep.get("first_name") + " " + rep.get("last_name");
                View view = getLayoutInflater().inflate(R.layout.rep_view, congressionalLayout, false);
                TextView nameView = (TextView) view.findViewById(R.id.rep_name);
                TextView emailView = (TextView) view.findViewById(R.id.rep_email);
                TextView webView = (TextView) view.findViewById(R.id.rep_web);
                TextView tweetView = (TextView) view.findViewById(R.id.rep_tweet);
                nameView.setText(full_name + " " + rep.get("party"));
                emailView.setText(rep.get("oc_email"));
                tweetView.setText(mostRecentTweets.get(full_name).toString());
                webView.setText(rep.get("website"));

                RepOnClickListener click_listener = new RepOnClickListener();
                click_listener.setRepName(full_name);
                view.setOnClickListener(click_listener);

                congressionalLayout.addView(view);
            }
        }
    }


    public class RepOnClickListener implements OnClickListener {
        String rep_name;

        public void setRepName(String name) {
            this.rep_name = name;
        }

        @Override
        public void onClick(View view) {
            retrieveDetails(rep_name);
        }
    }

    public void retrieveDetails(String rep_name) {
//        Intent intent = new Intent(this, DetailedViewActivity.class);
//        intent.putExtra("SELECTED_REP", rep_name);
//        startActivity(intent);
    };


}
