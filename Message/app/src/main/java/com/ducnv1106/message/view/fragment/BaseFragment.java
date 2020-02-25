package com.ducnv1106.message.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<BD extends ViewDataBinding> extends Fragment {

    protected BD binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,layoutId(),container,false);
        initView();
        return binding.getRoot();
    }

    protected abstract void initView();

    protected abstract int layoutId();

    protected abstract String getTitle();

    public BD getBinding() {
        return binding;
    }

    public void setBinding(BD binding) {
        this.binding = binding;
    }
}
