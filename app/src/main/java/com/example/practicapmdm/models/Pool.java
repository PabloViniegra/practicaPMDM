package com.example.practicapmdm.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pool implements Parcelable {
    @SerializedName("title")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private Location location;

    public Pool (String name, Location location) {
        this.name = name;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Pool{" +
                "name='" + name + '\'' +
                ", location=" + location +
                '}';
    }

    protected Pool(Parcel in) {
        name = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(location, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pool> CREATOR = new Creator<Pool>() {
        @Override
        public Pool createFromParcel(Parcel in) {
            return new Pool(in);
        }

        @Override
        public Pool[] newArray(int size) {
            return new Pool[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }



}
