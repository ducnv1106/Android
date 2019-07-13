package com.t3h.miniproject.api;

import com.t3h.miniproject.model.NewsReponsive;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("everything")
    Call<NewsReponsive> getNews(
            @Query("q") String keySearch,
            @Query("apiKey") String key,
            @Query("language") String lan
    );

}
