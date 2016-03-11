package com.cs160.unzi.represent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

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
            String image = data[3];
            String rep_name = data[1];
            String rep_id = data[2];
            String end_term = data[0];
            HashMap<String, Bitmap> rep_pic = new HashMap<String, Bitmap>();
            Bitmap image_converted = StringToBitMap(image);
            rep_pic.put(rep_name, image_converted);
            String bill_url = "https://congress.api.sunlightfoundation.com/bills?sponsor_id=" + rep_id + "&apikey=" + sunlightKey;
            String committee_url = "https://congress.api.sunlightfoundation.com/committees?member_ids=" + rep_id + "&apikey=" + sunlightKey;
            new RetrieveRepDetails(this.getBaseContext(), rep_name, end_term, rep_pic).execute(bill_url, committee_url);
        } else if (messageEvent.getPath().equalsIgnoreCase("/shake")) {
            String new_location = retrieveZipCodes();
            Intent intent = new Intent(this, RetrieveContent.class);
            intent.putExtra("LOCATION", new_location);
            startService(intent);
        }
    }

    private String retrieveZipCodes() {
        ArrayList<String> zipcodes = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(getAssets().open("zipcodes.csv"));
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                zipcodes.add(scanner.next());
            }
            scanner.close();
        } catch (Exception e) {

        }
        Random random = new Random();
        int random_index = random.nextInt(zipcodes.size());
        Log.i("RANDOM", String.valueOf(random_index));
        return zipcodes.get(random_index);
    }
    private Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
