package com.t3h.mp3music.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

import com.t3h.mp3music.BR;
import com.t3h.mp3music.Constant;
import com.t3h.mp3music.R;
import com.t3h.mp3music.databinding.ActivitySongplayBinding;
import com.t3h.mp3music.model.Mp3Media;
import com.t3h.mp3music.model.Song;

import java.io.IOException;

public class SongPlayActivity extends BaseActivity<ActivitySongplayBinding> {

    private Mp3Media mp3Media;

    @Override
    protected void initView() {
        Intent intent=getIntent();

        Song song= (Song) intent.getSerializableExtra(Constant.EXTRA_SONG);

        binding.setVariable(BR.item,song);

        mp3Media=new Mp3Media(this,song);

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_songplay;
    }
}
