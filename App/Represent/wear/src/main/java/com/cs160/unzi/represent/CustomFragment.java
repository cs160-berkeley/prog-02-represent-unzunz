package com.cs160.unzi.represent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomFragment extends CardFragment {

    private static String repName;
    private static String repParty;
    private static Bitmap repImage;
    private static String bioguideId;
    private static String termEndDate;

    static TextView repNameView;
    static TextView repPartyView;

    public static CustomFragment create(String rep_name,String rep_party, Bitmap rep_image,
                                        String bioguide_id, String term_end_date) {
        CustomFragment fragment = new CustomFragment();
        repName = rep_name;
        repParty = rep_party;
        repImage = rep_image;
        bioguideId = bioguide_id;
        termEndDate = term_end_date;
        return fragment;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_custom, container, false);

        repNameView = (TextView) root.findViewById(R.id.card_name);
        repPartyView = (TextView) root.findViewById(R.id.card_party);

        repNameView.setText(repName);
        repPartyView.setText(repParty);

        if (repParty.equals("D")) {
            repPartyView.setBackgroundResource(R.drawable.blue_circle_bg);
        } else {
            repPartyView.setBackgroundResource(R.drawable.red_circle_bg);
        }

        Drawable background = new BitmapDrawable(getResources(), repImage);
        root.setBackgroundDrawable(background);


        return root;
    }

}
