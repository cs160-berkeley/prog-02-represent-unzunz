package com.cs160.unzi.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class TwitterLoginActivity {

    private static TwitterApiClient twitterApiClient;
    private static HttpsURLConnection twitterConnection;
    private static StringBuilder result;
    public static TwitterApiClient getTwitterApiClient() {
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> result) {
//                Log.d("bLALSKDJ", "loginGuest.callback.success called");
//                AppSession guestAppSession = result.data;
//                twitterApiClient = TwitterCore.getInstance().getApiClient(guestAppSession);
//
//
//                RetrieveTweets TwitterApiClient = new RetrieveTweets(Twitter.getSessionManager().getActiveSession());
//                Log.i("SEESSION", Twitter.getSessionManager().getActiveSession().toString());
//                RetrieveTweets.CustomService userService = TwitterApiClient.getCustomService();
//                userService.show("oonzunz", 1, new Callback<User>() {
//                    @Override
//                    public void success(Result<User> result) {
//                        Log.i("RESUTL: ", result.toString());
//                        Log.i("HELLO", "HI");
//                    }
//
//                    public void failure(TwitterException exception) {
//                        Log.i("ERROR", exception.toString());
//                        Log.i("HELLO", "OoOOoOOooOoooo");
//                    }
//                });

            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("bLALSKDJ.TAG", "loginGuest.callback.failure called");
                // unable to get an AppSession with guest auth
                throw exception;
            }
        });

        return twitterApiClient;
    }


    private static String encodeTwitterKeys() {
        String consumerKey = "YYVzkZslgrkBzr3C4RSrh8IBm";
        String consumerSecret = "8ShZZO2d5bKRow5uQdSwVVt6A8jKEQmk8haXljCZTGkBuZNL8y";
        try {
            String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
            String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");
            String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;


            return Base64.encodeToString(fullKey.getBytes(), Base64.NO_WRAP);


//            Log.i("ENCODED", new String(encodedBytes));
//            return new String(encodedBytes);
        } catch (UnsupportedEncodingException e) {
            return new String();
        }
    }

    public static String requestBearerToken() throws IOException {
        String encodedCredentials = encodeTwitterKeys();

        try {
            URL twitter_url = new URL("https://api.twitter.com/oauth2/token");
            twitterConnection = (HttpsURLConnection) twitter_url.openConnection();
            twitterConnection.setDoOutput(true);
            twitterConnection.setRequestMethod("POST");
            twitterConnection.setRequestProperty("Host", "api.twitter.com");
            twitterConnection.setRequestProperty("User-Agent", "Represent");
            twitterConnection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            twitterConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            twitterConnection.setRequestProperty("Content-Length", "29");
            twitterConnection.setUseCaches(false);

            twitterConnection.setConnectTimeout(15000);

            String str =  "grant_type=client_credentials";
            byte[] outputInBytes = str.getBytes("UTF-8");

            DataOutputStream wr = new DataOutputStream(twitterConnection.getOutputStream());
            wr.writeBytes(str);
            wr.flush();
            wr.close();

            Log.i("CONNECTION", twitterConnection.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());

        }

        try {
            Log.i("HI", "H????");
            int status = twitterConnection.getResponseCode();

            Log.i("MESSAGE", twitterConnection.getResponseMessage());

            InputStream in = new BufferedInputStream(twitterConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.i("RESULT: ", result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject json_object = new JSONObject(result.toString());
            String tokenType = (String) json_object.get("token_type");
            String token = (String) json_object.get("access_token");
            Log.i("TOKEN: ", token);
            return token;
        } catch (JSONException e) {
            Log.e("da ERROR", e.getMessage());
        }

        return null;
    }
}
