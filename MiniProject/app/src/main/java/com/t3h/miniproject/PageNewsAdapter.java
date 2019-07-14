package com.t3h.miniproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.t3h.miniproject.fragment.BaseFragment;
import com.t3h.miniproject.fragment.FavoriteFragment;
import com.t3h.miniproject.fragment.NewsFragment;
import com.t3h.miniproject.fragment.SavedFragment;

public class PageNewsAdapter extends FragmentPagerAdapter {
    private BaseFragment[] fms;

    public PageNewsAdapter(FragmentManager fm,BaseFragment...fms) {
        super(fm);
        this.fms = fms;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fms[position];
    }

    @Override
    public int getCount() {
        return fms.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fms[position].getTitle();
    }
}
