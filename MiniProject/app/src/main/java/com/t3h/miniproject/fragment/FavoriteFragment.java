package com.t3h.miniproject.fragment;

import android.os.Bundle;
import android.util.Log;

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
}
