package com.vivekmohanan.indianculturalholidaysapp.managers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAuthenticationManager {

    public static final String BROADCAST_LOGIN_SUCCESSFUL = "BROADCAST_LOGIN_SUCCESSFUL";
    public static final String BROADCAST_LOGIN_FAILED = "BROADCAST_LOGIN_FAILED";


    private static UserAuthenticationManager instance = null;

    private Context context = null;

    private UserAuthenticationManager(Context context) {
        this.context = context;

        observeFirebseAuthentication();
    }

    public static synchronized UserAuthenticationManager getInstance(Context context) {


        if (instance == null) {
            instance = new UserAuthenticationManager(context);
        }

        return instance;
    }

    private void observeFirebseAuthentication() {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    sendLoginFailedBroadcast();
                } else {
                    sendLoginSuccessBroadcast();
                }
            }
        });


    }

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }


    private void sendLoginSuccessBroadcast() {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(BROADCAST_LOGIN_SUCCESSFUL));
    }

    private void sendLoginFailedBroadcast() {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(BROADCAST_LOGIN_FAILED));
    }


}
