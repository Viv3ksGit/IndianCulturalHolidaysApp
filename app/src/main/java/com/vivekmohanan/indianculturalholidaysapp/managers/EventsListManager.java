package com.vivekmohanan.indianculturalholidaysapp.managers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vivekmohanan.indianculturalholidaysapp.R;
import com.vivekmohanan.indianculturalholidaysapp.models.EventDetails;
import com.vivekmohanan.indianculturalholidaysapp.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

public class EventsListManager {

    public static final String BROADCAST_EVENTS_LIST_CHANGED = "BROADCAST_EVENTS_LIST_CHANGED";

    private static EventsListManager instance = null;

    private Context context;
    private ArrayList<EventDetails> eventDetailsArrayList = new ArrayList<>();


    private EventsListManager(Context context) {
        this.context = context;

        observeSingleTime();
    }

    public static synchronized EventsListManager getInstance(Context context) {


        if (instance == null) {
            instance = new EventsListManager(context);
        }

        return instance;
    }

    public ArrayList<EventDetails> getEventDetailsArrayList() {
        return eventDetailsArrayList;
    }

    public ArrayList<EventDetails> getEventDetailsListOfDate(Date date) {

        ArrayList<EventDetails> list = new ArrayList<>();


        for (EventDetails eventDetails : eventDetailsArrayList) {
            Date dateOfEvent = new Date(eventDetails.getEventDate());

            String string = Utils.dateToString(dateOfEvent, "yyyy-MM-dd").toUpperCase();
            Date processedDate = Utils.stringToDate(string, "yyyy-MM-dd");

            if (processedDate != null && processedDate.getTime() == date.getTime()) {
                list.add(eventDetails);
            }
        }
        return list;
    }

    public ArrayList<EventDetails> getEventDetailsListFromDate(Date date) {

        ArrayList<EventDetails> list = new ArrayList<>();


        for (EventDetails eventDetails : eventDetailsArrayList) {
            Date dateOfEvent = new Date(eventDetails.getEventDate());

            if (dateOfEvent.getTime() > date.getTime()) {
                list.add(eventDetails);
            }
        }
        return list;
    }

    public ArrayList<EventDetails> getEventDetailsForMonthStarting(Date date) {

        long startingMillis = date.getTime();


        long endingMillisOfMonth = getDateForFirstDayOfNextMonth(date).getTime();


        ArrayList<EventDetails> list = new ArrayList<>();


        for (EventDetails eventDetails : eventDetailsArrayList) {
            Date dateOfEvent = new Date(eventDetails.getEventDate());

            if (dateOfEvent.getTime() >= startingMillis && dateOfEvent.getTime() < endingMillisOfMonth) {
                list.add(eventDetails);
            }
        }
        return list;
    }

    private Date getDateForFirstDayOfNextMonth(Date date) {
        String monthNumberString = Utils.dateToString(date, "MM").toUpperCase();
        String yearString = Utils.dateToString(date, "yyyy").toUpperCase();

        int monthNumber = Integer.parseInt(monthNumberString);
        int yearNumber = Integer.parseInt(yearString);
        int nextMonthNumber = monthNumber + 1;

        if (monthNumber == 12) {
            nextMonthNumber = 1;
            yearNumber += yearNumber;
        }

        String dateString = yearNumber + "-" + nextMonthNumber + "-01";
        return Utils.stringToDate(dateString, "yyyy-MM-dd");

    }

    private void observeSingleTime() {
        FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.event_details)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EventDetails eventDetails = snapshot.getValue(EventDetails.class);
                    if (eventDetails != null) {
                        eventDetails.setId(snapshot.getKey());
                        if (!eventDetailsArrayList.contains(eventDetails)) {
                            eventDetailsArrayList.add(eventDetails);
                        }
                    }
                }

                sendBroadcast();

                observeEventsList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void observeEventsList() {


        FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.event_details)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                EventDetails eventDetails = dataSnapshot.getValue(EventDetails.class);
                if (eventDetails != null) {
                    eventDetails.setId(dataSnapshot.getKey());
                    if (!eventDetailsArrayList.contains(eventDetails)) {
                        eventDetailsArrayList.add(eventDetails);
                        sendBroadcast();
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                EventDetails eventDetails = dataSnapshot.getValue(EventDetails.class);
                if (eventDetails != null) {
                    eventDetails.setId(dataSnapshot.getKey());
                    int index = eventDetailsArrayList.indexOf(eventDetails);
                    if (index != -1) {
                        eventDetailsArrayList.set(index, eventDetails);
                        sendBroadcast();
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                EventDetails eventDetails = dataSnapshot.getValue(EventDetails.class);
                if (eventDetails != null) {
                    eventDetails.setId(dataSnapshot.getKey());
                    int index = eventDetailsArrayList.indexOf(eventDetails);
                    if (index != -1) {
                        eventDetailsArrayList.remove(index);
                        sendBroadcast();
                    }
                }

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendBroadcast() {

        Intent intent = new Intent(BROADCAST_EVENTS_LIST_CHANGED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


}
