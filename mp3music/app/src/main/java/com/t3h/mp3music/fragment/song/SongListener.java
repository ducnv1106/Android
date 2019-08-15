package com.t3h.mp3music.fragment.song;

import com.t3h.mp3music.adapter.BaseAdapter;
import com.t3h.mp3music.model.Song;

public interface SongListener extends BaseAdapter.BaseItemListener {
    void SongClicked(Song song);
}

