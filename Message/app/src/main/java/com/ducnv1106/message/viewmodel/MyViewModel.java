package com.ducnv1106.message.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ducnv1106.message.api.RequestApi;
import com.ducnv1106.message.model.User;

public class MyViewModel extends ViewModel {

    private static MutableLiveData<User> user;
    private RequestApi requestApi;

    public void initUser(){
//        if(user !=null){
//            user = requestApi.readUser();
//        }
        requestApi = new RequestApi();
        user = requestApi.readUser();
    }

    public MutableLiveData<User> getUser() {
        return user;
    }


}
