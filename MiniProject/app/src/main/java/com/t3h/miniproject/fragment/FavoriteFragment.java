package com.t3h.miniproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.miniproject.NewsAdapter;
import com.t3h.miniproject.R;
import com.t3h.miniproject.model.News;

import java.util.ArrayList;

public class FavoriteFragment extends BaseFragment {
    public FavoriteFragment() {
    }

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
    public String getTitle() {
        return "Favorite";
    }

    @Override
    public void onItemLongClicked(final int position, View v) {
        PopupMenu menu_favorite=new PopupMenu(getContext(),v);
        menu_favorite.getMenuInflater().inflate(R.menu.menu_favorite,menu_favorite.getMenu());
        menu_favorite.show();
        menu_favorite.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getData().remove(position);
                getAdapter().setData(getData());
                return false;
            }
        });
    }
}
