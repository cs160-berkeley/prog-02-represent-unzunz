package com.cs160.unzi.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        ArrayList<String> message = intent.getStringArrayListExtra("REPRESENTATIVES");
        LinearLayout hello = (LinearLayout) findViewById(R.id.congressional_content);
        if (message != null) {
            for (String member : message) {
                TextView new_rep = new TextView(this);
                new_rep.setText(member);
                RepOnClickListener click_listener = new RepOnClickListener();
                click_listener.setRepName(member);
                new_rep.setOnClickListener(click_listener);
                hello.addView(new_rep);
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
