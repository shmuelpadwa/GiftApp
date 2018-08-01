package com.example.brandonmayle.giftisrael;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference uRef = database.getReference("users").child(user.getDisplayName() + "_" + user.getUid());
    DatabaseReference activitiesRef = database.getReference("global").child("activities");

    float startTime, endTime, latitude, longitude, radius;

    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        uRef.child("preferences").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SharedPreferences sharedPref = getSharedPreferences("com.example.brandonmayle.giftisrael.surveyResults", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    System.out.println(data.getKey());
                    editor.putFloat(data.getKey(), Float.parseFloat(data.getValue().toString()));
                }
                editor.commit();

                startTime = sharedPref.getFloat("start time", 0);
                endTime = sharedPref.getFloat("end time", 0);
                latitude = sharedPref.getFloat("latitude", 0);
                longitude = sharedPref.getFloat("longitude", 0);
                radius = sharedPref.getFloat("radius", 0);

                queryDatabase();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                about();
                return true;
            case R.id.contact:
                contact();
                return true;
            case R.id.log_out:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void queryDatabase() {
        activitiesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Activity> activityList = new ArrayList<Activity>();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Log.d(TAG, "Activity name: " + data.child("name").getValue(String.class));
                    String name = data.child("name").getValue(String.class);
                    String type = data.child("type").getValue().toString();
                    String date = data.child("date").getValue().toString();
                    String time = data.child("time").getValue().toString();
                    String location = data.child("location").getValue().toString();
                    String price = data.child("price").getValue().toString();
                    String description = data.child("description").getValue().toString();
                    long startTime = (Long) data.child("private").child("start time").getValue();
                    long endTime = (Long) data.child("private").child("end time").getValue();
                    double latitude = (double) data.child("private").child("latitude").getValue();
                    double longitude = (double) data.child("private").child("longitude").getValue();
                    Activity activity = new Activity(name, type, date, time, location, price, description, startTime, endTime, latitude, longitude);
                    activityList.add(activity);
                }
                updateList(activityList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateList(ArrayList<Activity> activityList) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> secondList = new ArrayList<String>();
        ArrayList<String> thirdList = new ArrayList<String>();

        SharedPreferences sharedPref = getSharedPreferences("surveyResults", Context.MODE_PRIVATE);

        for (Activity activity: activityList) {
            if (activity.getStartTime() >= startTime & activity.getEndTime() <= endTime) {
                Location loc1 = new Location("");
                loc1.setLatitude(latitude);
                loc1.setLongitude(longitude);

                Location loc2 = new Location("");
                loc2.setLatitude(activity.getLatitude());
                loc2.setLongitude(activity.getLongitude());

                float distance = loc1.distanceTo(loc2);
                if ((distance/1000) < sharedPref.getFloat("radius", 0)) {
                    list.add(activity.getName());
                }
                else {
                    secondList.add(activity.getName());
                }
            }
            else {
                thirdList.add(activity.getName());
            }
        }
        for (String string: secondList) {
            list.add(string);
        }
        for (String string: thirdList) {
            list.add(string);
        }

        findViewById(R.id.progressBar).setVisibility(View.GONE);

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listview, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                activitiesRef.child("activity" + position).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        SharedPreferences sharedPref = getSharedPreferences("com.example.brandonmayle.giftisrael.filePreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();

                        for (DataSnapshot data: dataSnapshot.getChildren()) {
                            editor.putString(data.getKey(), data.getValue().toString());
                        }

                        editor.commit();
                        showSearchViewDialog();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void showSearchViewDialog() {
        DialogFragment newFragment = new SearchViewFragment();
        newFragment.show(getSupportFragmentManager(), "searchViewPicker");
    }

    private void about() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void contact() {
        DialogFragment newFragment = new ContactFragment();
        newFragment.show(getSupportFragmentManager(), "contactPicker");
    }

    private void signOut() {
        Intent intent = new Intent(this, SignOutActivity.class);
        startActivity(intent);
    }
}