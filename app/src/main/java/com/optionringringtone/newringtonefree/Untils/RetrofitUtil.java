package com.optionringringtone.newringtonefree.Untils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static Retrofit retrofit;
    private static API api;

    public static API getApi(){
        Gson gson = new GsonBuilder().setLenient().create();
        if (api == null) {

            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(Configs.BASE_URL);
            builder.addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit = builder.build();
            api = retrofit.create(API.class);
        }
        return api;
    }
}
