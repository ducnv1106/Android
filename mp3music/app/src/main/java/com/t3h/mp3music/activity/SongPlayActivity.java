package com.t3h.mp3music.activity;

import android.content.Intent;

import com.t3h.mp3music.BR;
import com.t3h.mp3music.Constant;
import com.t3h.mp3music.R;
import com.t3h.mp3music.databinding.ActivitySongplayBinding;
import com.t3h.mp3music.model.Song;

public class SongPlayActivity extends BaseActivity<ActivitySongplayBinding> {


    @Override
    protected void initView() {
        Intent intent=getIntent();

        Song song= (Song) intent.getSerializableExtra(Constant.EXTRA_SONG);

        binding.setVariable(BR.item,song);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_songplay;
    }
}
