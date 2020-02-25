package com.ducnv1106.message.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ducnv1106.message.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RequestApi {

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    public RequestApi() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public MutableLiveData<User> readUser(){

        final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userMutableLiveData.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return userMutableLiveData;
    }
}
