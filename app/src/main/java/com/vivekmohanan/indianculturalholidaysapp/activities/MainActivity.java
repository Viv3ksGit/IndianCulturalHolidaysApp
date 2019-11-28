package com.vivekmohanan.indianculturalholidaysapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vivekmohanan.indianculturalholidaysapp.R;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.CalendarView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CalendarView calender;
    TextView date_view;

    private String strUserEmail;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        calender = findViewById(R.id.calender);
        date_view = findViewById(R.id.date_view);

        calender
                .setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                        String Date = day + "-" + (month + 1) + "-" + year;
                        date_view.setText(Date);
                        calender.setFirstDayOfWeek(2);
                    }
                });



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        strUserEmail = firebaseUser.getEmail().toString();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView UserEmail = headerView.findViewById(R.id.HeaderMail);
        UserEmail.setText(strUserEmail);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent Settings = new Intent(this, MainActivity.class);
            startActivity(Settings);
        }

            // Handle the camera action
//         else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
         else if (id == R.id.nav_tools) {
            Intent Settings = new Intent (this, SettingsActivity.class);
            startActivity(Settings);

        } else if (id == R.id.nav_share) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Alert!!")
                    .setMessage("I am currently working on this page, please come back later")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();

        } else if (id == R.id.Logout) {

            logout();
            finish();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout(){

        FirebaseAuth.getInstance().signOut();

        Intent Signout = new Intent (this, LoginActivity.class);

        //Signout.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Signout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Signout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(Signout);
    }

    public void ViewMonth(MenuItem item) {

        Intent i = new Intent(this, MonthWiseActivity.class);
        startActivity(i);
    }

    public void ViewRegion(MenuItem item) {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alert!!")
                .setMessage("I am currently working on this page, please come back later")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    public void Button7Click(View view) {
    }
    public void Button2Click(View view) {
        Intent i = new Intent(this, ProfilePageActivity.class);
        startActivity(i);
    }
    public void Button3Click(View view) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alert!!")
                .setMessage("I am currently working on this page, please come back later")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
    public void Button4Click(View view) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alert!!")
                .setMessage("I am currently working on this page, please come back later")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
    public void Button5Click(View view) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alert!!")
                .setMessage("I am currently working on this page, please come back later")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
    public void Button8Click(View view) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alert!!")
                .setMessage("I am currently working on this page, please come back later")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}
