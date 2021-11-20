package com.example.breatheeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    TextView textView;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);

        textView.animate().translationXBy(45f).alpha(1f).setDuration(1000);
        imageView.animate().translationXBy(-45f).alpha(1f).setDuration(1000);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}