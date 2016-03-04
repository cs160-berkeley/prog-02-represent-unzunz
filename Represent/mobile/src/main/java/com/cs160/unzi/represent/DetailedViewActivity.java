package com.cs160.unzi.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailedViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Log.i("HERE", "HERE");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String name = extras.getString("SELECTED_REP");
            TextView name_view = (TextView) findViewById(R.id.name);
            name_view.setText(name);
        }

        String[] committees = {"House Committee on Appropriations",
                               "House Committee of the Budget"};
        String[] bills = {"Food Assistance to Improve Reintegration Act of 2013",
                          "Food Assistane to Improve Reintegration Act of 2013"};

        LinearLayout committees_view = (LinearLayout) findViewById(R.id.committees);
        LinearLayout bills_view = (LinearLayout) findViewById(R.id.bills);

        int index = 0;
        for (String committee : committees) {
            TextView committee_view = new TextView(this);
            TextView bill_view = new TextView(this);
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams();
            committee_view.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
            committee_view.setHeight(142);
            committee_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            committee_view.setText(committee);
            bill_view.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            bill_view.setHeight(142);
            bill_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            bill_view.setText(bills[index]);

            committees_view.addView(committee_view);
            bills_view.addView(bill_view);
            index++;
        }
    }

}
