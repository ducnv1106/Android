package com.t3h.miniproject.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerTabStrip;

import com.t3h.miniproject.MainActivity;
import com.t3h.miniproject.NewsAdapter;
import com.t3h.miniproject.R;
import com.t3h.miniproject.WebActivity;
import com.t3h.miniproject.model.Constant;
import com.t3h.miniproject.model.News;

import java.util.ArrayList;

public abstract class BaseFragment  extends Fragment implements NewsAdapter.ItemClickListener {
    private RecyclerView lv_news;
    private ArrayList<News> data=new ArrayList<>();
    private NewsAdapter adapter;

    public BaseFragment(RecyclerView lv_news, ArrayList<News> data, NewsAdapter adapter) {
        this.lv_news = lv_news;
        this.data = data;
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter=new NewsAdapter(getContext());
        lv_news=getActivity().findViewById(getRecycleview());
        adapter.setData(data);
        lv_news.setAdapter(adapter);
        adapter.setItemClickListener(this);
    }

    protected  abstract int getRecycleview();
    protected abstract int getLayoutID();

    public abstract String getTitle();

    @Override
    public void onItemClicked(int position) {
        Intent intent=new Intent(getContext(), WebActivity.class);
        intent.putExtra(Constant.EXTRA_URL,data.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onItemLongClicked(int position, View v) {

    }

    public NewsAdapter getAdapter() {
        return adapter;
    }

    public void setData(ArrayList<News> data) {
        this.data = data;
    }

    public ArrayList<News> getData() {
        return data;
    }
    public void addData(News news){
        data.add(news);
    }
}