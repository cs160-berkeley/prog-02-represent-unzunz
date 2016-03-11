package com.cs160.unzi.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;

public class CongressionalViewActivity extends FragmentActivity {

    private Bitmap imageBitMap;
    String[][] repNames;
    String[][] repParties;
    String[][] repImages;
    String[][] bioguideIds;
    String[][] termEndDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);

        Intent intent = getIntent();
        repNames = (String[][]) intent.getSerializableExtra("repNames");
        repParties = (String[][]) intent.getSerializableExtra("repParties");
        repImages = (String[][]) intent.getSerializableExtra("repImages");
        bioguideIds = (String[][]) intent.getSerializableExtra("bioguideIds");
        termEndDates = (String[][]) intent.getSerializableExtra("termEndDates");

        DotsPageIndicator mPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        GridViewPager mViewPager = (GridViewPager) findViewById(R.id.pager);

        mViewPager.setAdapter(new GridPagerAdapter(getFragmentManager(), repNames, repParties,
                                                   repImages, bioguideIds, termEndDates));
        mPageIndicator.setPager(mViewPager);
   }
//
    private static final class GridPagerAdapter extends FragmentGridPagerAdapter {

        String[][] repNames;
        String[][] repParties;
        String[][] repImages;
        String[][] bioguideIds;
        String[][] termEndDates;

        private GridPagerAdapter(FragmentManager fm, String[][] rep_names, String[][] rep_parties,
                                 String[][] rep_images, String[][] bioguide_ids, String[][] term_end_dates) {
            super(fm);
            repNames = rep_names;
            repParties = rep_parties;
            repImages = rep_images;
            bioguideIds = bioguide_ids;
            termEndDates = term_end_dates;
        }

        @Override
        public Fragment getFragment(int row, int column) {
            if (row == 1) {
                return new PresidentResultsFragment().create(repNames[1][0], repParties[1][0], repNames[1][0]);
            }
            return  new CustomFragment().create(repNames[row][column], repParties[row][column],
                    repImages[row][column], bioguideIds[row][column], termEndDates[row][column]);
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
