package com.joaoibarra.ibarramaps.maps.presenter;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.joaoibarra.ibarramaps.base.persitence.AppDatabase;
import com.joaoibarra.ibarramaps.maps.contract.MapContract;

/**
 * Created by joaoibarra on 07/08/17.
 */

public class DatabaseInteractor
        implements MapContract.DatabaseInteractorContract{
    AppDatabase db;
    public void open(Activity _activity){
        this.db = Room.databaseBuilder(_activity,
                AppDatabase.class, "database-ibarramaps").allowMainThreadQueries().build();

    }

    public AppDatabase getDb() {
        return this.db;
    }
}
