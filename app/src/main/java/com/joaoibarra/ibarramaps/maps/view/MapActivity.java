package com.joaoibarra.ibarramaps.maps.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joaoibarra.ibarramaps.R;
import com.joaoibarra.ibarramaps.maps.contract.MapContract;
import com.joaoibarra.ibarramaps.maps.model.Favorite;
import com.joaoibarra.ibarramaps.maps.presenter.MapPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        MapContract.MapViewContract{

    private GoogleMap googleMap;
    private MapPresenter mapPresenter;
    private GoogleApiClient googleApiClient;
    private CameraPosition cameraPosition;
    private Location lastKnownLocation;
    private final LatLng defaultLocation = new LatLng(-23.6821604, -46.8754915);
    private boolean hasPermission;
    private boolean isOpenMenu = false;

    private static final int DEFAULT_ZOOM = 17;

    public static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    @BindView(R.id.fab_menu)
    FloatingActionButton fabMenu;

    @BindView(R.id.fab_menu_favorite)
    FloatingActionButton fabMenuFavorite;

    @BindView(R.id.fab_menu_favorites)
    FloatingActionButton fabMenuFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapPresenter = new MapPresenter();
        mapPresenter.attach(this);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        updateCurrentPosition();
        getDeviceLocation();
        mapPresenter.getFavoriteMarkers();    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        hasPermission = mapPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
        updateCurrentPosition();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (googleMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, googleMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onDestroy() {
        googleApiClient.disconnect();
        mapPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void updateCurrentPosition() {
        if (googleMap == null)
            return;

        showCurrentLocation();

        if (!hasPermission) {
            lastKnownLocation = null;
        }
    }

    @Override
    public void getDeviceLocation() {
        mapPresenter.requestPermissions(this);
        hasPermission = mapPresenter.onPermissionsResult(this);

        if (hasPermission){
            try{
                lastKnownLocation = LocationServices.FusedLocationApi
                        .getLastLocation(googleApiClient);
            }catch (SecurityException e){

            }
        }

        if (cameraPosition != null) {
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else if (lastKnownLocation != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastKnownLocation.getLatitude(),
                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    @Override
    public void setMap() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public Marker getCurrentPosition() {
        return null;
    }

    public void showCurrentLocation(){
        mapPresenter.requestPermissions(this);
        hasPermission = mapPresenter.onPermissionsResult(this);
        try {
            googleMap.setMyLocationEnabled(hasPermission);
        }catch (SecurityException e){

        }
        googleMap.getUiSettings().setMyLocationButtonEnabled(hasPermission);
    }

    public void createMarker(Favorite favorite){
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(favorite.getLatitude(), favorite.getLongitude()))
                .title(favorite.getName()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);

        return true;
    }

    private void searchAddress() {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE).build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setFilter(typeFilter)
                    .setBoundsBias(null)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(), 0 /*requestCode*/).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            String message = String.format("Google Play Services não disponível: %s", GoogleApiAvailability.getInstance().getErrorString(e.errorCode));
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void placePicker(){
        mapPresenter.startPlacePickerSearch(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                createMarker(new Favorite(
                        place.getName().toString(),
                        place.getLatLng().latitude,
                        place.getLatLng().longitude));
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                placePicker();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMenu(){
        isOpenMenu=true;
        fabMenuFavorite.animate().translationY(-getResources().getDimension(R.dimen.first_fab_menu));
        fabMenuFavorites.animate().translationY(-getResources().getDimension(R.dimen.second_fab_menu));
    }

    @Override
    public void closeMenu(){
        isOpenMenu=false;
        fabMenuFavorite.animate().translationY(0);
        fabMenuFavorites.animate().translationY(0);
    }


    @OnClick(R.id.fab_menu)
    public void toogleMenu(){
        mapPresenter.clickMenu(isOpenMenu);
    }

    @OnClick(R.id.fab_menu_favorite)
    public void menuFavorite(){
    }

    @OnClick(R.id.fab_menu_favorites)
    public void menuFavorites(){
    }
}
