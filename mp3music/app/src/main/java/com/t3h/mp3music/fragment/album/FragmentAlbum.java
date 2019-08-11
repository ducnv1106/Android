package com.t3h.mp3music.fragment.album;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.t3h.mp3music.R;
import com.t3h.mp3music.adapter.BaseAdapter;
import com.t3h.mp3music.databinding.FragmentAlbumBinding;
import com.t3h.mp3music.fragment.BaseFragment;
import com.t3h.mp3music.model.Album;
import com.t3h.mp3music.systemdata.SystemData;

public class FragmentAlbum extends BaseFragment<FragmentAlbumBinding> implements AlbumListener {

    private BaseAdapter<Album> adapter;
    private SystemData data;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data=new SystemData(getContext());
        adapter=new BaseAdapter<>(getContext(),R.layout.item_album);
        adapter.setData(data.getAlbum());
        adapter.setListener(this);
        binding.lvAlbum.setAdapter(adapter);
    }

    @Override
    public String getTtile() {
        return "Album";
    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_album;
    }

    @Override
    public void AlbumCLicked() {

    }
}
