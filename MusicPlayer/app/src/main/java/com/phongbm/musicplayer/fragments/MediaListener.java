package com.phongbm.musicplayer.fragments;

import com.phongbm.musicplayer.base.BaseAdapter;
import com.phongbm.musicplayer.model.MP3Media;

public interface MediaListener<T extends MP3Media>
        extends BaseAdapter.ListItemListener {
    void onItemMediaClick(T t);
}
