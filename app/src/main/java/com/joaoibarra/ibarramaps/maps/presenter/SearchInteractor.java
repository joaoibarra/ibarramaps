package com.joaoibarra.ibarramaps.maps.presenter;

import android.app.Activity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.joaoibarra.ibarramaps.maps.contract.MapContract;

/**
 * Created by joaoibarra on 07/08/17.
 */

public class SearchInteractor implements MapContract.SearchInteractorContract {
    public SearchInteractor() {
    }

    @Override
    public void placePickerSearch(Activity _activity) {
        int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            _activity.startActivityForResult(builder.build(_activity), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
