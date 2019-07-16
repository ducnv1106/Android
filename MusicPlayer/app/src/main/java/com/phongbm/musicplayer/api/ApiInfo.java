package com.phongbm.musicplayer.api;

import com.phongbm.musicplayer.api.response.MusicResponse;
import com.phongbm.musicplayer.api.response.UserResponse;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInfo {

    @FormUrlEncoded
    @POST("register.php")
    Call<Response> register(@Field("user_name") String userName,
                            @Field("password") String password,
                            @Field("name") String name);

    @FormUrlEncoded
    @POST("login.php")
    Call<UserResponse> login(@Field("user_name") String userName,
                             @Field("password") String password);

    @GET("music.php")
    Call<MusicResponse> getMusic();
}
