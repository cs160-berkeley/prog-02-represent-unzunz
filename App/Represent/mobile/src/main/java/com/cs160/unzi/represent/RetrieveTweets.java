package com.cs160.unzi.represent;

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

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by unzi on 3/8/16.
 */
public class RetrieveTweets extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

    private static HttpsURLConnection twitterConnection;
    private static StringBuilder result;
    protected ArrayList<HashMap<String, String>> doInBackground(String... urls) {
        Log.i("HELLO", "HI");
        try {
            String bearer_token = TwitterLoginActivity.requestBearerToken();
//            URL user_url = new URL("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=oonzunz&count=2");
            URL user_url = new URL("https://api.twitter.com/1.1/statuses/user_timeline.json?count=2&screen_name=RepBarbaraLee");
            twitterConnection = (HttpsURLConnection) user_url.openConnection();
            twitterConnection.setRequestMethod("GET");
            twitterConnection.setRequestProperty("Host", "api.twitter.com");
            twitterConnection.setRequestProperty("User-Agent", "Represent");
            twitterConnection.setRequestProperty("Authorization", "Bearer " + bearer_token );
//            twitterConnection.setRequestProperty("Accept-Encoding", "gzip");
            twitterConnection.setDoOutput(false);
            twitterConnection.setConnectTimeout(15000);
            twitterConnection.connect();

        } catch (Exception e) {
            Log.i("ERROR", e.toString());
        }

        try {
            Log.i("HI", "H????");
            int status = twitterConnection.getResponseCode();
            Log.i("STATUS", String.valueOf(status));
            Log.i("MESSAGE", twitterConnection.getResponseMessage());

            InputStream in = new BufferedInputStream(twitterConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                Log.i("LINE", line);
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        twitterConnection.disconnect();

        try {
            JSONArray json_object = new JSONArray(result.toString());
            Log.i("JSON", json_object.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        ArrayList<HashMap<String, String>> reps_list = new ArrayList<HashMap<String, String>>();
        return null;
    }

    protected void onPostExecute(ArrayList<HashMap<String, String>> reps_info) {
        if (reps_info != null) {

        }

    }
}
