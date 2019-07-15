package com.t3h.miniproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsReponsive {

    @SerializedName("articles")
    private ArrayList<News> news;

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }

    public ArrayList<News> getNews() {
        return news;
    }
}
