package com.t3h.miniproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.miniproject.MainActivity;
import com.t3h.miniproject.NewsAdapter;
import com.t3h.miniproject.R;
import com.t3h.miniproject.WebActivity;
import com.t3h.miniproject.model.Constant;
import com.t3h.miniproject.model.News;

import java.util.ArrayList;

public class NewsFragment extends BaseFragment implements NewsAdapter.ItemClickListener {
    private NewsAdapter adapter;
    private RecyclerView lv_News;
    private ArrayList<News> data = new ArrayList<>();
    public NewsFragment() {
    }

    public NewsFragment(ArrayList<News> data) {
        this.data = data;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.v(getClass().getName(),"onActivityCrated");
        super.onActivityCreated(savedInstanceState);
        adapter = new NewsAdapter(getContext());
        adapter.setData(data);
        data.size();
        lv_News = getActivity().findViewById(R.id.lv_news);
        lv_News.setAdapter(adapter);
        adapter.setItemClickListener(this);

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_news;
    }

    @Override
    public String getTitle() {
        return "News";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public NewsAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onDestroy() {
        Log.v(getClass().getName(),"Destroy");
        super.onDestroy();
    }

    @Override
    public void onPause() {
        Log.v(getClass().getName(),"onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.v(getClass().getName(),"onStop");
        super.onStop();
    }

    public void setAdapter(NewsAdapter adapter) {
        this.adapter = adapter;
    }

    public void setData(ArrayList<News> data) {
        this.data = data;
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent=new Intent(getContext(), WebActivity.class);
        intent.putExtra(Constant.EXTRA_URL,data.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onItemLongClicked(int position) {

    }
}
