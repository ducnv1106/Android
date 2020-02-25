package com.ducnv1106.message.view.acitivity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.ducnv1106.message.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public abstract class BaseActivity<BD extends ViewDataBinding> extends AppCompatActivity {

    protected BD binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (sytleId() != 0) {
            setTheme(sytleId());
        }
        binding = DataBindingUtil.setContentView(this, layoutId());
        super.onCreate(savedInstanceState);

        initView();


    }

    protected abstract int sytleId();

    protected abstract void initView();

    protected abstract int layoutId();

    public BD getBinding() {
        return binding;
    }

    public void setBinding(BD binding) {
        this.binding = binding;
    }
}
