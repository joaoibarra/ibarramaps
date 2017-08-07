package com.joaoibarra.ibarramaps.maps.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joaoibarra on 29/07/17.
 */


@Entity(tableName = "favorite")
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    private int fid;

    @SerializedName("name")
    String name;

    @SerializedName("latitude")
    double latitude;

    @SerializedName("longitude")
    double longitude;

    public Favorite(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getFid() {
        return fid;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
