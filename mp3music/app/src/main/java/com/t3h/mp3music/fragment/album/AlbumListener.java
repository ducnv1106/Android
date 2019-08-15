package com.t3h.mp3music.fragment.album;

import com.t3h.mp3music.adapter.BaseAdapter;
import com.t3h.mp3music.model.Album;

public interface AlbumListener extends BaseAdapter.BaseItemListener {
    void AlbumCLicked(Album album);
}
