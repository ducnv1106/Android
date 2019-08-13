package com.t3h.mp3music.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.t3h.mp3music.R;
import com.t3h.mp3music.databinding.FragmentStartBinding;

public class FragmentStart extends BaseFragment<FragmentStartBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.progress.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public String getTtile() {
        return null;
    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_start;
    }
}
