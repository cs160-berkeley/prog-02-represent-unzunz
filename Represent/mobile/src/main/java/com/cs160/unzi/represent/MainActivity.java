package com.cs160.unzi.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText location_input;
    public final static String REPRESENTATIVES = "com.represent.REPRESENTATIVES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location_input = (EditText) findViewById(R.id.location_input);
        location_input.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        location_input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String location_string = location_input.getText().toString();
                    retrieveReps(location_string);
                    return true;
                }
                return false;
            }
        });
    }

    public ArrayList<String> retrieveRepsAPI() {
        ArrayList<String> representatives = new ArrayList<>();
        representatives.add("Barbara Lee");
        representatives.add("Barbara Lee");
        return representatives;
    }

    public void retrieveReps(String location) {
        TextView sampleView = (TextView) findViewById(R.id.dundun);
        sampleView.setText(location);

        ArrayList<String> representatives = retrieveRepsAPI();

        Intent congressionalIntent = new Intent(this, CongressionalViewActivity.class);
        congressionalIntent.putStringArrayListExtra("REPRESENTATIVES", representatives);
        startActivity(congressionalIntent);

        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putStringArrayListExtra("REPRESENTATIVES", representatives);
        startService(sendIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
