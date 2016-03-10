package com.cs160.unzi.represent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class WatchToPhoneService extends Service {

    private GoogleApiClient mWatchApiClient;
    private List<Node> nodes = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mWatchApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }
                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                }).build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatchApiClient.disconnect();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();

        final String repId = extras.getString("repId");
        final String repName = extras.getString("repName");
        final String termEnd = extras.getString("termEnd");

        final String data = repId + "!" + repName + "!" + termEnd;

        new Thread(new Runnable() {
            @Override
            public void run() {
                mWatchApiClient.connect();
                sendMessage("/rep_info", data);
            }
        }).start();
        return START_STICKY;
    }

    private void sendMessage(final String path, final String text ) {
        new Thread(new Runnable() {
            @Override
        public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mWatchApiClient).await();
                for (Node node : nodes.getNodes()) {
                    Log.i("PATHNAME: ", path);
                    Log.i("SELECTED_NAME", text);
                    Log.i("SELECTED_NODE", node.getId());
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mWatchApiClient, node.getId(), path, text.getBytes()
                    ).await();
                }
            }
        }).start();
    }
}
