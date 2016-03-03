package com.cs160.unzi.represent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CongressionalViewActivity extends FragmentActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);

        Intent intent = getIntent();
        ArrayList<String> extras = intent.getStringArrayListExtra("REGION_REPS");

//        LinearLayout hello = (LinearLayout) findViewById(R.id.watch_congressional);
//        mTextView = (TextView) findViewById(R.id.reps_output);
//        mTextView.setText("WHYS IS THIS NOT WORKIGN HELLO SLKDFJSLKDJF BYSDFLKSDF???? WHY IS DIS NOT NOT WORKI?SDFSDF?");
//        Log.i("We in here", mTextView.getText().toString());

//        Log.i("EXTRAS? ", Integer.toString(extras.size()));

        final DotsPageIndicator mPageIndicator;
        final GridViewPager mViewPager;

        final String[][] rep_names = {
            { "Barbara Lee", "Jerry McNerney", "Loni Hancock" },
            { "Obama - 59.3%"}
        };

        final String[][] rep_parties = {
            { "Democrat", "Democrat", "Democratic" },
            { "Romney - 38.3%"}
        };

        // Get UI references
        mPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        mViewPager = (GridViewPager) findViewById(R.id.pager);

        // Assigns an adapter to provide the content for this pager
        mViewPager.setAdapter(new GridPagerAdapter(getFragmentManager(), rep_names, rep_parties));
        mPageIndicator.setPager(mViewPager);


//        if (extras != null) {
//            for (String member : extras) {
////                Log.i("MEMBER: ", member);
//                TextView new_rep = new TextView(this);
//                new_rep.setText(member);
//                RepOnClickListener click_listener = new RepOnClickListener();
//                click_listener.setRepName(member);
//                new_rep.setOnClickListener(click_listener);
//                hello.addView(new_rep);
//            }
//        }
    }
    private static final class GridPagerAdapter extends FragmentGridPagerAdapter {

        String[][] repNames;
        String[][] repParties;

        private GridPagerAdapter(FragmentManager fm, String[][] rep_names, String[][] rep_parties) {
            super(fm);
            repNames = rep_names;
            repParties = rep_parties;
        }

        @Override
        public Fragment getFragment(int row, int column) {
            return (CardFragment.create(repNames[row][column], repParties[row][column]));
        }

        @Override
        public int getRowCount() {
            return repNames.length;
        }

        @Override
        public int getColumnCount(int row) {
            return repNames[row].length;
        }
    }

    public class RepOnClickListener implements View.OnClickListener {
        String rep_name;

        public void setRepName(String name) {
            this.rep_name = name;
        }

        @Override
        public void onClick(View view) {
            retrieveDetails(rep_name);
        }
    }

    public void retrieveDetails(String rep_name) {
        Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
        sendIntent.putExtra("SELECTED_REP", rep_name);
        startService(sendIntent);
    };



}
