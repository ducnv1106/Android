package com.t3h.miniproject.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.t3h.miniproject.R;

public class FavoriteFragment extends BaseFragment {
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
