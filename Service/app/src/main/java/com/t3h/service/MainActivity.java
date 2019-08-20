package com.t3h.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.t3h.service.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MainActivityListener {
    private ActivityMainBinding binding;
    private MyService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setListener(this);
    }

    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder binder= (MyService.MyBinder) service;

            MainActivity.this.service=binder.getService();
            binding.tv.setText("Service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onBindClicked() {

        Intent intent=new Intent(this,MyService.class);
//        bindService(intent,connection,BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    public void onUnBindClicked() {
//        unbindService(connection);
        Intent intent=new Intent(this,MyService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
