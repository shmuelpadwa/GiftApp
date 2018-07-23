package com.example.brandonmayle.giftisrael;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference textRef = storage.getReferenceFromUrl("gs://gift-israel.appspot.com/textTest.txt");
//
//        textRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                TextView text = (TextView) findViewById(R.id.textView2);
//                String str = new String(bytes);
//                text.setText(str);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//
//            }
//        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                about();
                return true;
            case R.id.log_out:
                signOut();
                return true;
            default:
                return false;
        }
    }

    public void popToRecordActivity(View view) {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }

    public void popToSearchActivity(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent WebView = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(WebView);
    }

    public void facebookButton(View view) {
        goToUrl("http://facebook.com/GIFTIsraelCharity");
    }

    public void twitterButton(View view) {
        goToUrl("http://twitter.com/GIFTCharity");
    }

    public void instagramButton(View view) {
        goToUrl("http://instagram.com/jgiftisrael");
    }

    public void youtubeButton(View view) {
        goToUrl("http://youtube.com/user/JGiftTV");
    }

    public void websiteButton(View view) {
        goToUrl("http://jgift.org/");
    }

    public void about() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void signOut() {
        Intent intent = new Intent(this, SignOutActivity.class);
        startActivity(intent);
    }
}
