package com.cs160.unzi.represent;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by unzi on 3/8/16.
 */
public class RetrieveRepresentatives extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

    private Context mContext;

    public RetrieveRepresentatives(Context context) {
        mContext = context;
    }

    protected ArrayList<HashMap<String, String>> doInBackground(String... urls) {
        Log.i("HELLO", "HI");
        JSONObject reps_info = JSONparser.makeHttpRequest(urls[0]);
        ArrayList<HashMap<String, String>> reps_list = new ArrayList<HashMap<String, String>>();
        try {
            JSONArray members = (JSONArray) reps_info.get("results");
            for (int i = 0; i < members.length(); i++) {
                HashMap<String, String> rep_info = new HashMap<String, String>();
                JSONObject rep_json = (JSONObject) members.get(i);
                rep_info.put("bioguide_id", rep_json.get("bioguide_id").toString());
                rep_info.put("first_name", rep_json.get("first_name").toString());
                rep_info.put("last_name", rep_json.get("last_name").toString());
                rep_info.put("party", rep_json.get("party").toString());
                rep_info.put("oc_email", rep_json.get("oc_email").toString());
                rep_info.put("website", rep_json.get("website").toString());
                rep_info.put("term_end", rep_json.get("term_end").toString());
                rep_info.put("twitter_id", rep_json.get("twitter_id").toString());
                Log.i("twitter_id", rep_json.get("twitter_id").toString());
                reps_list.add(rep_info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reps_list;
    }

    protected void onPostExecute(ArrayList<HashMap<String, String>> reps_info) {
        Intent intent = new Intent(mContext, CongressionalViewActivity.class);
        intent.putExtra("ArrayList", reps_info);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);


//        Intent toCongressional = new Intent(this, CongressionalViewActivity.class);
//        toCongressional.putStringArrayListExtra("REPRESENTATIVES", rep_names);
//        toCongressional.putStringArrayListExtra("PARTIES", rep_parties);
//        toCongressional.putStringArrayListExtra("EMAILS", rep_emails);
//        toCongressional.putStringArrayListExtra("WEBS", rep_webs);
//        toCongressional.putStringArrayListExtra("TWEETS", rep_tweets);
//        toCongressional.putStringArrayListExtra("PRESIDENTIAL", presidential_results);
//        startActivity(toCongressional);
//
//        Intent toWatch = new Intent(getBaseContext(), PhoneToWatchService.class);
////        toWatch.putStringArrayListExtra("REPRESENTATIVES", rep_names);
////        toWatch.putStringArrayListExtra("PARTIES", rep_parties);
////        toWatch.putStringArrayListExtra("PRESIDENTIAL", presidential_results);
//        toWatch.putExtra("LOCATION", location);
//        startService(toWatch);

//        if (reps_info != null) {
//            Log.i("TWITTER: ", reps_info.get(0).get("twitter_id").toString());
//            Log.i("TWITTER: ", reps_info.get(1).get("twitter_id").toString());
//        }

    }
}
