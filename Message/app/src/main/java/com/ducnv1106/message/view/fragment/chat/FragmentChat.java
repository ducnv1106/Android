package com.ducnv1106.message.view.fragment.chat;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ducnv1106.message.Constants;
import com.ducnv1106.message.R;
import com.ducnv1106.message.adapter.BaseAdapter;
import com.ducnv1106.message.databinding.FragmentChatBinding;
import com.ducnv1106.message.model.Message;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.notification.Token;
import com.ducnv1106.message.view.acitivity.message.MessageActivity;
import com.ducnv1106.message.view.fragment.BaseFragment;
import com.ducnv1106.message.view.fragment.user.UserListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashSet;

public class FragmentChat extends BaseFragment<FragmentChatBinding> implements UserListener {

    private BaseAdapter<User> adapter;
    private ArrayList<User> data = new ArrayList<>();
    private ArrayList<String> dataIdChat = new ArrayList<>();
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private HashSet<Integer> hashSet = new HashSet<>();


    @Override
    protected void initView() {

        readIdChat();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        adapter = new BaseAdapter<>(getContext(), R.layout.item_user, true, false, true);
        adapter.setData(data);
        binding.lvUserchat.setAdapter(adapter);
        adapter.setListener(this);

        updateToken(FirebaseInstanceId.getInstance().getToken());


    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected String getTitle() {
        return "Chat";
    }

    public void readIdChat() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Message");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataIdChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Message message = snapshot.getValue(Message.class);

                    if (message.getSender().equals(firebaseUser.getUid())) {
                        dataIdChat.add(message.getReceiver());
                    }
                    if (message.getReceiver().equals(firebaseUser.getUid())) {
                        dataIdChat.add(message.getSender());
                    }

                }

                readUserChat();

                Log.e("FragmentChat", dataIdChat.size() + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readUserChat() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User user = snapshot.getValue(User.class);
                    for (String id : dataIdChat) {
                        if (user.getUserId().equals(id)) {

                            if (data.size() != 0) {
                                if (checkIdChat(user.getUserId())) {
                                    data.add(user);
                                }

                            } else {
                                data.add(user);
                            }
                        }
                    }

                }
                adapter.setData(data);
                indexScroll();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public boolean checkIdChat(String id) {

        for (User user : data) {
            if (id.equals(user.getUserId())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getContext(), MessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.EXTRA_USER, user);
        startActivity(intent);

    }

    public ArrayList<User> getData() {
        return data;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }

    public void indexScroll() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Message");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hashSet.clear();
                for (int i = 0; i < data.size(); i++) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Message message = snapshot.getValue(Message.class);
                        if (message.getSender().equals(data.get(i).getUserId()) && message.getReceiver().equals(firebaseUser.getUid())) {
                            if (!message.getIsseen()) {
                                hashSet.add(i);
                            }
                        }
                    }
                }

                Log.e("FragmentChat", hashSet.size() + "a");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateToken(String updateToken) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Token").child(firebaseUser.getUid());
        Token token = new Token(updateToken);
        databaseReference.setValue(token);
    }

    public HashSet<Integer> getHashSet() {
        return hashSet;
    }

    public void setHashSet(HashSet<Integer> hashSet) {
        this.hashSet = hashSet;
    }
}
