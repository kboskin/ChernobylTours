package com.tourkiev.chernobyltours.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import static com.tourkiev.chernobyltours.Constants.CHECK_IF_IS_AUTH_PASSED;

public class SplashScreenActivity extends AppCompatActivity {

    private Intent intent;
    private SharedPreferences prefs;


    @Override
    protected void onStart() {
        super.onStart();

        // lines makes activity to become full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (isLoggedIn()) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }


    private boolean isLoggedIn() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getBoolean(CHECK_IF_IS_AUTH_PASSED, false);
    }

}
