package com.cs160.unzi.represent;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
//import android.support.wearable.view.CardFragment;
import android.support.wearable.view.CardFragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class CustomFragment extends Fragment {

//    private static String repName;
//    private static String repParty;
//    private static String repImage;
//    private static String bioguideId;

//    static TextView repNameView;
//    static TextView repPartyView;

    public static CustomFragment create(String rep_name, String rep_party, String rep_image,
                                        String bioguide_id, String term_end) {
        CustomFragment fragment = new CustomFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", rep_name);
        bundle.putString("party", rep_party);
        bundle.putString("image", rep_image);
        bundle.putString("bioguide", bioguide_id);
        bundle.putString("termEnd", term_end);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_custom, container, false);

        TextView repNameView = (TextView) root.findViewById(R.id.card_name);
        TextView repPartyView = (TextView) root.findViewById(R.id.card_party);

        String name = getArguments().getString("name");
        String party = getArguments().getString("party");
        String image = getArguments().getString("image");
        String bioguide = getArguments().getString("bioguide");
        String termEnd = getArguments().getString("termEnd");

        repNameView.setText(name);
        repPartyView.setText(party);

        if (party.equals("D")) {
            repPartyView.setBackgroundResource(R.drawable.blue_circle_bg);
        } else {
            repPartyView.setBackgroundResource(R.drawable.red_circle_bg);
        }
//
        Drawable background = new BitmapDrawable(Resources.getSystem(), StringToBitMap(image));
        BoxInsetLayout box_container = (BoxInsetLayout) root.findViewById(R.id.fragment_container);

        box_container.setBackground(background);
        root.setOnClickListener(new RepOnClickListener(name, bioguide, termEnd));
        return root;
    }


    private Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public class RepOnClickListener implements View.OnClickListener {
        String repName;
        String bioguideId;
        String termEndDate;

        public RepOnClickListener(String name, String term_end_date, String bioguide_id) {
            repName = name;
            bioguideId = bioguide_id;
            termEndDate = term_end_date;
        }

        @Override
        public void onClick (View v) {
            Intent sendIntent = new Intent(getActivity(), WatchToPhoneService.class);
            sendIntent.putExtra("repName", repName);
            sendIntent.putExtra("repId", bioguideId);
            sendIntent.putExtra("termEnd", termEndDate);
            getActivity().startService(sendIntent);
        }
    }
}
