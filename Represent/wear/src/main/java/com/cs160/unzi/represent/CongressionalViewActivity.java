package com.cs160.unzi.represent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CongressionalViewActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);

        Intent intent = getIntent();
        ArrayList<String> extras = intent.getStringArrayListExtra("REGION_REPS");

        LinearLayout hello = (LinearLayout) findViewById(R.id.watch_congressional);
//        mTextView = (TextView) findViewById(R.id.reps_output);
//        mTextView.setText("WHYS IS THIS NOT WORKIGN HELLO SLKDFJSLKDJF BYSDFLKSDF???? WHY IS DIS NOT NOT WORKI?SDFSDF?");
//        Log.i("We in here", mTextView.getText().toString());

//        Log.i("EXTRAS? ", Integer.toString(extras.size()));

        if (extras != null) {
            for (String member : extras) {
//                Log.i("MEMBER: ", member);
                TextView new_rep = new TextView(this);
                new_rep.setText(member);
                RepOnClickListener click_listener = new RepOnClickListener();
                click_listener.setRepName(member);
                new_rep.setOnClickListener(click_listener);
                hello.addView(new_rep);
            }
        }
    }

    public class RepOnClickListener implements View.OnClickListener {
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
        Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
        sendIntent.putExtra("SELECTED_REP", rep_name);
        startService(sendIntent);
    };
}
