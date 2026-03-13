package com.vprop.vtrader.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash);

        if (getActionBar() != null) {
            getActionBar().hide();
        }

        // Transition on the next frame so the splash can draw once.
        getWindow().getDecorView().post(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        });
    }
}
