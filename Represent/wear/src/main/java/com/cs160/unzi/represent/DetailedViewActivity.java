package com.cs160.unzi.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailedViewActivity extends Activity {

    private TextView mTextView;

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
