package com.ducnv1106.message.notification;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {

    public static Api api;

    public static Api getApi(){
        if(api==null){
            api=new Retrofit.Builder().baseUrl("https://fcm.googleapis.com/").addConverterFactory(GsonConverterFactory.create()).build().create(Api.class);
        }

        return api;
    }
}
