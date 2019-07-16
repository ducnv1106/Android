package com.t3h.miniproject.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.miniproject.NewsAdapter;
import com.t3h.miniproject.R;
import com.t3h.miniproject.model.News;

import java.util.ArrayList;

public class FavoriteFragment extends BaseFragment {
    public FavoriteFragment(RecyclerView lv_news, ArrayList<News> data, NewsAdapter adapter) {
        super(lv_news, data, adapter);
    }

    @Override
    protected int getRecycleview() {
        return R.id.lv_favorite;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public String getTitle() {
        return "Favorite";
    }

}
