package com.cs160.unzi.represent;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by unzi on 3/9/16.
 */
public class RetrieveTwitterBearerToken extends AsyncTask<Void, Void, String> {

    public AsyncResponse delegate = null;

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public RetrieveTwitterBearerToken(AsyncResponse delegate){
        this.delegate = delegate;
    }

    protected String doInBackground(Void... args) {
        String bearer_token = "";
        try {
            bearer_token = TwitterAuthorization.requestBearerToken();
        } catch (IOException e) {
            Log.i("ERROR", e.toString());
        }
        return bearer_token;
    }

    protected void onPostExecute(String bearer_token) {
        delegate.processFinish(bearer_token);
    }
}
