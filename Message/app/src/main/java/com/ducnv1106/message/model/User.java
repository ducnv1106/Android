package com.ducnv1106.message.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class User extends BaseModel implements Serializable {

    private String userId;
    private String username;
    private ArrayList<String> email;
    private ArrayList<String> phone;
    private String password;
    private String avatar;
    private String status;
    private String birthday;
    private String gender;
    private String interested;
    private String country;
    private String religious;
    private String political;
    private ArrayList<String> domin;
    private String address;
    private String coverimage;

    public User(String userId, String username, ArrayList<String> email, ArrayList<String> phone, String password, String avatar, String status, String birthday
            , String gender, String interested, String religious, String political, String country, ArrayList<String> domin,String arress,String coverimage) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.status = status;
        this.birthday = birthday;
        this.gender = gender;
        this.interested = interested;
        this.religious = religious;
        this.political = political;
        this.country = country;
        this.domin = domin;
        this.phone = phone;
        this.address=arress;
        this.coverimage=coverimage;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<String> phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInterested() {
        return interested;
    }

    public void setInterested(String interested) {
        this.interested = interested;
    }

    public String getReligious() {
        return religious;
    }

    public void setReligious(String religious) {
        this.religious = religious;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<String> getDomin() {
        return domin;
    }

    public void setDomin(ArrayList<String> domin) {
        this.domin = domin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }
}


