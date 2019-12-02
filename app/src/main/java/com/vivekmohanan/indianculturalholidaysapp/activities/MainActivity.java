package com.vivekmohanan.indianculturalholidaysapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vivekmohanan.indianculturalholidaysapp.R;
import com.vivekmohanan.indianculturalholidaysapp.adapters.EventsListAdapter;
import com.vivekmohanan.indianculturalholidaysapp.managers.EventsListManager;
import com.vivekmohanan.indianculturalholidaysapp.models.EventDetails;
import com.vivekmohanan.indianculturalholidaysapp.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CalendarView calenderViewCalender;
    private TextView textViewDate;
    private TextView textViewWeekDay;
    private EventsListAdapter todayEventsListAdapter;
    private EventsListAdapter eventsListAdapter;
    private TextView textViewEventListTodayNoData;
    private TextView textViewEventListNoData;

    private Date selectedDate;

    public static final String EXTRA_EVENT_DETAILS = "EXTRA_EVENT_DETAILS";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null) {
                return;
            }
            switch (intent.getAction()) {
                case EventsListManager.BROADCAST_EVENTS_LIST_CHANGED:


                    todayEventsListAdapter.updateData(EventsListManager.getInstance(MainActivity.this).getEventDetailsListOfDate(selectedDate));
                    notifyTodayNoData();

                    eventsListAdapter.updateData(EventsListManager.getInstance(MainActivity.this).getEventDetailsListFromDate(selectedDate));

                    notifyNoData();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_activity_main_title);
        setSupportActionBar(toolbar);

        calenderViewCalender = findViewById(R.id.calender_view_activity_main_calender);
        textViewDate = findViewById(R.id.text_view_activity_main_date);
        textViewWeekDay = findViewById(R.id.text_view_activity_main_week_day);
        RecyclerView recyclerViewUpcoming = findViewById(R.id.recycler_view_activity_main_event_list);
        RecyclerView recyclerViewToday = findViewById(R.id.recycler_view_activity_main_today_event_list);
        textViewEventListTodayNoData = findViewById(R.id.text_view_activity_main_today_no_data);
        textViewEventListNoData = findViewById(R.id.text_view_activity_main_no_data);


        String string = Utils.dateToString(new Date(), getString(R.string.string_to_date_convertion_format)).toUpperCase();
        selectedDate = stringToDate(string, getString(R.string.string_to_date_convertion_format));

        String stringWeek = Utils.dateToString(selectedDate, getString(R.string.string_to_date_week_fromat)).toUpperCase();
        textViewWeekDay.setText(stringWeek);
        String stringDate = Utils.dateToString(selectedDate, getString(R.string.string_to_date_convertion)).toUpperCase();
        textViewDate.setText(stringDate);

        eventsListAdapter = new EventsListAdapter(EventsListManager.getInstance(this).getEventDetailsListFromDate(selectedDate),
                new EventsListAdapter.SelectionListener() {
                    @Override
                    public void onSelection(EventDetails eventDetails) {
                        Intent intent =  new Intent(MainActivity.this,EventDetailsActivity.class);
                        intent.putExtra(MainActivity.EXTRA_EVENT_DETAILS,eventDetails);
                        startActivity(intent);
                    }
                });

        recyclerViewUpcoming.setAdapter(eventsListAdapter);


        todayEventsListAdapter = new EventsListAdapter(EventsListManager.getInstance(this).getEventDetailsListOfDate(selectedDate),
                new EventsListAdapter.SelectionListener() {
                    @Override
                    public void onSelection(EventDetails eventDetails) {
                        Intent intent =  new Intent(MainActivity.this,EventDetailsActivity.class);
                        intent.putExtra(MainActivity.EXTRA_EVENT_DETAILS,eventDetails);
                        startActivity(intent);
                    }
                });


        recyclerViewToday.setAdapter(todayEventsListAdapter);


        calenderViewCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                String dateString = year + "-" + (month + 1) + "-" + day;
                Date date = stringToDate(dateString, getString(R.string.string_to_date_convertion_format));

                selectedDate = date;
                updateDate();

                todayEventsListAdapter.updateData(EventsListManager.getInstance(MainActivity.this).getEventDetailsListOfDate(selectedDate));
                notifyTodayNoData();

                eventsListAdapter.updateData(EventsListManager.getInstance(MainActivity.this).getEventDetailsListFromDate(selectedDate));

                notifyNoData();


            }
        });


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EventsListManager.BROADCAST_EVENTS_LIST_CHANGED);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    private void updateDate() {
        String string = Utils.dateToString(selectedDate, getString(R.string.string_to_date_convertion)).toUpperCase();
        textViewDate.setText(string);

        String stringWeek = Utils.dateToString(selectedDate, getString(R.string.string_to_date_week_fromat)).toUpperCase();
        textViewWeekDay.setText(stringWeek);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_screen_menu, menu);
        return true;
    }

    public void logoutAndGoToLoginScreen() {

        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSettingsScreen() {

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }

    public void onShareOption(MenuItem item) {

        Intent i = new Intent(this, SendMailActivity.class);
        startActivity(i);
    }

    public void onSettingOptions(MenuItem item) {

        goToSettingsScreen();
    }

    private Date stringToDate(String dateAsString, String type) {
        SimpleDateFormat format = new SimpleDateFormat(type, Locale.getDefault());
        try {
            return format.parse(dateAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void onUserProfileAction(View view) {

        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.pop_over_user_account, popup.getMenu());
        popup.getMenu().getItem(0).setTitle(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.pop_over_user_account_logout:
                        logoutAndGoToLoginScreen();
                        return true;
                }
                return false;
            }
        });

        popup.show();
    }

    public void onChooseViewType(View view) {
//
//        PopupMenu popup = new PopupMenu(this, view);
//        popup.getMenuInflater().inflate(R.menu.pop_over_select_view_type, popup.getMenu());
//
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.pop_over_select_view_type_month_wise:
//                        Intent intent = new Intent(MainActivity.this, MonthWiseEventListActivity.class);
//                        startActivity(intent);
//                        return true;
//                    case R.id.pop_over_select_view_type_region_wise:
//                        return true;
//                }
//                return false;
//            }
//        });
//
//        popup.show();

        Intent intent = new Intent(MainActivity.this, MonthWiseEventListActivity.class);
        startActivity(intent);
    }

    private void notifyNoData() {
        if (eventsListAdapter.getItemCount() == 0) {
            textViewEventListNoData.setVisibility(View.VISIBLE);
        } else {
            textViewEventListNoData.setVisibility(View.GONE);
        }
    }

    private void notifyTodayNoData() {
        if (todayEventsListAdapter.getItemCount() == 0) {
            textViewEventListTodayNoData.setVisibility(View.VISIBLE);
        } else {
            textViewEventListTodayNoData.setVisibility(View.GONE);
        }
    }

    public void onAddHoliday(MenuItem item) {

        startActivity(new Intent(this,AddHolidayActivity.class));
    }
}