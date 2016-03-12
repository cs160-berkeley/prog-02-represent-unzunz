package com.cs160.unzi.represent;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by unzi on 3/8/16.
 */
public class JSONparser {

    private static HttpsURLConnection sunlightConnection;
    private static StringBuilder result;

    public static JSONObject makeHttpRequest(String url) {
        try {
            URL sunlight_url = new URL(url);
            sunlightConnection = (HttpsURLConnection) sunlight_url.openConnection();
            sunlightConnection.setRequestMethod("GET");
            sunlightConnection.setDoOutput(false);
            sunlightConnection.setRequestProperty("Accept-Charset", "UTF-8");
            sunlightConnection.setConnectTimeout(15000);
            sunlightConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream in = new BufferedInputStream(sunlightConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        sunlightConnection.disconnect();

        try {
            JSONObject json_object = new JSONObject(result.toString());
            return json_object;
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return null;
    }

}
