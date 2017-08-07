package com.joaoibarra.ibarramaps.base.api;

import com.joaoibarra.ibarramaps.maps.model.FavoriteResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by joaoibarra on 30/07/17.
 */

public interface ApiService {
    @GET("raw/M9e1vpTd")
    Call<FavoriteResponse> listFavorites();
}
