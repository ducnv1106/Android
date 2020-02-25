package com.ducnv1106.message.view.acitivity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ducnv1106.message.Constants;
import com.ducnv1106.message.R;
import com.ducnv1106.message.databinding.ActivitySplashBinding;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.MainActivity;
import com.ducnv1106.message.viewmodel.MyViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private MyViewModel myViewModel;


    @Override
    protected int sytleId() {
        return R.style.Theme_Spalsh;
    }

    @Override
    protected void initView() {

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                }
            }.start();
            return;
        }
        loadDataUser();


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_splash;
    }

    public void loadDataUser() {

        final Observer<User> userObserver = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra(Constants.EXTRA_USER, user);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                myViewModel.getUser().removeObserver(this);
                Log.e("Splash", "hello");
            }
        };

        myViewModel.initUser();
        myViewModel.getUser().observe(this,userObserver);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Splash", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Splash", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Splash", "onDestroy");
    }
}
