package com.t3h.mp3music.fragment.artist;

import com.t3h.mp3music.adapter.BaseAdapter;

import com.t3h.mp3music.model.Artist;

public interface ArtistListener extends BaseAdapter.BaseItemListener {
    void ArtistClicker(Artist artist);

}
