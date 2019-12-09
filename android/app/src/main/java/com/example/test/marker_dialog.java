package com.example.test;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class marker_dialog extends DialogFragment {
    TextView name;
    TextView open_now;
    TextView rating;
    TextView latitude;
    TextView longitude;
    View marker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        marker = inflater.inflate(R.layout.marker_view, container);
        name = (TextView)marker.findViewById(R.id.park_name);
        open_now = (TextView)marker.findViewById(R.id.open_time);
        rating = (TextView)marker.findViewById(R.id.rating);
        latitude = (TextView)marker.findViewById(R.id.latitude);
        longitude = (TextView)marker.findViewById(R.id.longitude);
        return marker;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        name.setText(bundle.getString("name"));
        open_now.setText(bundle.getString("open"));
        rating.setText(bundle.getString("rating"));
        latitude.setText(bundle.get("latitude").toString());
        longitude.setText(bundle.get("longitude").toString());
    }
}
