package com.example.syntaxgo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    // Declaring these globally so I can kill the handler later to prevent memory leaks
    private Handler splashHandler;
    private Runnable splashRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashHandler = new Handler();

        // Setting up the transition logic to the main screen
        splashRunnable = () -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        };

        splashHandler.postDelayed(splashRunnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cleaning up the callbacks to avoid zombie handlers if the user closes the app early
        if (splashHandler != null && splashRunnable != null) {
            splashHandler.removeCallbacks(splashRunnable);
        }
    }
}