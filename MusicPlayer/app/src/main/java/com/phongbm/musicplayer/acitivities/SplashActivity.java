package com.phongbm.musicplayer.acitivities;

import android.content.Intent;
import android.os.Handler;

import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.base.BaseActivity;
import com.phongbm.musicplayer.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    @Override
    protected void initAct() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
