package com.t3h.miniproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.t3h.miniproject.R;

public class SavedFragment extends BaseFragment {

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_saved;
    }

    @Override
    public String getTitle() {
        return "Saved";
    }


}
