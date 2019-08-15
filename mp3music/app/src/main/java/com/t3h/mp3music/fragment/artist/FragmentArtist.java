package com.t3h.mp3music.fragment.artist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.t3h.mp3music.Constant;
import com.t3h.mp3music.R;
import com.t3h.mp3music.activity.SongArtistActivity;
import com.t3h.mp3music.adapter.BaseAdapter;
import com.t3h.mp3music.databinding.FragmentArtistBinding;
import com.t3h.mp3music.fragment.BaseFragment;
import com.t3h.mp3music.model.Artist;
import com.t3h.mp3music.systemdata.SystemData;

public class FragmentArtist extends BaseFragment<FragmentArtistBinding> implements ArtistListener{

    private BaseAdapter<Artist> adapter;
    private SystemData data;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data=new SystemData(getContext());
        data.getAlbum();
        adapter=new BaseAdapter<>(getContext(),R.layout.item_artist);
        data.getArtist().size();
        adapter.setData(data.getArtist());
        adapter.setListener(this);
        binding.lvArtist.setAdapter(adapter);
    }

    @Override
    public String getTtile() {
        return "Artist";
    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_artist;
    }

    @Override
    public void ArtistClicker(Artist artist) {

        Intent intent=new Intent(getContext(),SongArtistActivity.class);
        intent.putExtra(Constant.EXTRA_ARTIST,artist);
        startActivity(intent);
    }
}
