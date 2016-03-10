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
import java.util.HashMap;

public class DetailedViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        Intent intent = getIntent();

        //introduced_on, official_title
        ArrayList<HashMap<String, String>> billInfo = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("billInfo");
        //name
        ArrayList<HashMap<String, String>> committeeInfo = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("committeeInfo");
        String repName = (String) getIntent().getSerializableExtra("name");
        String endTerm = (String) getIntent().getSerializableExtra("endTerm");

        TextView rep_name = (TextView) findViewById(R.id.detailed_name);
        TextView end_term = (TextView) findViewById(R.id.end_term);

        rep_name.setText(repName);
        Log.i("ENDTER", endTerm);
        end_term.setText(endTerm);

        Log.i("HSDKLFJ", billInfo.toString());
        Log.i("HSDKLFJ", committeeInfo.toString());
        LinearLayout committees_view = (LinearLayout) findViewById(R.id.committees);
        LinearLayout bills_view = (LinearLayout) findViewById(R.id.bills);

        int index = 0;
        for (HashMap<String, String> committee : committeeInfo) {
            TextView committee_view = new TextView(this);

//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams();
            committee_view.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            committee_view.setHeight(142);
            committee_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            committee_view.setText(committee.get("name"));
            committees_view.addView(committee_view);

        }
        for (HashMap<String, String> bill : billInfo) {
            TextView bill_view = new TextView(this);
            bill_view.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            bill_view.setHeight(142);
            bill_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            bill_view.setText(bill.get("introduced_on") + " " + bill.get("official_title"));
            bills_view.addView(bill_view);
        }
    }

}
