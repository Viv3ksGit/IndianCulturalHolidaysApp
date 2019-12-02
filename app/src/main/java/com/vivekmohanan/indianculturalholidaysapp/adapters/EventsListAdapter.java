package com.vivekmohanan.indianculturalholidaysapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vivekmohanan.indianculturalholidaysapp.R;
import com.vivekmohanan.indianculturalholidaysapp.models.EventDetails;
import com.vivekmohanan.indianculturalholidaysapp.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    private ArrayList<EventDetails> eventDetailsArrayList = new ArrayList<>();
    private SelectionListener selectionListener;

    public EventsListAdapter(ArrayList<EventDetails> list, SelectionListener selectionListener) {
        this.eventDetailsArrayList.addAll(list);
        this.selectionListener = selectionListener;
    }

    public void updateData(ArrayList<EventDetails> list) {
        this.eventDetailsArrayList.clear();
        this.eventDetailsArrayList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EventDetails eventDetails = eventDetailsArrayList.get(position);

        holder.textViewEventName.setText(eventDetails.getEventName());

        String date = Utils.dateToString(new Date(eventDetails.getEventDate()), "dd MMM").toUpperCase();

        holder.textViewEventDate.setText(date);

        Picasso.get().load(eventDetails.getImageUrl()).into(holder.imageViewBg);
    }

    @Override
    public int getItemCount() {
        return eventDetailsArrayList.size();
    }

    public interface SelectionListener {
        void onSelection(EventDetails eventDetails);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewBg;
        TextView textViewEventName;
        TextView textViewEventDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewBg = itemView.findViewById(R.id.image_view_item_event_details_bg);
            textViewEventName = itemView.findViewById(R.id.text_view_item_event_details_name);
            textViewEventDate = itemView.findViewById(R.id.text_view_item_event_details_subtitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectionListener.onSelection(eventDetailsArrayList.get(getAdapterPosition()));
                }
            });


        }
    }
}
