package com.joaoibarra.ibarramaps.maps.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.joaoibarra.ibarramaps.maps.contract.MapContract;
import com.joaoibarra.ibarramaps.maps.model.Action;
import com.joaoibarra.ibarramaps.maps.model.Favorite;
import com.joaoibarra.ibarramaps.maps.model.FavoriteResponse;
import com.joaoibarra.ibarramaps.maps.model.MapService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by joaoibarra on 29/07/17.
 */

public class MapPresenter implements MapContract.MapPresenterContract<MapContract.MapViewContract>{
    MapContract.MapViewContract mapView;

    SearchInteractor searchInteractor;
    PermissionInteractor permissionInteractor;

    @Override
    public void attach(final MapContract.MapViewContract view) {
        mapView = view;
        mapView.setMap();
        searchInteractor = new SearchInteractor();
        permissionInteractor = new PermissionInteractor();
    }

    @Override
    public void detach() {
        mapView = null;
    }

    @Override
    public void getFavoriteMarkers(){
        MapService mapService = new MapService();
        Call<FavoriteResponse> call = mapService.getFavorites();
        call.enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(Call<FavoriteResponse> call, Response<FavoriteResponse> response) {
                Log.i("LOLZIN", "É nóis");
                FavoriteResponse favoriteResponse = response.body();
                for(int i = 0; i < favoriteResponse.getFavorites().size(); i++ )
                    mapView.createMarker(favoriteResponse.getFavorites().get(i));
            }

            @Override
            public void onFailure(Call<FavoriteResponse> call, Throwable t) {
                Log.i("LOLZIN", "Não é nóis");
            }
        });
    }

    @Override
    public void requestPermissions(Activity _activity){
        permissionInteractor.requestLocationPermission(_activity);
    }

    @Override
    public boolean onPermissionsResult(Activity _activity){
        return permissionInteractor.hasSelfPermission(_activity, Action.ACCESS_LOCATION);
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode,
                                    @NonNull String permissions[],
                                    @NonNull int[] grantResults){
        return permissionInteractor.onRequestPermissionsResult(requestCode,permissions,grantResults, Action.ACCESS_LOCATION);
    }

    @Override
    public void startPlacePickerSearch(Activity _activity) {
        searchInteractor.placePickerSearch(_activity);
    }

    public void clickMenu(boolean isOpenMenu){
        if(isOpenMenu)
            mapView.closeMenu();
        else
            mapView.showMenu();
    }
}
