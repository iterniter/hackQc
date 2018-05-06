package com.example.khalilbennani.gestiondesarbres;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class HomeActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Uri uri = Uri.parse("https://gcs-vimeo.akamaized.net/exp=1525631026~acl=%2A%2F526815049.mp4%2A~hmac=bf10a0bc709ee192236bf1e13798cac00db0424750dc361d79dc667960a677fe/vimeo-prod-skyfire-std-us/01/3118/6/165591217/526815049.mp4");
        VideoView videoV = (VideoView) findViewById(R.id.video);
        videoV.setVideoURI(uri);
        videoV.setVideoURI(uri);
        videoV.start();

        //animation depart
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);






    }
}
