package com.t3h.mp3music.fragment.song;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.t3h.mp3music.Constant;
import com.t3h.mp3music.R;
import com.t3h.mp3music.activity.SongPlayActivity;
import com.t3h.mp3music.adapter.BaseAdapter;
import com.t3h.mp3music.databinding.FragmentSongBinding;
import com.t3h.mp3music.fragment.BaseFragment;
import com.t3h.mp3music.model.Song;
import com.t3h.mp3music.systemdata.SystemData;

public class FragmentSong extends BaseFragment<FragmentSongBinding> implements SongListener{

    private BaseAdapter<Song> adapter;
    private SystemData data;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data=new SystemData(getContext());
        data.getAlbum();
        adapter=new BaseAdapter<>(getContext(),R.layout.item_song);
        adapter.setData(data.getDataSong());
        adapter.setListener(this);
        binding.lvSong.setAdapter(adapter);
    }

    @Override
    public String getTtile() {

        return "Song";
    }

    @Override
    protected int getlayout() {

        return R.layout.fragment_song;
    }

    @Override
    public void SongClicked(Song song) {
        Intent intent=new Intent(getContext(), SongPlayActivity.class);
        intent.putExtra(Constant.EXTRA_SONG,song);
        startActivity(intent);
    }
}
