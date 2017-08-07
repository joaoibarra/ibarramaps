package com.joaoibarra.ibarramaps.base.persitence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.joaoibarra.ibarramaps.maps.model.Favorite;

/**
 * Created by joaoibarra on 07/08/17.
 */

@Database(entities = {Favorite.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();
}

