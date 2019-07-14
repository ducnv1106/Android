package com.t3h.miniproject.fragment;

import com.t3h.miniproject.R;

public class NewsFragment extends BaseFragment {
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_news;
    }

    @Override
    public String getTitle() {
        return "News";
    }
}
