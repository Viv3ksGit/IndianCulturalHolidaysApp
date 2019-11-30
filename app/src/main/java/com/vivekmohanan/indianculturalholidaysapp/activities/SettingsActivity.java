package com.vivekmohanan.indianculturalholidaysapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vivekmohanan.indianculturalholidaysapp.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    public void onFeedbackAction(View view) {
        Intent i = new Intent(SettingsActivity.this, SendMailActivity.class);
        startActivity(i);
    }

    public void onReportBugsAction(View view) {

        new AlertDialog.Builder(SettingsActivity.this)
                .setTitle("Alert!!")
                .setMessage("This function is currently unavailable")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }

    public void onHelpAction(View view) {
        new AlertDialog.Builder(SettingsActivity.this)
                .setTitle("Alert!!")
                .setMessage("This function is currently unavailable")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    public void onSettingsBackPress(View view) {

        finish();
    }
}