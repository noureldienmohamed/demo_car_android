package com.softxpert.softxpert.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.softxpert.softxpert.R;

import java.util.Objects;

public class Splash extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler(Objects.requireNonNull(Looper.myLooper())).postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {
                
                    Intent intent1 = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
            }

        }, 3000);


    }
}