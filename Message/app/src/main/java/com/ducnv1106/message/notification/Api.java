package com.ducnv1106.message.notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAaGtGp-s:APA91bGZtrv6eu-gTSwMHQp7T58FYR2iCzkEUupZjAB-trVqXI-oLua5XFauX6ysFeJffZeJJWGS1eUGEj2DuhaRKvNcEorckxcAK2ltjt8Q2A0tnBvKYc6bcyD10e8hh-g4P8kbppXe"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> senNotification(@Body Sender body);
}
