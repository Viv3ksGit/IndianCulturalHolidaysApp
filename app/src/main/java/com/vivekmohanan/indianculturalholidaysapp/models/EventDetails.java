package com.vivekmohanan.indianculturalholidaysapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class EventDetails implements Parcelable {

    private String id;
    private String eventName;
    private long eventDate;
    private String imageUrl;
    private String description;
    private String rituals;
    private String facts;

    public EventDetails() {
    }


    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public String getRituals() {
        return rituals;
    }

    public void setRituals(String rituals) {
        this.rituals = rituals;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventDetails)) return false;
        EventDetails that = (EventDetails) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.eventName);
        dest.writeLong(this.eventDate);
        dest.writeString(this.imageUrl);
        dest.writeString(this.description);
        dest.writeString(this.rituals);
        dest.writeString(this.facts);
    }

    protected EventDetails(Parcel in) {
        this.id = in.readString();
        this.eventName = in.readString();
        this.eventDate = in.readLong();
        this.imageUrl = in.readString();
        this.description = in.readString();
        this.rituals = in.readString();
        this.facts = in.readString();
    }

    public static final Creator<EventDetails> CREATOR = new Creator<EventDetails>() {
        @Override
        public EventDetails createFromParcel(Parcel source) {
            return new EventDetails(source);
        }

        @Override
        public EventDetails[] newArray(int size) {
            return new EventDetails[size];
        }
    };
}
