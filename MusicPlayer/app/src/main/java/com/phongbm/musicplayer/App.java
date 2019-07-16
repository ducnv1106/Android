package com.phongbm.musicplayer;

import android.app.Application;

import com.phongbm.musicplayer.api.response.UserResponse;
import com.phongbm.musicplayer.service.MP3Service;

public class App extends Application {

    private MP3Service service;
    private UserResponse user;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public MP3Service getService() {
        return service;
    }

    public void setService(MP3Service service) {
        this.service = service;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
