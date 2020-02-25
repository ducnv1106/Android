package com.ducnv1106.message.view.fragment.user;

import com.ducnv1106.message.adapter.BaseAdapter;
import com.ducnv1106.message.model.User;

public interface UserListener extends BaseAdapter.BaseListener {

    void onUserClicked(User user);




}
