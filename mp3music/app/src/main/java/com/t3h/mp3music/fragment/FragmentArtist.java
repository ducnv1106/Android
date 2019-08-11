package com.t3h.mp3music.fragment;

import com.t3h.mp3music.R;

public class FragmentArtist extends BaseFragment {

    @Override
    public String getTtile() {
        return "Artist";
    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_artist;
    }
}
