package com.joaoibarra.ibarramaps.base.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joaoibarra on 30/07/17.
 */

public class ApiModel {
    public static Retrofit start(){
        return new Retrofit.Builder()
                .baseUrl("http://pastebin.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
