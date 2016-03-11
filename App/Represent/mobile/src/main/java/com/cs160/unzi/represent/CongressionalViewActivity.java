package com.cs160.unzi.represent;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
    private final String sunlightKey = "946f65d6df5c4ae2b5f9ddb58fd867f5";
    private HashMap<String, String> repsId = new HashMap<String, String>();
    private HashMap<String, String> repsEndTerm = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);
        ArrayList<HashMap<String, String>> repsInfo = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("repsInfo");
        HashMap<String, String> mostRecentTweets = (HashMap<String, String>) getIntent().getSerializableExtra("recentTweets");
        HashMap<String, Bitmap> repPictures = (HashMap<String, Bitmap>) getIntent().getSerializableExtra("repPictures");
//
        Log.i("WE HEREEE", repsInfo.toString());
        Log.i("WE HEREEE", mostRecentTweets.toString());
        Log.i("WE HEREEE", repPictures.toString());

        LinearLayout congressionalLayout = (LinearLayout) findViewById(R.id.congressional_content);
        String full_name;
        if (repsInfo != null) {
            for (HashMap<String, String> rep : repsInfo) {
                full_name = rep.get("first_name") + " " + rep.get("last_name");
                repsId.put(full_name, rep.get("bioguide_id"));
                repsEndTerm.put(full_name, rep.get("term_end"));

                View view = getLayoutInflater().inflate(R.layout.rep_view, congressionalLayout, false);
                TextView nameView = (TextView) view.findViewById(R.id.rep_name);
                TextView emailView = (TextView) view.findViewById(R.id.rep_email);
                TextView webView = (TextView) view.findViewById(R.id.rep_web);
                TextView tweetView = (TextView) view.findViewById(R.id.rep_tweet);
                ImageView picView = (ImageView) view.findViewById(R.id.rep_pic);

                if (repPictures.get(full_name) != null) {
                    picView.setImageBitmap(repPictures.get(full_name));
                }

                if (!mostRecentTweets.get(full_name).equals("")) {
                    tweetView.setText(mostRecentTweets.get(full_name).toString());
                }

                nameView.setText(full_name + " " + rep.get("party"));
                emailView.setText(rep.get("oc_email"));
                webView.setText(rep.get("website"));

                RepOnClickListener click_listener = new RepOnClickListener();
                click_listener.setRepName(full_name);
                view.setOnClickListener(click_listener);

                congressionalLayout.addView(view);
            }
        }
    }


    public class RepOnClickListener implements OnClickListener {
        String repName;
        public void setRepName(String rep_name) {
            this.repName = rep_name;
        }

        @Override
        public void onClick(View view) {
            retrieveDetails(repName);
        }
    }

    public void retrieveDetails(String repName) {
        String rep_id = repsId.get(repName);
        String end_term = repsEndTerm.get(repName);
        String bill_url = "https://congress.api.sunlightfoundation.com/bills?sponsor_id=" + rep_id + "&apikey=" + sunlightKey;
        String committee_url = "https://congress.api.sunlightfoundation.com/committees?member_ids=" + rep_id + "&apikey=" + sunlightKey;
        new RetrieveRepDetails(this.getBaseContext(), repName, end_term).execute(bill_url, committee_url);
    };
}
