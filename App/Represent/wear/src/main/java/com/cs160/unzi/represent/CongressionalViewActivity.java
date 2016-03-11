package com.cs160.unzi.represent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CongressionalViewActivity extends FragmentActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);

        Intent intent = getIntent();
        String[][] repNames = (String[][]) intent.getSerializableExtra("repNames");
        String[][] repParties = (String[][]) intent.getSerializableExtra("repParties");
        String[] repImages = (String[]) intent.getSerializableExtra("repImages");
        String[] endTermDates = (String[]) intent.getSerializableExtra("endTermDates");
        String[] bioguideIds = (String[]) intent.getSerializableExtra("bioguideIds");

        DotsPageIndicator mPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        GridViewPager mViewPager = (GridViewPager) findViewById(R.id.pager);

        mViewPager.setAdapter(new GridPagerAdapter(getFragmentManager(), repNames, repParties,
                                                   repImages, bioguideIds, endTermDates));
        mPageIndicator.setPager(mViewPager);
    }
    private static final class GridPagerAdapter extends FragmentGridPagerAdapter {

        String[][] repNames;
        String[][] repParties;
        String[] repImages;
        String[] bioguideIds;
        String[] endTermDates;

//        String location;

        private GridPagerAdapter(FragmentManager fm, String[][] rep_names, String[][] rep_parties,
                                 String[] rep_images, String[] bioguide_ids, String[] end_term_dates) {
            super(fm);
            repNames = rep_names;
            repParties = rep_parties;
            repImages = rep_images;
            bioguideIds = bioguide_ids;
            endTermDates = end_term_dates;
//            this.location = location;
        }


        @Override
        public Fragment getFragment(int row, int column) {
            return CardFragment.create(repNames[row][column], repParties[row][column], repImages[column],
                                       bioguideIds[column], endTermDates[column], 0);
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

}
