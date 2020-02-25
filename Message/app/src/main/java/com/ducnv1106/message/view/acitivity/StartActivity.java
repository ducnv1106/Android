package com.ducnv1106.message.view.acitivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ducnv1106.message.R;
import com.ducnv1106.message.databinding.ActivityStartBinding;
import com.ducnv1106.message.view.MainActivity;
import com.ducnv1106.message.view.fragment.PagerAdapter;
import com.ducnv1106.message.view.fragment.signin.SignInFragment;
import com.ducnv1106.message.view.fragment.signup.SignUpFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends BaseActivity<ActivityStartBinding> {

    private SignInFragment signInFragment = new SignInFragment();
    private SignUpFragment signUpFragment =new SignUpFragment();
    private PagerAdapter adapter;

    private FirebaseUser firebaseUser;
    @Override
    protected void initView() {
            adapter =new PagerAdapter(getSupportFragmentManager(),signInFragment,signUpFragment);
            binding.viewPager.setAdapter(adapter);
            binding.tabLayout.setupWithViewPager(binding.viewPager);

            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if(firebaseUser!=null){
                Intent intent =new Intent(StartActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
    }

    @Override
    protected int sytleId() {
        return R.style.Theme_Start;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
