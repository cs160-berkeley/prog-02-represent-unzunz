package com.cs160.unzi.represent;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PresidentResultsFragment extends Fragment {

    public static PresidentResultsFragment create(String obama, String romney, String location) {
        PresidentResultsFragment fragment = new PresidentResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("obama", obama);
        bundle.putString("romney", romney);
        bundle.putString("location", location);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_president_results, container, false);

        TextView obamaView = (TextView) root.findViewById(R.id.obama_results);
        TextView romneyView = (TextView) root.findViewById(R.id.romney_results);
        TextView locationView = (TextView) root.findViewById(R.id.location);

        String obama = getArguments().getString("obama");
        String romney = getArguments().getString("romney");
        String location = getArguments().getString("location");

        obamaView.setText(obama);
        romneyView.setText(romney);
        locationView.setText(location);

        return root;
    }


}
