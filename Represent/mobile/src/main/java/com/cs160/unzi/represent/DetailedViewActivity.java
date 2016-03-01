package com.cs160.unzi.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailedViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {

            String name = extras.getString("SELECTED_REP");

            LinearLayout hello = (LinearLayout) findViewById(R.id.detailed_content);
            TextView new_rep = new TextView(this);
            new_rep.setText(name);
            hello.addView(new_rep);
        }
    }

}
