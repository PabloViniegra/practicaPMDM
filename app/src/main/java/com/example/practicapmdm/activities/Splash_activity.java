package com.example.practicapmdm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.practicapmdm.R;
import com.example.practicapmdm.constants.Constants;

public class Splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_activity);
        new Handler().postDelayed(() -> {
            Intent intent= new Intent(Splash_activity.this, InitHomeActivity.class);
            startActivity(intent);
            finish();
        }, Constants.SPLASH_TIME);
    }
}