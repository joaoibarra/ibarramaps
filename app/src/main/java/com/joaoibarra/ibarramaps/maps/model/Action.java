package com.joaoibarra.ibarramaps.maps.model;

import android.Manifest;

/**
 * Created by joaoibarra on 07/08/17.
 */

public class Action {
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 66;
    public static final Action ACCESS_LOCATION = new Action(PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);

    private int code;
    private String permission;

    private Action(int value, String name) {
        this.code = value;
        this.permission = name;
    }

    public int getCode() {
        return code;
    }

    public String getPermission() {
        return permission;
    }
}
