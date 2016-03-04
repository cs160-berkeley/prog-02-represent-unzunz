package com.cs160.unzi.represent;

import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RepFragment extends CardFragment {
    private View fragmentView;
    private View.OnClickListener listener;

    public static RepFragment create(CharSequence title, CharSequence text, int iconRes) {
        RepFragment fragment = new RepFragment();
        Log.i("HELLO?CMON", "SLKDFJDS");
        Bundle args = new Bundle();
        if(title != null) {
            args.putCharSequence("CardFragment_title", title);
        }

        if(text != null) {
            args.putCharSequence("CardFragment_text", text);
        }

        if(iconRes != 0) {
            args.putInt("CardFragment_icon", iconRes);
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_card, container, false);
//        View view = getLayoutInflater().inflate(R.layout.rep_view, congressionalLayout, false);
        Log.i("HELLO?????", "GOODYBYE");

        String title = getArguments().getString("KEY_TITLE");
        fragmentView = super.onCreateContentView(inflater, container, savedInstanceState);
        fragmentView.setOnClickListener(new RepOnClickListener(title));

        return fragmentView;
    }

    public void setOnClickListener(final View.OnClickListener listener) {
        this.listener = listener;

    }

    public class RepOnClickListener implements View.OnClickListener {
        String rep_name;

        public RepOnClickListener(String name) {
            this.rep_name = name;
        }

        @Override
        public void onClick (View v) {
            Log.i("click, ", "cry");
//        Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
//        sendIntent.putExtra("SELECTED_REP", rep_name);
//        startService(sendIntent);
        }
    }
}
