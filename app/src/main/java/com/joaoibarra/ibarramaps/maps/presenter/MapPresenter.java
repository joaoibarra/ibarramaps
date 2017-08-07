package com.joaoibarra.ibarramaps.maps.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.joaoibarra.ibarramaps.maps.contract.MapContract;
import com.joaoibarra.ibarramaps.maps.model.Action;
import com.joaoibarra.ibarramaps.maps.model.Favorite;
import com.joaoibarra.ibarramaps.maps.model.FavoriteResponse;
import com.joaoibarra.ibarramaps.maps.model.MapService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by joaoibarra on 29/07/17.
 */

public class MapPresenter implements MapContract.MapPresenterContract<MapContract.MapViewContract>{
    MapContract.MapViewContract mapView;

    SearchInteractor searchInteractor;
    PermissionInteractor permissionInteractor;
    DatabaseInteractor databaseInteractor;

    @Override
    public void attach(final MapContract.MapViewContract view) {
        mapView = view;
        mapView.setMap();
        searchInteractor = new SearchInteractor();
        permissionInteractor = new PermissionInteractor();
        databaseInteractor = new DatabaseInteractor();

    }

    @Override
    public void detach() {
        mapView = null;
    }

    @Override
    public void getFavoriteMarkers(){
        MapService mapService = new MapService();
        Call<FavoriteResponse> call = mapService.getFavorites();

        List<Favorite> favorites = databaseInteractor.db.favoriteDao().getAll();
        if(favorites.size() <= 0){
            getFavoriteMarkers(call);
        }else{
            /*for(int i = 0; i < favorites.size(); i++ ) {
                mapView.createMarker(favorites.get(i));
            }*/
        }

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

    @Override
    public void clickMenu(boolean isOpenMenu){
        if(isOpenMenu)
            mapView.closeMenu();
        else
            mapView.showMenu();
    }

    public void getFavoriteMarkers(Call call){
        call.enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(Call<FavoriteResponse> call, Response<FavoriteResponse> response) {
                Log.i("LOLZIN", "É nóis");
                FavoriteResponse favoriteResponse = response.body();
                for(int i = 0; i < favoriteResponse.getFavorites().size(); i++ ) {
                    //mapView.createMarker(favoriteResponse.getFavorites().get(i));
                    databaseInteractor.getDb().favoriteDao().insert(favoriteResponse.getFavorites().get(i));
                }
            }

            @Override
            public void onFailure(Call<FavoriteResponse> call, Throwable t) {
                Log.i("LOLZIN", "Não é nóis");
            }
        });
    }

    @Override
    public void startDb(Activity _activity){
        databaseInteractor.open(_activity);
    }

    @Override
    public void addFavorite(double latitude, double longitude){
        Favorite favorite = new Favorite("Novo", latitude, longitude);
        mapView.createMarker(favorite);
        databaseInteractor.getDb().favoriteDao().insert(favorite);
    }

    @Override
    public void getAllFavorites(){
        List<Favorite> favorites = databaseInteractor.getDb().favoriteDao().getAll();
        for(int i = 0; i < favorites.size(); i++)
            mapView.createMarker(favorites.get(i));
    }
}
