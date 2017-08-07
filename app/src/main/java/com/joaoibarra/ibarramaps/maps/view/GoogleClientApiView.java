package com.joaoibarra.ibarramaps.maps.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.SupportMapFragment;
import com.joaoibarra.ibarramaps.R;
import com.joaoibarra.ibarramaps.maps.contract.MapContract;

/**
 * Created by joaoibarra on 06/08/17.
 */

public class GoogleClientApiView
        implements
        MapContract.GoogleApiClientContract,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    Context context;
    public void start(Context context){
        this.context = context;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    /*    SupportMapFragment mapFragment = (SupportMapFragment) ((Activity) context).getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
