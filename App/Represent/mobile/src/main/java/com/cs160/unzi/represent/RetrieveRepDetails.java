package com.cs160.unzi.represent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by unzi on 3/9/16.
 */
public class RetrieveRepDetails extends AsyncTask<String, Void, HashMap<String, ArrayList<HashMap<String, String>>>> {

    private Context mContext;
    private String repName;
    private String endTerm;
    private HashMap<String, Bitmap> image;

    public RetrieveRepDetails(Context context, String rep_name, String end_term, HashMap<String, Bitmap> image) {
        mContext = context;
        repName = rep_name;
        endTerm = end_term;
        this.image = image;
    }
    protected HashMap<String, ArrayList<HashMap<String, String>>> doInBackground(String... urls) {
        try {
            String bill_url = urls[0];
            String committee_url = urls[1];

            //introduced_on, official_title
            ArrayList<HashMap<String, String>> bill_info = new ArrayList<HashMap<String, String>>();
            JSONArray json_bills = (JSONArray) JSONparser.makeHttpRequest(bill_url).get("results");
            for (int i = 0; i < json_bills.length(); i++) {
                JSONObject bill_json = (JSONObject) json_bills.get(i);
                HashMap<String, String> bill = new HashMap<String, String>();
                if (bill_json.get("short_title").equals(null)) {
                    bill.put("official_title", bill_json.get("official_title").toString());
                } else {
                    bill.put("official_title", bill_json.get("short_title") .toString());
                }
                bill.put("introduced_on", bill_json.get("introduced_on").toString());
                bill_info.add(bill);
            }
            // subcommittee == false, name
            ArrayList<HashMap<String, String>> committee_info = new ArrayList<HashMap<String, String>>();
            JSONArray json_committees = (JSONArray) JSONparser.makeHttpRequest(committee_url).get("results");
            for (int i = 0; i < json_committees.length(); i++) {
                JSONObject committee_json = (JSONObject) json_committees.get(i);
                if (committee_json.get("subcommittee") == false) {
                    HashMap<String, String> committee = new HashMap<String, String>();
                    committee.put("name", committee_json.get("name").toString());
                    committee_info.add(committee);
                }
            }

            HashMap<String, ArrayList<HashMap<String, String>>> result = new HashMap<String, ArrayList<HashMap<String, String>>>();
            result.put("rep_bills", bill_info);
            result.put("rep_committees", committee_info);
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(HashMap<String, ArrayList<HashMap<String, String>>> result) {
        Intent intent = new Intent(mContext, DetailedViewActivity.class);
        intent.putExtra("billInfo", result.get("rep_bills"));
        intent.putExtra("committeeInfo", result.get("rep_committees"));
        intent.putExtra("name", repName);
        intent.putExtra("endTerm", endTerm);
        intent.putExtra("image", image);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}

