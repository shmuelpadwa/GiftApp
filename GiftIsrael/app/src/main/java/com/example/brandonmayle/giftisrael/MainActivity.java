package com.example.brandonmayle.giftisrael;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        goToUrl("https://facebook.com/GIFTIsraelCharity");
    }

    public void twitterButton(View view) {
        goToUrl("https://twitter.com/GIFTCharity");
    }

    public void instagramButton(View view) {
        goToUrl("https://instagram.com/jgiftisrael");
    }

    public void youtubeButton(View view) {
        goToUrl("https://youtube.com/user/JGiftTV");
    }

    public void websiteButton(View view) {
        goToUrl("https://jgift.org/");
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