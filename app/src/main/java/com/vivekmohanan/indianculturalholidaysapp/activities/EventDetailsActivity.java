package com.vivekmohanan.indianculturalholidaysapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.squareup.picasso.Picasso;
import com.vivekmohanan.indianculturalholidaysapp.R;
import com.vivekmohanan.indianculturalholidaysapp.managers.EventsListManager;
import com.vivekmohanan.indianculturalholidaysapp.models.EventDetails;

import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity {

    private  TextView textViewEventName;
    private ImageView imageViewEventBg;
    private  TextView textViewEventSubtitle;
    private  TextView textViewEventDescription;

    private EventDetails eventDetails;
    private TextView textViewRituals;
    private TextView textViewFacts;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null) {
                return;
            }
            switch (intent.getAction()) {
                case EventsListManager.BROADCAST_EVENTS_LIST_CHANGED:
                    ArrayList<EventDetails> list = EventsListManager.getInstance(EventDetailsActivity.this).getEventDetailsArrayList();

                    for(EventDetails event : list){
                        if(eventDetails.equals(event)){
                            eventDetails = event;
                            updateData();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventDetails = getIntent().getParcelableExtra(MainActivity.EXTRA_EVENT_DETAILS);

        textViewEventName = findViewById(R.id.text_view_activity_event_details_name);
        imageViewEventBg = findViewById(R.id.image_view_activity_event_details_bg);
        textViewEventSubtitle = findViewById(R.id.text_view_activity_event_details_subtitle);
        textViewEventDescription = findViewById(R.id.text_view_activity_event_details_description);
        textViewRituals = findViewById(R.id.text_view_activity_event_details_rituals);
        textViewFacts = findViewById(R.id.text_view_activity_event_details_facts);

        updateData();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EventsListManager.BROADCAST_EVENTS_LIST_CHANGED);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }


    private void updateData(){
        textViewEventName.setText(eventDetails.getEventName());
        textViewEventDescription.setText(eventDetails.getDescription());
        textViewRituals.setText(eventDetails.getRituals());
        textViewFacts.setText(eventDetails.getFacts());

        Picasso.get().load(eventDetails.getImageUrl()).into(imageViewEventBg);
    }

    public void onEventDetailsBackPress(View view) {
        finish();
    }
}
