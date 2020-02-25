package com.ducnv1106.message.view.fragment.friend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ducnv1106.message.R;
import com.ducnv1106.message.adapter.BaseAdapter;
import com.ducnv1106.message.databinding.FragmentFriendBinding;
import com.ducnv1106.message.model.Friend;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.fragment.BaseFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class FragmentFriend extends BaseFragment<FragmentFriendBinding> {

    private BaseAdapter<User> adapter;
    private HashMap<User, Boolean> hashMap = new HashMap<>();

    private DatabaseReference databaseReference;


    @Override
    protected void initView() {

        adapter = new BaseAdapter<>(getContext(), R.layout.item_user, false, true,false);
        adapter.setHashMap(hashMap);
        binding.lvUserFriend.setAdapter(adapter);

        readUserFriend();

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_friend;
    }

    @Override
    protected String getTitle() {
        return "Friend";
    }

    public void readUserFriend() {

        final HashMap<String, Boolean> arr = new HashMap<>();
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Friend");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hashMap.clear();

                arr.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (checkFriendId(snapshot.getKey())) {

                        Friend friend = snapshot.getValue(Friend.class);

                        if (friend.isFriend()) {
                            if (friend.getSender().equals(id)) {
//                                arr.add(friend.getReceiver());
                                arr.put(friend.getReceiver(), true);
                            }
                            if (friend.getReceiver().equals(id)) {
                                arr.put(friend.getSender(), true);
                            }
                        } else {
                            if (friend.getReceiver().equals(id)) {
                                arr.put(friend.getSender(), false);
                            }
                        }
                    }

                }
                databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Set<String> set = arr.keySet();

                            if (checkUserId(snapshot.getKey(), set)) {
                                User user = snapshot.getValue(User.class);
                                hashMap.put(user, arr.get(snapshot.getKey()));
                            }
                        }
                        adapter.setHashMap(hashMap);
                        Log.e("FragmentFriend",hashMap.size()+"");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean checkFriendId(String friendId) {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (friendId.contains(id)) {
            return true;
        }
        return false;

    }

    public boolean checkUserId(String id, Set<String> arr) {
        for (String str : arr) {
            if (id.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
