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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by unzi on 3/10/16.
 */

public class sendWatchData extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Context mContext;
    private HashMap<String, String[]> watchContent;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        watchContent = (HashMap<String,  String[]>) intent.getSerializableExtra("watchContent");
//        new DataTask(mContext, watchContent).execute();
        Log.i("COMON", "PLES");
        return START_STICKY;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("watch", watchContent.toString());
        for (Map.Entry<String, String[]> entry : watchContent.entrySet()) {
            Log.i("KEY", entry.getKey());
            Log.i("VALUE", entry.getValue()[0]  );
            Log.i("VALUE", entry.getValue()[1]  );
        }
        new DataTask (mContext, watchContent).execute();
        String [] myData = new String[]{"data1", "data2", "data3"};
    }


    class DataTask  extends AsyncTask<Node, Void, Void> {

        private final HashMap<String, String[]> contents;
        Context c;

        public DataTask (Context c, HashMap<String, String[]> contents) {
            this.c = c;
            this.contents = contents;
        }

        @Override
        protected Void doInBackground(Node... nodes) {
            Log.i("HERE", "UGH");
            PutDataMapRequest dataMap = PutDataMapRequest.create("/myapp/myevent");


//            dataMap.getDataMap().putStringArray("contents", contents);
            for (Map.Entry<String, String[]> entry : contents.entrySet()) {
                dataMap.getDataMap().putStringArray(entry.getKey(), entry.getValue());
            }
            Log.i("CHECKING", dataMap.getDataMap().keySet().toString());

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