package com.ducnv1106.message.view.fragment.messageprofile;

import com.ducnv1106.message.adapter.ImageAdapter;

public interface MessageProfileListener extends ImageAdapter.ImageListener {

    void onAddFriendClicked();

    void onCancelFriendClicked();

    void onAcceptFriendClicked();

    void onProfileClicked();

    void onNickNamesClicked();

    void onCreatGroupClicked();

    void onImageClicked(String imgUrl);


}
