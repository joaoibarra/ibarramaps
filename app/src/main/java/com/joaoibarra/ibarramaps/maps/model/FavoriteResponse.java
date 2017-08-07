package com.joaoibarra.ibarramaps.maps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by joaoibarra on 06/08/17.
 */

public class FavoriteResponse {
    @SerializedName("favorites")
    @Expose
    List<Favorite> favorites = null;

    public List<Favorite> getFavorites() {
        return favorites;
    }
}
