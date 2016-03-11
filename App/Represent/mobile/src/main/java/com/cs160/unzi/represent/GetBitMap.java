package com.cs160.unzi.represent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by unzi on 3/10/16.
 */
class GetBitMap extends AsyncTask<String, Void, Void> {

    public interface AsyncResponse {
        void processFinish(HashMap<String, Bitmap> bitmapOutput, HashMap<String, String> stringOutput);
    }

    public AsyncResponse delegate = null;
    private Context mContext;
    private  ArrayList<String> fullNames;
    private HashMap<String, String> repPics;

    private HashMap<String, Bitmap> bitmapOutput;
    private HashMap<String, String> stringOutput;

    public GetBitMap(AsyncResponse delegate, Context context, ArrayList<String> full_names, HashMap<String, String> rep_pics) {
        mContext = context;
        fullNames = full_names;
        repPics = rep_pics;
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(String... urls) {
        for (String full_name : fullNames) {
            if (!repPics.get(full_name).equals("")) {
                try {
                    URL url = new URL(repPics.get(full_name));
                    Bitmap bitmat_output = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    bitmapOutput.put(full_name, bitmat_output);

                    ByteArrayOutputStream byte_ouput_stream = new ByteArrayOutputStream();
                    bitmat_output.compress(Bitmap.CompressFormat.PNG, 100, byte_ouput_stream);
                    byte[] bytes = byte_ouput_stream.toByteArray();
                    String bitmap_string = Base64.encodeToString(bytes, Base64.DEFAULT);

                    bitmapOutput.put(full_name, bitmat_output);
                    stringOutput.put(full_name, bitmap_string);

                } catch (IOException e) {
                    Log.i("ERROR", e.getMessage());
                    return null;
                }
            } else {
                bitmapOutput.put(full_name, null);
                stringOutput.put(full_name, "");
            }
        }
        return null;
    }

    protected void onPostExecute() {
        delegate.processFinish(bitmapOutput, stringOutput);
    }
}