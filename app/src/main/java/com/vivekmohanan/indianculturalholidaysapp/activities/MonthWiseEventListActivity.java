package com.vivekmohanan.indianculturalholidaysapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vivekmohanan.indianculturalholidaysapp.R;
import com.vivekmohanan.indianculturalholidaysapp.adapters.EventsListAdapter;
import com.vivekmohanan.indianculturalholidaysapp.adapters.MonthListAdapter;
import com.vivekmohanan.indianculturalholidaysapp.managers.EventsListManager;
import com.vivekmohanan.indianculturalholidaysapp.models.EventDetails;
import com.vivekmohanan.indianculturalholidaysapp.utils.Utils;

import java.util.Date;

public class MonthWiseEventListActivity extends AppCompatActivity {

    private MonthListAdapter monthListAdapter;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null) {
                return;
            }
            switch (intent.getAction()) {
                case EventsListManager.BROADCAST_EVENTS_LIST_CHANGED:


                    monthListAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_wise_event_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_view_activity_month_wise_event_list_month_list);
        TextView tvYear = findViewById(R.id.text_view_activity_month_wise_event_list_year);



        monthListAdapter = new MonthListAdapter(new EventsListAdapter.SelectionListener() {
            @Override
            public void onSelection(EventDetails eventDetails) {
                Intent intent =  new Intent(MonthWiseEventListActivity.this, EventDetailsActivity.class);
                intent.putExtra(MainActivity.EXTRA_EVENT_DETAILS,eventDetails);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(monthListAdapter);




        String yearString = Utils.dateToString(new Date(), "yyyy").toUpperCase();
        tvYear.setText(yearString);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EventsListManager.BROADCAST_EVENTS_LIST_CHANGED);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);



    }

    public void onMonthWiseBackPress(View view) {
        finish();
    }
}
