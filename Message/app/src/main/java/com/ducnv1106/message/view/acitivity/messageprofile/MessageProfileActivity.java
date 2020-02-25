package com.ducnv1106.message.view.acitivity.messageprofile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ducnv1106.message.Constants;
import com.ducnv1106.message.R;
import com.ducnv1106.message.databinding.ActivityMessageprofileBinding;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.acitivity.BaseActivity;
import com.ducnv1106.message.view.fragment.image.FragmentImage;
import com.ducnv1106.message.view.fragment.messageprofile.FragmentMessageProfile;

public class MessageProfileActivity extends BaseActivity<ActivityMessageprofileBinding> {
    private User user;
    private FragmentMessageProfile fragmentMessageProfile=new FragmentMessageProfile();
    private FragmentImage fragmentImage = new FragmentImage();

    private final String[] PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected int sytleId() {
        return R.style.Base_Theme_DesignDemo;
    }

    @Override
    protected void initView() {

        if (checkPermission()==true){
            init();
        }else {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(PERMISSION,0);
            }
        }

    }

    public void init(){

        Intent intent = getIntent();
        user= (User) intent.getSerializableExtra(Constants.EXTRA_USER);

        fragmentMessageProfile.setUser(user);

//        initFragment();

        showFragment(fragmentMessageProfile);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_messageprofile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void showFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.layout_panel,fragment);
        transaction.addToBackStack("MessageProfile");

        transaction.commit();
    }



    public FragmentImage getFragmentImage() {
        return fragmentImage;
    }

    public void setFragmentImage(FragmentImage fragmentImage) {
        this.fragmentImage = fragmentImage;
    }

    @Override
    public void onBackPressed() {
        int countBackStack = getSupportFragmentManager().getBackStackEntryCount();
        if(countBackStack>1){
            getSupportFragmentManager().popBackStack();
        }else {
            finish();
        }
    }

    private boolean checkPermission() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            for (String p: PERMISSION){
                int check=checkSelfPermission(p);
                if(check!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(checkPermission()){
            init();
        }else {
            finish();
        }
    }
}
