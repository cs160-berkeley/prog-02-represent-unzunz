package com.cs160.unzi.represent;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.twitter.sdk.android.Twitter;

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
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by unzi on 3/8/16.
 */
public class RetrieveTweets extends AsyncTask<String, Void, String> {

    private Context mContext;
    private static HttpsURLConnection twitterConnection;
    private static StringBuilder result;
    private static String bearerToken;
    private static HashMap<String, String> mostRecentTweets;
    private static HashMap<String, String> repPictures;
    private static HashMap<String, String> twitterIds;
    private static ArrayList<HashMap<String, String>> repsInfo;
//    public AsyncResponse delegate = null;

//    public interface AsyncResponse {
//        void processFinish(ArrayList<HashMap<String, String>> output);
//    }

    public RetrieveTweets(Context context, String bearer_token, HashMap<String, String> twitter_ids, HashMap<String, String> most_recent_tweets, HashMap<String, String> rep_pictures, ArrayList<HashMap<String, String>> reps_info) {
        bearerToken = bearer_token;
        twitterIds = twitter_ids;
        mostRecentTweets = most_recent_tweets;
        repsInfo = reps_info;
        mContext = context;
        repPictures = rep_pictures;
    }

    protected String doInBackground(String... urls) {

        for (HashMap.Entry<String, String> rep : twitterIds.entrySet()) {
            try {
                URL user_url = new URL("https://api.twitter.com/1.1/statuses/user_timeline.json?count=1&screen_name=" + rep.getValue());
                twitterConnection = (HttpsURLConnection) user_url.openConnection();
                twitterConnection.setRequestMethod("GET");
                twitterConnection.setRequestProperty("Host", "api.twitter.com");
                twitterConnection.setRequestProperty("User-Agent", "Represent");
                twitterConnection.setRequestProperty("Authorization", "Bearer " + bearerToken);
                twitterConnection.setDoOutput(false);
                twitterConnection.setConnectTimeout(15000);
                twitterConnection.connect();

            } catch (Exception e) {
                Log.i("ERROR", e.toString());
            }

            try {
                int status = twitterConnection.getResponseCode();
//                Log.i("STATUS", String.valueOf(status));
//                Log.i("MESSAGE", twitterConnection.getResponseMessage());

                InputStream in = new BufferedInputStream(twitterConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            twitterConnection.disconnect();

            try {
                JSONArray json_object = new JSONArray(result.toString());
                JSONObject object_0 = json_object.getJSONObject(0);
                JSONObject user = (JSONObject) object_0.get("user");
                String image_url = user.get("profile_image_url").toString();
                String text = object_0.get("text").toString();


                mostRecentTweets.put(rep.getKey(), text);
                repPictures.put(rep.getKey(), image_url);
//
//                Log.i("FIELDs", object_0.names().toString());
//                Log.i("TEXT", text);
//                Log.i("IMage", image_url);

            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        }
        return "hi";
    }

    protected void onPostExecute(String something) {
        Intent intent = new Intent(mContext, CongressionalViewActivity.class);
        intent.putExtra("repsInfo", repsInfo);
        intent.putExtra("recentTweets", mostRecentTweets);
        intent.putExtra("repPictures", repPictures);
//        intent.putExtra("BearerToken", bearerToken);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
