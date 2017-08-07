package com.joaoibarra.ibarramaps.maps.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.joaoibarra.ibarramaps.maps.contract.MapContract;
import com.joaoibarra.ibarramaps.maps.model.Action;

/**
 * Created by joaoibarra on 07/08/17.
 */

public class PermissionInteractor extends Application
        implements MapContract.PermissionInteractorContract{

    @Override
    public void requestLocationPermission(Activity _activity) {
        checkAndRequestPermission(_activity, Action.ACCESS_LOCATION);
    }

    public void checkAndRequestPermission(Activity _activity, Action action) {
        if (!hasSelfPermission(_activity, action)) {
            requestSelfPermission(_activity, action);
        }
    }

    @Override
    public boolean hasSelfPermission(Activity _activity, Action action) {
       return ContextCompat.checkSelfPermission(_activity, action.getPermission()) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestSelfPermission(Activity _activity, Action action){
        ActivityCompat.requestPermissions(_activity,
                new String[]{action.getPermission()},
                action.getCode());
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode,
                                              @NonNull String permissions[],
                                              @NonNull int[] grantResults,
                                              Action action){
        if (requestCode == action.getCode()
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                return true;
        }
        return false;
    }
}
