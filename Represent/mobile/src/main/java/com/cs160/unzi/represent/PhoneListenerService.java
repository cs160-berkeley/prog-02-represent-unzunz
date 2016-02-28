package com.cs160.unzi.represent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class PhoneListenerService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

    }

}
