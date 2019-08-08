package com.t3h.asynctaskdemo;

import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.t3h.asynctaskdemo.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskTime.TimeCallBack {
    private ActivityMainBinding binding;
    private SimpleDateFormat date=new SimpleDateFormat("HH:mm:ss");
    private String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        init();
    }

    private void init() {
        time=date.format(new Date().getTime());
        binding.tvTime.setText(time);
        binding.btnStart.setOnClickListener(this);
        binding.btnStop.setOnClickListener(this);
        AsyncTaskTime async= new AsyncTaskTime(this);
        async.execute(time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:{

            }
            case R.id.btn_stop:{

            }
        }

    }


    @Override
    public void ontTimeUpdate(String time) {
      binding.tvTime.setText(time);
    }

    @Override
    public void onTimeSuccess(String time) {

    }

}
