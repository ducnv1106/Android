package com.phongbm.musicplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.api.ApiBuilder;
import com.phongbm.musicplayer.api.response.MusicResponse;
import com.phongbm.musicplayer.base.BaseAdapter;
import com.phongbm.musicplayer.base.BaseFragment;
import com.phongbm.musicplayer.databinding.FragmentAlbumBinding;
import com.phongbm.musicplayer.databinding.FragmentOnlineBinding;
import com.phongbm.musicplayer.model.Album;
import com.phongbm.musicplayer.model.Music;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineFragment extends BaseFragment<FragmentOnlineBinding> implements Callback<MusicResponse>, MediaListener<Music> {
    private BaseAdapter<Music> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_online;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseAdapter(getContext(), R.layout.item_music);
        adapter.setListener(this);
        binding.lvOnline.setAdapter(adapter);
        ApiBuilder.getApi(getContext()).getMusic()
                .enqueue(this);

    }

    @Override
    public int getTitle() {
        return R.string.online;
    }

    @Override
    public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
        adapter.setData(response.body().getArr());
    }

    @Override
    public void onFailure(Call<MusicResponse> call, Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemMediaClick(Music music) {
        app.getService().setArrMusic(adapter.getData());
        int index = adapter.getData().indexOf(music);
        app.getService().create(index);
    }
}
