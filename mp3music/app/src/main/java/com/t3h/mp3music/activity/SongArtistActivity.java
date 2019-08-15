package com.t3h.mp3music.activity;

import android.content.Intent;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.t3h.mp3music.BR;
import com.t3h.mp3music.Constant;
import com.t3h.mp3music.R;
import com.t3h.mp3music.adapter.BaseAdapter;
import com.t3h.mp3music.databinding.ActivitySongaritstBinding;
import com.t3h.mp3music.fragment.song.SongListener;
import com.t3h.mp3music.model.Album;
import com.t3h.mp3music.model.Artist;
import com.t3h.mp3music.model.Song;
import com.t3h.mp3music.systemdata.SystemData;

import java.io.Serializable;

public class SongArtistActivity extends BaseActivity<ActivitySongaritstBinding> implements SongListener {

    private BaseAdapter<Song> adapter;


    @Override
    protected void initView() {
        receive();

    }

    @Override
    protected int getLayoutID() {

        return R.layout.activity_songaritst;
    }

    public void receive(){
        Intent intent=getIntent();
        Artist artist= (Artist) intent.getSerializableExtra(Constant.EXTRA_ARTIST);

        if(artist!=null){
            adapter=new BaseAdapter<>(this,R.layout.item_songartist);
            SystemData data=new SystemData(this);
            data.getDataSong();
            adapter.setData(data.getArrSongArtist(artist.getIdArtist()));
            adapter.setListener(this);
            binding.lvSongartist.setAdapter(adapter);

            Glide.with(binding.imgArtist).load(artist.getImage()).into(binding.imgArtist);

            binding.tvNumberSong.setText(artist.getNumberSong()+" Bài Hát");
            binding.tvArtist.setText("Nhưng bài hát của "+artist.getName());

        }else{
            Album album= (Album) intent.getSerializableExtra(Constant.EXTRA_ALBUM);
            SystemData data=new SystemData(this);
            data.getDataSong().size();
            adapter=new BaseAdapter<>(this,R.layout.item_songartist);

            adapter.setData(data.getArrSongAlbum(album.getKeyAlbum()));
            adapter.setListener(this);
            binding.lvSongartist.setAdapter(adapter);

            Glide.with(binding.imgArtist).load(album.getImage()).into(binding.imgArtist);
            binding.tvArtist.setText("Những bài hát của "+album.getArtist());
            binding.tvNumberSong.setText(album.getNumberSong()+" Bài Hát");


        }
    }

    @Override
    public void SongClicked(Song song) {
        Intent intent=new Intent(SongArtistActivity.this,SongPlayActivity.class);
        intent.putExtra(Constant.EXTRA_SONG,song);
        startActivity(intent);
    }
}
