package com.example.brandonmayle.giftisrael;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ActivityViewFragment extends DialogFragment {

    TextView t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_view, container, false);
        t = (TextView) view.findViewById(R.id.activityViewText);
        setText();
        return view;
    }

    public void setText() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("com.example.brandonmayle.giftisrael.filePreferences", Context.MODE_PRIVATE);
        t.setText("Name: " + sharedPref.getString("name", null) + "\nDate: " + sharedPref.getString("date", null) +
                "\nTime: " + sharedPref.getString("time", null) + "\nHours: " + sharedPref.getString("hours", null) + "\nLocation: " +
                sharedPref.getString("location", null) + "\nDescription: " + sharedPref.getString("description", null));
    }

}