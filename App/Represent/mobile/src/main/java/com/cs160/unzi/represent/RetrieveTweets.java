package com.cs160.unzi.represent;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.twitter.sdk.android.Twitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
    private static HashMap<String, Bitmap> repBitmapPics;
    private static HashMap<String, String> twitterIds;
    private static ArrayList<HashMap<String, String>> repsInfo;
    private static HashMap<String,String[]> watchContent;

    public RetrieveTweets(Context context, String bearer_token, HashMap<String, String> twitter_ids,
                          HashMap<String, String> most_recent_tweets,
                          HashMap<String, Bitmap> rep_bitmap_pics,
                          ArrayList<HashMap<String, String>> reps_info,
                          HashMap<String,String[]> watch_content) {
        bearerToken = bearer_token;
        twitterIds = twitter_ids;
        mostRecentTweets = most_recent_tweets;
        repsInfo = reps_info;
        mContext = context;
        repBitmapPics = rep_bitmap_pics;
        watchContent = watch_content;
    }

    protected String doInBackground(String... urls) {
        Log.i("RetrieveTweets", "ok>>>>");
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
                image_url = image_url.replace("_normal","");
                String text = object_0.get("text").toString();

                if (text.length() > 72) {
                    String short_tweet = text.substring(0, 70);
                    int i = short_tweet.lastIndexOf(" ");
                    text = "\"" + short_tweet.substring(0, i) + " " + "..." + "\"";
                } else {
                    text = " " + text + " ";
                }
                mostRecentTweets.put(rep.getKey(), text);

                try {
                    URL url = new URL(image_url);
                    Bitmap bitmap_output = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Bitmap bitmap_scaled = scaleDownBitmap(bitmap_output, 30, mContext);
                    ByteArrayOutputStream byte_ouput_stream = new ByteArrayOutputStream();
                    bitmap_scaled.compress(Bitmap.CompressFormat.PNG, 100, byte_ouput_stream);
                    byte[] bytes = byte_ouput_stream.toByteArray();
                    String bitmap_string = Base64.encodeToString(bytes, Base64.DEFAULT);
                    repBitmapPics.put(rep.getKey(), scaleDownBitmap(bitmap_output, 30, mContext));
                    String[] holder = watchContent.get(rep.getKey());
                    holder[3] = bitmap_string;
                    watchContent.put(rep.getKey(), holder);
                } catch (Exception e) {
                    Log.i("Error", e.getMessage());
                }
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
        intent.putExtra("repPictures", repBitmapPics);
        intent.putExtra("latitude", "0.0");
        intent.putExtra("longitude", "0.0");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

        Intent toWatch = new Intent(mContext, sendWatchData.class);
        toWatch.putExtra("watchContent", watchContent);
        mContext.startService(toWatch);


    }
    private static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }
}
