package com.cs160.unzi.represent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WatchListenerService extends WearableListenerService {
    private static final String REGION_REPS = "/region_reps";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());

        if (messageEvent.getPath().equalsIgnoreCase(REGION_REPS)) {
            Log.i("HELLO", "HELLO ITS ME");
            ArrayList<String> message_array = new ArrayList<String>();
            ByteArrayInputStream byte_stream = new ByteArrayInputStream(messageEvent.getData());
            DataInputStream input_data = new DataInputStream(byte_stream);
            try {
                while (input_data.available() > 0) {
                    message_array.add(input_data.readUTF());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, CongressionalViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putStringArrayListExtra("REGION_REPS", message_array);
            startActivity(intent);
        } else {
//            super.onMessageReceived(messageEvent);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("SOMETHING", "Error");
            startActivity(intent);
        }

    }
}
