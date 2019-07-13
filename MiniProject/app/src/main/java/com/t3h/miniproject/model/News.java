package com.t3h.miniproject.model;

import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String desc;

    @SerializedName("url")
    private String url;

    @SerializedName("urlToImage")
    private String img;

    @SerializedName("publishedAt")
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
