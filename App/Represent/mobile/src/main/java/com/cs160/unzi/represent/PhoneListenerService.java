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
    private static final String DISPLAY_REP = "/rep_info";
    private final String sunlightKey = "946f65d6df5c4ae2b5f9ddb58fd867f5";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.i("I GOT IT THE MESSAGE!!", "I GOT!!");
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if (messageEvent.getPath().equalsIgnoreCase(DISPLAY_REP)) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            String[] data = value.split("!");
            String rep_name = data[1];
            String rep_id = data[2];
            String end_term = data[0];


            String bill_url = "https://congress.api.sunlightfoundation.com/bills?sponsor_id=" + rep_id + "&apikey=" + sunlightKey;
            String committee_url = "https://congress.api.sunlightfoundation.com/committees?member_ids=" + rep_id + "&apikey=" + sunlightKey;
            new RetrieveRepDetails(this.getBaseContext(), rep_name, end_term).execute(bill_url, committee_url);
        }

    }

}
