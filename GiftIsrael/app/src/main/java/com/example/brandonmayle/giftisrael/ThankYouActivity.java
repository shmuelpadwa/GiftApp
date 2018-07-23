package com.example.brandonmayle.giftisrael;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ThankYouActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference uRef = database.getReference(user.getUid());

    public static final String TAG = "ThankYouActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        SharedPreferences sharedPref = getSharedPreferences("com.example.brandonmayle.giftisrael.filePreferences", Context.MODE_PRIVATE);

        TextView prefsView = (TextView) findViewById(R.id.prefsView);
        prefsView.setText("Name: " + sharedPref.getString("name", null) + "\nDate: " + sharedPref.getString("date", null) +
                "\nTime: " + sharedPref.getString("time", null) + "\nHours: " + sharedPref.getString("hours", null) + "\nLocation: " +
        sharedPref.getString("location", null) + "\nDescription: " + sharedPref.getString("description", null));

        Button button = (Button) findViewById(R.id.finishButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uRef.child("count").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        long value = dataSnapshot.getValue(long.class);
                        Log.d(TAG, "Value is: " + value);

                        writeToDatabase(value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });
    }

    public void writeToDatabase(long value) {
        SharedPreferences sharedPref = getSharedPreferences("com.example.brandonmayle.giftisrael.filePreferences", Context.MODE_PRIVATE);
        DatabaseReference activityRef = uRef.child("activities").child("activity" + value);

        DatabaseReference nameRef = activityRef.child("name");
        nameRef.setValue(sharedPref.getString("name", null));
        DatabaseReference dateRef = activityRef.child("date");
        dateRef.setValue(sharedPref.getString("date", null));
        DatabaseReference timeRef = activityRef.child("time");
        timeRef.setValue(sharedPref.getString("time", null));
        DatabaseReference hoursRef = activityRef.child("hours");
        hoursRef.setValue(sharedPref.getString("hours", null));
        DatabaseReference locationRef = activityRef.child("location");
        locationRef.setValue(sharedPref.getString("location", null));
        DatabaseReference descriptionRef = activityRef.child("description");
        descriptionRef.setValue(sharedPref.getString("description", null));

        DatabaseReference countRef = uRef.child("count");
        countRef.setValue(value + 1);

        returnToLog();
    }

//    public void finishUpload() {
//        try {
//            final File countFile = File.createTempFile(user.getUid(), "txt");
//
//            countRef.getFile(countFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    String path = countFile.getPath();
//
//                    // Retrieve the count variable from the cloud file
//                    try {
//                        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
//                        String buffer;
//                        StringBuilder stringBuilder = new StringBuilder();
//
//                        while ((buffer = bufferedReader.readLine()) != null) {
//                            stringBuilder.append(buffer);
//                        }
//
//                        count = Integer.parseInt(stringBuilder.toString());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    // Iterate the count
//                    try {
//                        BufferedWriter bw = new BufferedWriter(new FileWriter(countFile));
//                        count = count + 1;
//
//                        bw.write(Integer.toString(count));
//                        bw.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    // Reupload the file
//                    Uri uCountFile = Uri.fromFile(countFile);
//
//                    UploadTask uploadTask = countRef.putFile(uCountFile, metadata);
//                    uploadTask.addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            // Handle unsuccessful uploads
//                        }
//                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            // Upload the activity file
//                            File mFolder = new File(getFilesDir() + user.getUid());
//                            File activityFile = new File(mFolder.getAbsolutePath() + "/activity" + (count - 1) + ".txt");
//                            if (!mFolder.exists()) {
//                                mFolder.mkdir();
//                            }
//                            if (!activityFile.exists()) {
//                                try {
//                                    activityFile.createNewFile();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            Uri uActivityFile = Uri.fromFile(activityFile);
//
//                            UploadTask uploadTask = storageRef.child(user.getUid() + "/" + uActivityFile.getLastPathSegment()).putFile(uActivityFile, metadata);
//                            uploadTask.addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception exception) {
//                                    int errorCode = ((StorageException) exception).getErrorCode();
//                                    String errorMessage = exception.getMessage();
//                                }
//                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    returnToLog();
//                                }
//                            });
//                        }
//                    });
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void returnToLog() {
        Toast.makeText(ThankYouActivity.this, "Activity submitted.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
