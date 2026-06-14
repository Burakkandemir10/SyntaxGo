package com.example.syntaxgo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Splash screen shown upon app launch.
 * It displays the app identity for a brief moment.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Schedule a transition to the main screen after a delay.
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            // Close this activity so the user doesn't return to it via the back button.
            finish();
        }, 2000); // Wait for 2 seconds.
    }
}