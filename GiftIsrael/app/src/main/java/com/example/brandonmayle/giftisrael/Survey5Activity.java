package com.example.brandonmayle.giftisrael;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Survey5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView startTimeText = (TextView) findViewById(R.id.startTimeText);
                Float startTime = Float.parseFloat(startTimeText.getText().toString());
                TextView endTimeText = (TextView) findViewById(R.id.endTimeText);
                Float endTime = Float.parseFloat(endTimeText.getText().toString());

                SharedPreferences sharedPref = getSharedPreferences("com.example.brandonmayle.giftisrael.surveyResults", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat("start time", startTime);
                editor.putFloat("end time", endTime);
                editor.commit();

                nextActivity();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showStartTimePickerDialog(View v) {
        DialogFragment newFragment = new StartTimeFragment();
        newFragment.show(getSupportFragmentManager(), "startTimePicker");
    }

    public void showEndTimePickerDialog(View v) {
        DialogFragment newFragment = new EndTimeFragment();
        newFragment.show(getSupportFragmentManager(), "endTimePicker");
    }

    private void nextActivity() {
        Intent intent = new Intent(this, Survey6Activity.class);
        startActivity(intent);
    }
}