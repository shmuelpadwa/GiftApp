package com.example.brandonmayle.giftisrael;

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

public class SearchViewFragment extends DialogFragment {

    TextView t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_view, container, false);
        t = (TextView) view.findViewById(R.id.searchViewText);
        setText();
        return view;
    }

    public void setText() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("com.example.brandonmayle.giftisrael.filePreferences", Context.MODE_PRIVATE);
        t.setText("Name: " + sharedPref.getString("name", null) + "\nType of activity: " + sharedPref.getString("type", null) +
                "\nDate: " + sharedPref.getString("date", null) + "\nTime: " + sharedPref.getString("time", null) + "\nLocation: " +
                sharedPref.getString("location", null) + "\nPrice: " + sharedPref.getString("price", null) + "\nDescription: " + sharedPref.getString("description", null) + "\nContact: " + sharedPref.getString("contact", null));
    }

}
