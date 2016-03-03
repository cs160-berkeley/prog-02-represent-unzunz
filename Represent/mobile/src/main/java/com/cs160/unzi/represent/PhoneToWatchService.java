package com.cs160.unzi.represent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhoneToWatchService extends Service {

    private GoogleApiClient mApiClient;
    private static final String REGION_REPS = "/region_reps";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }
                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                })
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }

    @Override
    //gets called when a client calls startService(Intent)
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
//        final List<String> names = extras.getStringArrayList("REPRESENTATIVES");
//        final List<String> parties = extras.getStringArrayList("PARTIES");
//        final List<String> pres_results = extras.getStringArrayList("PRESIDENTIAL");
        final String location = extras.getString("LOCATION");

        // Send message
        new Thread(new Runnable() {
            @Override
            public void run() {
                mApiClient.connect();
                sendMessage("LOCATION", location);
//                sendMessage("NAMES", names);
//                sendMessage("PARTIES", parties);
//                sendMessage("PRESIDENTIAL", pres_results);
            }
        }).start();
        return START_STICKY;
    }

    private void sendMessage(final String path, final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mApiClient).await();
//                ByteArrayOutputStream byte_stream = new ByteArrayOutputStream();
//                DataOutputStream output_bytes = new DataOutputStream(byte_stream);
//                for (String element : text) {
//                    try {
//                        output_bytes.writeUTF(element);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }

                List<Node> nodes_list = nodes.getNodes();

                Log.i("Node length", Integer.toString(nodes_list.size()));

//                byte[] bytes = byte_stream.toByteArray();
                for (Node node : nodes.getNodes()) {
                    Log.i("PATHNAME: ", path);
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
//                        mApiClient, node.getId(), path, bytes
                            mApiClient, node.getId(), path, text.getBytes()
                    ).await();
                }
            }
        }).start();
    }
}
