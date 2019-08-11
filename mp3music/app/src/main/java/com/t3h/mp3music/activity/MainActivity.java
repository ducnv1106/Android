package com.t3h.mp3music.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;

import com.t3h.mp3music.R;
import com.t3h.mp3music.adapter.Mp3PagerAdapter;
import com.t3h.mp3music.databinding.ActivityMainBinding;
import com.t3h.mp3music.fragment.album.FragmentAlbum;
import com.t3h.mp3music.fragment.song.FragmentSong;
import com.t3h.mp3music.systemdata.SystemData;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private FragmentSong fmSong=new FragmentSong();
    private FragmentAlbum fmAlbum=new FragmentAlbum();

    private Mp3PagerAdapter mp3PagerAdapter;

    private final String[] PEMISSONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private boolean checkPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PEMISSONS) {
                int check = checkSelfPermission(p);
                if (check != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermisson()) {
            initView();
        } else {
            finish();
        }
    }

    @Override
    protected void initView() {
        if (checkPermisson()==false){
            return;
        }
        mp3PagerAdapter=new Mp3PagerAdapter(getSupportFragmentManager(),fmSong,fmAlbum);
        binding.pager.setAdapter(mp3PagerAdapter);

        SystemData data=new SystemData(this);
        data.getAlbum();

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }
}
