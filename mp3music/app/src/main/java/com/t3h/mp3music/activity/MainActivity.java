package com.t3h.mp3music.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.t3h.mp3music.Constant;
import com.t3h.mp3music.R;
import com.t3h.mp3music.adapter.Mp3PagerAdapter;
import com.t3h.mp3music.databinding.ActivityMainBinding;
import com.t3h.mp3music.fragment.FragmentStart;
import com.t3h.mp3music.fragment.album.FragmentAlbum;
import com.t3h.mp3music.fragment.artist.FragmentArtist;
import com.t3h.mp3music.fragment.song.FragmentSong;
import com.t3h.mp3music.systemdata.SystemData;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static final int MESSAGE_TIMEDELAY=1;
    private Handler mHandler;
    private FragmentStart fragmentStart=new FragmentStart();
    private FragmentSong fmSong=new FragmentSong();
    private FragmentAlbum fmAlbum=new FragmentAlbum();
    private FragmentArtist fmArtist=new FragmentArtist();

    private Mp3PagerAdapter mp3PagerAdapter;

    private MenuItem myMenuItem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHandler();
        timeDelay();

    }

    private void initHandler() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==MESSAGE_TIMEDELAY){
                        if(msg.arg1==2){
                            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                            transaction.hide(fragmentStart);
                            transaction.commit();

                            initFragment();
                        }

                }
            }
        };
    }

    private void initFragment() {

        binding.bottomNavi.setVisibility(View.VISIBLE);
        binding.bottomNavi.setOnNavigationItemSelectedListener(this);
        mp3PagerAdapter=new Mp3PagerAdapter(getSupportFragmentManager(),fmSong,fmAlbum,fmArtist);
        binding.pager.setAdapter(mp3PagerAdapter);

        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(myMenuItem!=null){
                    myMenuItem.setChecked(false);
                }else {
                    binding.bottomNavi.getMenu().getItem(0).setChecked(true);
                }
                binding.bottomNavi.getMenu().getItem(position).setChecked(true);
                myMenuItem=binding.bottomNavi.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    private void timeDelay() {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<3;i++){

                    Message message=new Message();
                    message.what=MESSAGE_TIMEDELAY;
                    message.arg1=i;
                    mHandler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }




    @Override
    protected void initView() {

        if (checkPermisson()==false){
            return;
        }
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.panel,fragmentStart);
        transaction.show(fragmentStart);
        transaction.commit();

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.icon_song:{
                binding.pager.setCurrentItem(0);
                break;
            }
            case R.id.icon_album:{
                binding.pager.setCurrentItem(1);
                break;
            }
            case R.id.icon_artist:{
                binding.pager.setCurrentItem(2);
                break;
            }
        }
        return false;
    }
    public boolean checkPermisson() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            return  false;
        } else {
            return  true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (checkPermisson()) {
            initView();
        }else {
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}
