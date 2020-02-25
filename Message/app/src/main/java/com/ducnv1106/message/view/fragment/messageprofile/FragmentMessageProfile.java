package com.ducnv1106.message.view.fragment.messageprofile;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;


import com.ducnv1106.message.R;
import com.ducnv1106.message.adapter.ImageAdapter;
import com.ducnv1106.message.databinding.FragmentMessageProfileBinding;
import com.ducnv1106.message.model.Friend;
import com.ducnv1106.message.model.Message;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.acitivity.messageprofile.MessageProfileActivity;
import com.ducnv1106.message.view.fragment.BaseFragment;
import com.ducnv1106.message.viewmodel.MyViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentMessageProfile extends BaseFragment<FragmentMessageProfileBinding> implements MessageProfileListener {

    private User user;
    private MyViewModel myViewModel;

    private DatabaseReference databaseReference;

    private ImageAdapter adapter;
    private ArrayList<String> arrImage = new ArrayList<>();


    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void initView() {


        binding.setUser(user);

        binding.setListener(this);
        readFriend();

        getImageMessage();
        adapter = new ImageAdapter(getContext());
        binding.lvImage.setAdapter(adapter);
        adapter.setArrImage(arrImage);
        adapter.setListener(this);

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        databaseReference = FirebaseDatabase.getInstance().getReference("Friend").child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "," + user.getUserId());

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_message_profile;
    }

    @Override
    protected String getTitle() {
        return "hhh";
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                 MessageProfileActivity messageProfileActivity = (MessageProfileActivity) getActivity();
                messageProfileActivity.finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddFriendClicked() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Friend").child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "," + user.getUserId());

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("friend", false);
        hashMap.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("receiver", user.getUserId());

        databaseReference.setValue(hashMap);

        binding.btnAddFriend.setVisibility(View.GONE);
        binding.btnCancelFriend.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCancelFriendClicked() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Friend").child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "," + user.getUserId());
        databaseReference.removeValue();
        binding.btnAddFriend.setVisibility(View.VISIBLE);
        binding.btnCancelFriend.setVisibility(View.GONE);
        binding.btnAcceptFriend.setVisibility(View.GONE);

    }

    @Override
    public void onAcceptFriendClicked() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Friend").child(user.getUserId() + "," + FirebaseAuth.getInstance().getCurrentUser().getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("friend", true);

        databaseReference.updateChildren(hashMap);

    }

    @Override
    public void onProfileClicked() {

    }

    @Override
    public void onNickNamesClicked() {


    }

    @Override
    public void onCreatGroupClicked() {

    }

    @Override
    public void onImageClicked(String url) {
        MessageProfileActivity messageProfileActivity = (MessageProfileActivity) getActivity();
        messageProfileActivity.getFragmentImage().setUrlImage(url);
        messageProfileActivity.showFragment(messageProfileActivity.getFragmentImage());
        Log.e("FragmentMessageProfile",url);

    }

    private void readFriend() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Friend");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                binding.btnAddFriend.setVisibility(View.VISIBLE);
                binding.btnCancelFriend.setVisibility(View.GONE);
                binding.btnAcceptFriend.setVisibility(View.GONE);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Friend friend = snapshot.getValue(Friend.class);


                    String friendId = friend.getSender() + "," + friend.getReceiver();

                    Log.e("MessageProfile", friendId);


                    if (checkSendAddFriend(friendId)) {

                        if (friendId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid() + "," + user.getUserId())) {

                            if (!friend.isFriend()) {
                                binding.btnCancelFriend.setVisibility(View.VISIBLE);
                                binding.btnAddFriend.setVisibility(View.GONE);
                                binding.btnAcceptFriend.setVisibility(View.GONE);
                                binding.btnMessage.setVisibility(View.GONE);
                            } else {
                                binding.btnAddFriend.setVisibility(View.GONE);
                                binding.btnCancelFriend.setVisibility(View.GONE);
                                binding.btnAcceptFriend.setVisibility(View.GONE);
                                binding.btnMessage.setVisibility(View.VISIBLE);
                            }

                        } else {

                            if (!friend.isFriend()) {
                                binding.btnAcceptFriend.setVisibility(View.VISIBLE);
                                binding.btnAddFriend.setVisibility(View.GONE);
                                binding.btnCancelFriend.setVisibility(View.GONE);
                                binding.btnMessage.setVisibility(View.GONE);
                            } else {
                                binding.btnAddFriend.setVisibility(View.GONE);
                                binding.btnCancelFriend.setVisibility(View.GONE);
                                binding.btnAcceptFriend.setVisibility(View.GONE);
                                binding.btnMessage.setVisibility(View.VISIBLE);
                            }
                        }

                    } else {
//
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public boolean checkSendAddFriend(String friendId) {

        if (friendId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid() + "," + user.getUserId()) ||
                friendId.equals(user.getUserId() + "," + FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return true;
        }

        return false;
    }

    public void getImageMessage() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Message");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrImage.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);

                    if (!message.getType().equals("text")) {
                        if ((message.getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && message.getReceiver().equals(user.getUserId())) ||
                                (message.getSender().equals(user.getUserId()) && message.getReceiver().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {
                            arrImage.add(message.getMessage());
                        }
                    }

                }

                Log.e("MessageProfileActivity", arrImage.size() + "");
                adapter.setArrImage(arrImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
