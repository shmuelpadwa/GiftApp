package com.example.brandonmayle.giftisrael;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FinishSurveyActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference uRef = database.getReference("users").child(user.getDisplayName() + "_" + user.getUid());

    public static final String TAG = "FinishSurveyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_survey);

        SharedPreferences sharedPref = getSharedPreferences("com.example.brandonmayle.giftisrael.surveyResults", Context.MODE_PRIVATE);

        TextView prefsView = (TextView) findViewById(R.id.prefsView);
        prefsView.setText("1: " + sharedPref.getString("1", null) + "\n2: " + sharedPref.getString("2", null) +
                "\n3: " + sharedPref.getString("3", null) + "\n4: " + sharedPref.getString("4", null));

        Button button = (Button) findViewById(R.id.finishButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToDatabase();
            }
        });
    }

    private void writeToDatabase() {
        SharedPreferences sharedPref = getSharedPreferences("com.example.brandonmayle.giftisrael.surveyResults", Context.MODE_PRIVATE);
        DatabaseReference surveyRef = uRef.child("survey");

        DatabaseReference survey1Ref = surveyRef.child("1");
        survey1Ref.setValue(sharedPref.getString("1", null));
        DatabaseReference survey2Ref = surveyRef.child("2");
        survey2Ref.setValue(sharedPref.getString("2", null));
        DatabaseReference survey3Ref = surveyRef.child("3");
        survey3Ref.setValue(sharedPref.getString("3", null));
        DatabaseReference survey4Ref = surveyRef.child("4");
        survey4Ref.setValue(sharedPref.getString("4", null));

        returnToMain();
    }

    private void returnToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
