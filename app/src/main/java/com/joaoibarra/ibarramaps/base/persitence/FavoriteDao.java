package com.joaoibarra.ibarramaps.base.persitence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.joaoibarra.ibarramaps.maps.model.Favorite;

import java.util.List;

/**
 * Created by joaoibarra on 07/08/17.
 */

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    List<Favorite> getAll();

    @Query("SELECT * FROM favorite WHERE fid IN (:favoriteIds)")
    List<Favorite> loadAllByIds(int[] favoriteIds);

    @Query("SELECT * FROM favorite WHERE name LIKE :name LIMIT 1")
    Favorite findByName(String name);

    @Insert
    void insertAll(Favorite... favorites);

    @Insert
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);
}
