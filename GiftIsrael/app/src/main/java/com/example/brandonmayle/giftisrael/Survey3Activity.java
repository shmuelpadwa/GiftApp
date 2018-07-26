package com.example.brandonmayle.giftisrael;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Survey3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView surveyText = (TextView) findViewById(R.id.survey3Field);
                String text = surveyText.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("com.example.brandonmayle.giftisrael.surveyResults", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("3", text);
                editor.commit();

                nextActivity();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void nextActivity() {
        Intent intent = new Intent(this, Survey4Activity.class);
        startActivity(intent);
    }
}