package com.joaoibarra.ibarramaps.maps.contract;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.Marker;
import com.joaoibarra.ibarramaps.base.BasePresenter;
import com.joaoibarra.ibarramaps.maps.model.Action;
import com.joaoibarra.ibarramaps.maps.model.Favorite;

/**
 * Created by joaoibarra on 29/07/17.
 */

public interface MapContract {
    interface MapViewContract {
        void setMap();
        Marker getCurrentPosition();
        void updateCurrentPosition();
        void getDeviceLocation();
        void createMarker(Favorite favorite);
        void showMenu();
        void closeMenu();
        /*void getDeviceLocation(GoogleMap googleMap, GoogleApiClient googleApiClient);*/
    }

    interface GoogleApiClientContract{
        void start(Context context);
    }

    interface MapPresenterContract<M> extends BasePresenter<MapContract.MapViewContract> {
        void getFavoriteMarkers();
        void requestPermissions(Activity _activity);
        boolean onPermissionsResult(Activity _activity);
        void startPlacePickerSearch(Activity _activity);
        boolean onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults);
    }

    interface SearchInteractorContract{
        void placePickerSearch(Activity _activity);
    }

    interface PermissionInteractorContract{
        boolean hasSelfPermission(Activity _activity,Action action);
        void requestLocationPermission(Activity _activity);
        void requestSelfPermission(Activity _activity, Action action);
        boolean onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults,
                                           Action action);

    }
}
