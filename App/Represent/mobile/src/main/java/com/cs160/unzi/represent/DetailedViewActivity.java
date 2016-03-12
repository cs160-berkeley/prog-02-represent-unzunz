package com.cs160.unzi.represent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        HashMap<String, Bitmap> image = (HashMap<String, Bitmap>) getIntent().getSerializableExtra("image");

        TextView rep_name = (TextView) findViewById(R.id.detailed_name);
        TextView end_term = (TextView) findViewById(R.id.end_term);

        Bitmap rep_image = image.get(repName);
        if (rep_image != null) {
            ImageView picView = (ImageView) findViewById(R.id.detail_pic);
            picView.setImageBitmap(rep_image);
        }
        rep_name.setText(repName);
        Log.i("ENDTER", endTerm);
        end_term.setText(endTerm);

        Log.i("HSDKLFJ", billInfo.toString());
        Log.i("HSDKLFJ", committeeInfo.toString());
        LinearLayout committees_view = (LinearLayout) findViewById(R.id.committees);
        LinearLayout bills_view = (LinearLayout) findViewById(R.id.bills);

        for (HashMap<String, String> committee : committeeInfo) {
            View committee_view = getLayoutInflater().inflate(R.layout.committee_row, bills_view, false);
            TextView committeeName = (TextView) committee_view.findViewById(R.id.committee_name);
            Log.i("COMMITTEE", committee.get("name"));
            committeeName.setText(committee.get("name"));
            committees_view.addView(committee_view);
        }

        for (HashMap<String, String> bill : billInfo) {
            View bill_view = getLayoutInflater().inflate(R.layout.bill_row, bills_view, false);
            TextView billName = (TextView) bill_view.findViewById(R.id.bill_name);
            TextView billDate = (TextView) bill_view.findViewById(R.id.bill_date);
            billDate.setText(bill.get("introduced_on"));
            billName.setText(bill.get("official_title"));
            bills_view.addView(bill_view);
        }
    }


    private Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
