package com.cs160.unzi.represent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class PhoneListenerService extends WearableListenerService {
    private static final String DISPLAY_REP = "/display_rep";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.i("I GOT IT THE MESSAGE!!", "I GOT!!");
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if (messageEvent.getPath().equalsIgnoreCase(DISPLAY_REP)) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Log.i("PHONE RECEIVED: ", value);
            Intent intent = new Intent(this, DetailedViewActivity.class);
            intent.putExtra("SELECTED_REP", value);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

}
