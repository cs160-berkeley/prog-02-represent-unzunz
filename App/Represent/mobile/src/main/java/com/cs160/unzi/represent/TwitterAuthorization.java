package com.cs160.unzi.represent;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class TwitterAuthorization {

    private static HttpsURLConnection twitterConnection;
    private static StringBuilder result;

    private static final String consumerKey = "YYVzkZslgrkBzr3C4RSrh8IBm";
    private static final String consumerSecret = "8ShZZO2d5bKRow5uQdSwVVt6A8jKEQmk8haXljCZTGkBuZNL8y";

    private static String encodeTwitterKeys() {
        try {
            String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
            String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");
            String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;

            return Base64.encodeToString(fullKey.getBytes(), Base64.NO_WRAP);
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
//            int status = twitterConnection.getResponseCode();
//            Log.i("MESSAGE", twitterConnection.getResponseMessage());

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
            JSONObject json_object = new JSONObject(result.toString());
            String tokenType = (String) json_object.get("token_type");
            String token = (String) json_object.get("access_token");
            Log.i("TOKEN: ", token);
            return token;
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage());
        }
        return null;
    }
}
