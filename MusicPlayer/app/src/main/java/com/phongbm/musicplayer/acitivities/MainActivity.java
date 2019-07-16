package com.phongbm.musicplayer.acitivities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.phongbm.musicplayer.App;
import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.adapter.PagerAdapter;
import com.phongbm.musicplayer.base.BaseActivity;
import com.phongbm.musicplayer.databinding.ActivityMainBinding;
import com.phongbm.musicplayer.fragments.AlbumFragment;
import com.phongbm.musicplayer.fragments.ArtistFragment;
import com.phongbm.musicplayer.fragments.MusicFragment;
import com.phongbm.musicplayer.fragments.OnlineFragment;
import com.phongbm.musicplayer.service.MP3Service;

public class MainActivity extends BaseActivity<ActivityMainBinding>
        implements NavigationView.OnNavigationItemSelectedListener {

    private MusicFragment fmMusic = new MusicFragment();
    private AlbumFragment fmAlbum = new AlbumFragment();
    private ArtistFragment fmArtist = new ArtistFragment();
    private OnlineFragment fmOnline =  new OnlineFragment();

    private PagerAdapter adapter;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MP3Service.MP3Binder binder = (MP3Service.MP3Binder) service;
            App app = (App) getApplicationContext();
            app.setService(binder.getService());
            binding.playView.registerState();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PERMISSIONS) {
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(PERMISSIONS, 0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermission()) {
            initAct();
        } else {
            finish();
        }
    }

    @Override
    protected void initAct() {
        if (checkPermission() == false) {
            return;
        }

        Intent intent = new Intent(this, MP3Service.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        App app = (App) getApplicationContext();
        if (app.getUser() == null){
            adapter = new PagerAdapter(this,
                    getSupportFragmentManager(),
                    fmMusic, fmAlbum, fmArtist);
        }else{
            adapter = new PagerAdapter(this,
                    getSupportFragmentManager(),
                    fmMusic, fmAlbum, fmArtist, fmOnline);
        }
        binding.pager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.pager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_music:
                binding.pager.setCurrentItem(0);
                break;
            case R.id.nav_album:
                binding.pager.setCurrentItem(1);
                break;
            case R.id.nav_artist:
                binding.pager.setCurrentItem(2);
                break;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
