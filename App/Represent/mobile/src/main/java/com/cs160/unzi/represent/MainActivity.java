package com.cs160.unzi.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        final EditText location_input = (EditText) findViewById(R.id.location_input);
        location_input.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        location_input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String location_string = location_input.getText().toString();
                    Intent intent = new Intent(mContext, RetrieveContent.class);
                    intent.putExtra("LOCATION", location_string);
                    mContext.startService(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
