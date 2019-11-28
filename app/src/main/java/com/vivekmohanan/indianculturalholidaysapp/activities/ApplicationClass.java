package com.vivekmohanan.indianculturalholidaysapp.activities;

import android.app.Application;

import com.vivekmohanan.indianculturalholidaysapp.managers.UserAuthenticationManager;

public class ApplicationClass extends Application {

    public static Application instance  = null;

    public  static  synchronized  Application getInstance(){
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();


        instance = this;

        UserAuthenticationManager.getInstance(this);


    }
}
