package com.example.brandonmayle.giftisrael;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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