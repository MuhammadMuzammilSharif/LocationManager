package com.muhammadmuzammilsharif.demolibrary;
/*
 * Created by M_Muzammil Sharif on 25-Aug-17.
 */

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muhammadmuzammilsharif.locationhelper.Location.OnLocationChangeListener;

import java.text.DateFormat;
import java.util.Date;

public class MyFragment extends Fragment implements OnLocationChangeListener {
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo_location, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.location);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (textView != null)
            textView.setText("FRAGMENT: \n Location: \nLat: " + String.valueOf(location.getLatitude()) + " - Lng: " + String.valueOf(location.getLongitude()) + "\n Updated On: " + DateFormat.getDateTimeInstance().format(new Date()));
    }
}
