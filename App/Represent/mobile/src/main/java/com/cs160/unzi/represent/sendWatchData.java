package com.cs160.unzi.represent;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by unzi on 3/10/16.
 */

public class sendWatchData extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Context mContext;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("senddata", "onCreate");
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("HELLO?LASDKLAJD", "SLDKFJSDF");
        return 0;
    }
    @Override
    public void onConnected(Bundle bundle) {
        Log.i("senddata", "onConnected");
        String [] myData = new String[] {"homgfas", "dw2", "dat3"};
        new DataTask (mContext, myData).execute();
    }

    class DataTask  extends AsyncTask<Node, Void, Void> {

        private final String[] contents;
        Context c;

        public DataTask (Context c, String[] contents) {
            this.c = c;
            this.contents = contents;
        }

        @Override
        protected Void doInBackground(Node... nodes) {

            PutDataMapRequest dataMap = PutDataMapRequest.create("/myapp/myevent");
            dataMap.getDataMap().putStringArray("contents", contents);

            PutDataRequest request = dataMap.asPutDataRequest();

            DataApi.DataItemResult dataItemResult = Wearable.DataApi
                    .putDataItem(mGoogleApiClient, request).await();


//            Log.d("[DEBUG] SendDataCoolTask - doInBackground", "/myapp/myevent" status, "+getStatus());
            Log.d("DEBUG", "UGH");
            return null;
        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


}



//
//public static DataMap toDataMap(HashMap<String, String> hashMap) {
//    DataMap dataMap = new DataMap();
//    for (Map.Entry<String, String> entry : hashMap.entrySet()) {
//        dataMap.putString(entry.getKey(), entry.getValue());
//    }
//    return dataMap;
//
//
//public static HashMap<String, String> fromDataMap(DataMap dataMap) {
//    HashMap<String, String> hashMap = new HashMap<String, String>();
//    for (String key : dataMap.keySet()) {
//        hashMap.put(key, dataMap.getString(key));
//    }
//    return hashMap;
//}