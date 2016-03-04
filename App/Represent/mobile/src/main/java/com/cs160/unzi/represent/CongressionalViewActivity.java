package com.cs160.unzi.represent;

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

import java.util.ArrayList;

public class CongressionalViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);

        Intent intent = getIntent();
        ArrayList<String> rep_names = intent.getStringArrayListExtra("REPRESENTATIVES");
        ArrayList<String> rep_parties = intent.getStringArrayListExtra("PARTIES");
        ArrayList<String> rep_emails = intent.getStringArrayListExtra("EMAILS");
        ArrayList<String> rep_webs = intent.getStringArrayListExtra("WEBS");
        ArrayList<String> rep_tweets = intent.getStringArrayListExtra("TWEETS");
        ArrayList<String> presidential_results = intent.getStringArrayListExtra("REPRESENTATIVES");

        LinearLayout congressionalLayout = (LinearLayout) findViewById(R.id.congressional_content);


        if (rep_names != null) {
            int index = 0;
            for (String name : rep_names) {
                View view = getLayoutInflater().inflate(R.layout.rep_view, congressionalLayout, false);
//                View view = inflater.inflate(R.layout.rep_view, null);
                TextView nameView = (TextView) view.findViewById(R.id.rep_name);
                TextView emailView = (TextView) view.findViewById(R.id.rep_email);
                TextView webView = (TextView) view.findViewById(R.id.rep_web);
                TextView tweetView = (TextView) view.findViewById(R.id.rep_tweet);

                nameView.setText(rep_names.get(index) + "(" + rep_parties.get(index) + ")");
                emailView.setText(rep_emails.get(index));
                tweetView.setText(rep_tweets.get(index));
                webView.setText(rep_webs.get(index));


                RepOnClickListener click_listener = new RepOnClickListener();
                click_listener.setRepName(name);
                view.setOnClickListener(click_listener);

                congressionalLayout.addView(view);
                index++;
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
        Intent intent = new Intent(this, DetailedViewActivity.class);
        intent.putExtra("SELECTED_REP", rep_name);
        startActivity(intent);
    };


}
