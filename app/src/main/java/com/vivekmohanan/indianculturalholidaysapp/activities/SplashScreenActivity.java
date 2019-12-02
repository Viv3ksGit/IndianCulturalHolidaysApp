package com.vivekmohanan.indianculturalholidaysapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.vivekmohanan.indianculturalholidaysapp.R;
import com.vivekmohanan.indianculturalholidaysapp.adapters.MonthListAdapter;
import com.vivekmohanan.indianculturalholidaysapp.managers.UserAuthenticationManager;

public class SplashScreenActivity extends AppCompatActivity {

    private  Handler handler  = new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(UserAuthenticationManager.getInstance(SplashScreenActivity.this).getCurrentUser() == null){
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);

                    finish();
                }else {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);

                    finish();
                }


            }
        }, 1000);
    }

    @Override
    protected void onPause() {

        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }
}
