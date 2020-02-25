package com.ducnv1106.message.view.fragment.user;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ducnv1106.message.Constants;
import com.ducnv1106.message.R;
import com.ducnv1106.message.adapter.BaseAdapter;
import com.ducnv1106.message.databinding.FragmentUserBinding;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.MainActivity;
import com.ducnv1106.message.view.acitivity.message.MessageActivity;
import com.ducnv1106.message.view.fragment.BaseFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentUser extends BaseFragment<FragmentUserBinding> implements UserListener {

    private BaseAdapter<User> adapter;
    private FirebaseUser firebaseUser;
    private ArrayList<User> data = new ArrayList<>();

    private DatabaseReference databaseReference;

    @Override
    protected void initView() {

        adapter = new BaseAdapter<>(getContext(), R.layout.item_user, false,false,false);
        adapter.setData(data);
        binding.lvUser.setAdapter(adapter);
        adapter.setListener(this);
        readUser();

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected String getTitle() {
        return "User";
    }

    public void readUser() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    assert firebaseUser != null;

                    if (!user.getUserId().equals(firebaseUser.getUid())) {
                        data.add(user);
                    }
                }
                adapter.setData(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("FragmentUser", "onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("FragmentUser", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("FragmentUser", "onStop");
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getContext(), MessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.EXTRA_USER, user);
        startActivity(intent);
    }
}
