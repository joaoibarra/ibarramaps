package com.joaoibarra.ibarramaps.maps.model;

import com.joaoibarra.ibarramaps.base.api.ApiModel;
import com.joaoibarra.ibarramaps.base.api.ApiService;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by joaoibarra on 30/07/17.
 */

public class MapService {
    ApiService apiService;
    public MapService() {
        Retrofit retrofit = ApiModel.start();
        this.apiService = retrofit.create(ApiService.class);
    }

    public Call<FavoriteResponse> getFavorites(){
        return this.apiService.listFavorites();
    }
}
