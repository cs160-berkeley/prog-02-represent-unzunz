package com.cs160.unzi.represent;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by unzi on 3/10/16.
 */
public class PresidentialResults {

    private static Context mContext;

    public PresidentialResults(Context context) {
        mContext = context;
    }
    private static JSONArray retrieveJSONfile() {
        String json_string = "";
        JSONArray jsonArray;

        try {
            Log.i("CONTEXT", mContext.toString());
            InputStream input_stream = mContext.getAssets().open("election-county-2012.json");
            int size = input_stream.available();
            byte[] byte_buffer = new byte[size];
            input_stream.read(byte_buffer);
            input_stream.close();

            json_string = new String(byte_buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            jsonArray = new JSONArray(json_string);
            return jsonArray;
        } catch (JSONException e) {

        }
        return null;
    }
    public static String[] getPresidentialResults(String state, String county) {
        JSONArray json_array = retrieveJSONfile();
        try {
            for (int i = 0; i < json_array.length(); i++) {
                JSONObject info = (JSONObject) json_array.getJSONObject(i);
                if (info.get("state-postal") == state && info.get("county-name") == county) {
                    String[] result = {info.get("obama-vote").toString(), info.get("romney-vote").toString()};
                    return result;
                }
            }
        } catch (JSONException e) {

        }
        return null;
    }
}
