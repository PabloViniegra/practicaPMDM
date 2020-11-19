package com.example.practicapmdm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pool implements Serializable {
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
