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
    private String bearerToken;
    public AsyncResponse delegate = null;

    public interface AsyncResponse {
        void processFinish(HashMap<String, String> watch_content, HashMap<String, String> rep_pics);
    }

    public RetrieveRepresentatives(AsyncResponse delegeate, Context context, String bearer_token) {
        this.delegate = delegate;
        mContext = context;
        bearerToken = bearer_token;
    }

    protected ArrayList<HashMap<String, String>> doInBackground(String... urls) {
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
                rep_info.put("term_end", rep_json.get("term_end").toString());
                reps_list.add(rep_info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reps_list;
    }

    protected void onPostExecute(ArrayList<HashMap<String, String>> reps_info) {

        HashMap<String, String> twitterIds = new HashMap<String, String>();
        HashMap<String, String> mostRecentTweets = new HashMap<String, String>();
        HashMap<String, String> repPictures = new HashMap<String, String>();
        HashMap<String, String[]> watchContent = new HashMap<String,String[]>();
        String full_name;

        for (HashMap<String, String> rep : reps_info) {
            full_name = rep.get("first_name") + " " + rep.get("last_name");
            mostRecentTweets.put(full_name, "");
            repPictures.put(full_name, "");
            twitterIds.put(full_name, rep.get("twitter_id"));
            String[] party_pic = new String[2];
            party_pic[0] = rep.get("party");
            watchContent.put(full_name, party_pic);
        }
        Log.i("RETRIEVEREPS", "ok");
        Log.i("RETRIEVEREPS", twitterIds.toString());
        if (!twitterIds.isEmpty()) {
            Log.i("RETRIEVEREPS", "ok???");
            RetrieveTweets tweetsAsync = new RetrieveTweets(mContext, bearerToken, twitterIds, mostRecentTweets, repPictures, reps_info, watchContent);
            tweetsAsync.execute();
        }
    }
}
