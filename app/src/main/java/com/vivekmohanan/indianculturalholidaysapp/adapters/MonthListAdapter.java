package com.vivekmohanan.indianculturalholidaysapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vivekmohanan.indianculturalholidaysapp.R;
import com.vivekmohanan.indianculturalholidaysapp.managers.EventsListManager;
import com.vivekmohanan.indianculturalholidaysapp.models.EventDetails;
import com.vivekmohanan.indianculturalholidaysapp.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MonthListAdapter extends RecyclerView.Adapter<MonthListAdapter.ViewHolder> {

    private EventsListAdapter.SelectionListener selectionListener;

    public MonthListAdapter(EventsListAdapter.SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_month_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        String monthName = "";

        switch (position) {
            case Calendar.JANUARY:

                monthName = "JANUARY";
                break;
            case Calendar.FEBRUARY:
                monthName = "FEBRUARY";
                break;
            case Calendar.MARCH:
                monthName = "MARCH";
                break;
            case Calendar.APRIL:
                monthName = "APRIL";
                break;
            case Calendar.MAY:
                monthName = "MAY";
                break;
            case Calendar.JUNE:
                monthName = "JUNE";
                break;
            case Calendar.JULY:
                monthName = "JULY";
                break;
            case Calendar.AUGUST:
                monthName = "AUGUST";
                break;
            case Calendar.SEPTEMBER:
                monthName = "SEPTEMBER";
                break;
            case Calendar.OCTOBER:
                monthName = "OCTOBER";
                break;
            case Calendar.NOVEMBER:
                monthName = "NOVEMBER";
                break;
            case Calendar.DECEMBER:
                monthName = "DECEMBER";
                break;
        }
        holder.tvMonth.setText(monthName);

        Date startingOfMonth = getFirstDateOfMonth(monthName);

        ArrayList<EventDetails> list = EventsListManager.getInstance(holder.itemView.getContext()).getEventDetailsForMonthStarting(startingOfMonth);

        EventsListAdapter adapter = new EventsListAdapter(list,
                new EventsListAdapter.SelectionListener() {
                    @Override
                    public void onSelection(EventDetails eventDetails) {
                        selectionListener.onSelection(eventDetails);

                    }
                });

        holder.rvEventList.setAdapter(adapter);



    }

    @Override
    public int getItemCount() {
        return 12;
    }

    private Date getFirstDateOfMonth(String monthName) {

        String yearString = Utils.dateToString(new Date(), "yyyy").toUpperCase();


        String dateName = "01-" + monthName + "-" + yearString + "-00-00-00";

        return Utils.stringToDate(dateName, "dd-MMMM-yyyy-HH-mm-ss");
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMonth;
        RecyclerView rvEventList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMonth = itemView.findViewById(R.id.text_view_activity_month_wise_event_list_month);
            rvEventList = itemView.findViewById(R.id.text_view_activity_month_wise_event_list_jan);
        }
    }
}
