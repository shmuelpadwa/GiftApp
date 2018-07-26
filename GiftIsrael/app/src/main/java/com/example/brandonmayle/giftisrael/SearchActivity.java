package com.example.brandonmayle.giftisrael;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    DatabaseReference activitiesRef = database.getReference("activities");
    ArrayList<String> list = new ArrayList<String>();

    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        readCount();
    }

    public void readCount() {
        activitiesRef.child("count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long value = dataSnapshot.getValue(long.class);
                Log.d(TAG, "Value is: " + value);

                queryDatabase(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void queryDatabase(long value) {
        for (int i = 0; i < value; ++i) {
            DatabaseReference nameRef = activitiesRef.child("activity" + i).child("name");
            nameRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Value is: " + value);

                    list.add(value);
                    displayList(list);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
    }

    public void displayList(ArrayList<String> list) {
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
                            System.out.print(data.getKey() + " ");
                            System.out.println(data.getValue().toString());
                            editor.putString(data.getKey(), data.getValue().toString());
                        }

                        editor.commit();
                        showActivityViewDialog();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void showActivityViewDialog() {
        DialogFragment newFragment = new SearchViewFragment();
        newFragment.show(getSupportFragmentManager(), "searchViewPicker");
    }
}
