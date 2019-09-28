package com.e.driver.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.e.driver.R;
import com.e.driver.utils.Constants;
import com.e.driver.utils.SamsPrefs;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       // getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               if (SamsPrefs.getBoolean(SplashActivity.this, Constants.LOGGEDIN)){
                   Intent intent = new Intent(SplashActivity.this,DashBoardNewactivity.class);
                   startActivity(intent);
                   overridePendingTransition(R.anim.enter, R.anim.exit);

                   finish();
               }else{
                   Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                   startActivity(intent);
                   overridePendingTransition(R.anim.enter, R.anim.exit);

                   finish();
               }

            }
        },3*1000);
    }
}
