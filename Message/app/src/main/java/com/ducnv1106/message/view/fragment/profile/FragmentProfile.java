package com.ducnv1106.message.view.fragment.profile;

import com.ducnv1106.message.R;
import com.ducnv1106.message.databinding.FragmentProfileBinding;
import com.ducnv1106.message.view.fragment.BaseFragment;

public class FragmentProfile extends BaseFragment<FragmentProfileBinding> {
    @Override
    protected void initView() {

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected String getTitle() {
        return "Profile";
    }
}
