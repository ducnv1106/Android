package com.t3h.buoi12.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<AC extends AppCompatActivity> extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(getLayout(),container,false);
        return v;
    }

    protected abstract int getLayout();
    protected <V extends  View> V findViewByID(@IdRes int resId){
        return getActivity().findViewById(resId);
    }
    public AC getParentActivity(){
        return (AC) getActivity();
    }
}
