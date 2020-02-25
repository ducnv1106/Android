package com.ducnv1106.message.view.acitivity.message;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.ducnv1106.message.Constants;
import com.ducnv1106.message.R;
import com.ducnv1106.message.adapter.MessageApdater;
import com.ducnv1106.message.databinding.ActivityMessageBinding;
import com.ducnv1106.message.model.Message;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.notification.ApiBuilder;
import com.ducnv1106.message.notification.Data;
import com.ducnv1106.message.notification.MyResponse;
import com.ducnv1106.message.notification.Sender;
import com.ducnv1106.message.notification.Token;
import com.ducnv1106.message.view.acitivity.BaseActivity;
import com.ducnv1106.message.view.acitivity.messageprofile.MessageProfileActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends BaseActivity<ActivityMessageBinding> implements MessageListener, MessageApdater.BaseMessageListener {
    private static int PICK_IMAGE = 1;
    private Uri uri;

    private ArrayList<Message> data = new ArrayList<>();
    private MessageApdater apdater;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private User user;

    private ValueEventListener listener;

    private boolean notify=false;


    @Override
    protected int sytleId() {
        return R.style.Base_Theme_DesignDemo;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(Constants.EXTRA_USER);
        binding.setUser(user);

        apdater = new MessageApdater(this, user);
        apdater.setData(data);
        apdater.setListener(this);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar = binding.toobar;
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        binding.setListener(this);
        binding.lvMessage.setAdapter(apdater);

        readMessage();



        binding.edtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.lvMessage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.lvMessage.scrollToPosition(binding.lvMessage.getAdapter().getItemCount() - 1);
                    }
                }, 200);
                Log.e("Mess", "Click");
            }
        });

        Log.e("MessActivity",user.getUserId());
        seenMessage(user.getUserId());
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void onSendClicked() {
        String message = binding.edtMessage.getText().toString();

        if (message.isEmpty()) {

            Toast.makeText(this, "Message do not empty", Toast.LENGTH_SHORT).show();
        } else {
            notify=true;
            sendMessage(message);
            binding.edtMessage.setText("");
        }
    }

    @Override
    public void onProfileClicked() {
        Intent intent = new Intent(this, MessageProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.EXTRA_USER, user);
        startActivity(intent);
    }

    @Override
    public void onSendImgClicked() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE && data!=null){
            uri=data.getData();
            sendImage();
        }
    }

    public void sendImage(){
        if(uri!=null){
            binding.proressBar.setVisibility(View.VISIBLE);
            binding.layout.setAlpha((float) 0.7);
            final StorageReference storageReference=FirebaseStorage.getInstance().getReference("Uploads").child(System.currentTimeMillis()+"."+getReadPathFromUri(uri));
            final StorageTask storageTask = storageReference.putFile(uri);
            storageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task task) throws Exception {
                   if(!task.isSuccessful()){
                       throw  task.getException();
                   }

                   return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){

                        String url=task.getResult().toString();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Message");

                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("sender",firebaseUser.getUid());
                        hashMap.put("receiver",user.getUserId());
                        hashMap.put("type","image");
                        hashMap.put("message",url);
                        hashMap.put("isseen",false);

                        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                    }
                }
            });
        }
    }

    // read type file
    public String getReadPathFromUri(Uri uri) {

        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void sendMessage(String message) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Message");

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("message", message);
        hashMap.put("sender", firebaseUser.getUid());
        hashMap.put("receiver", user.getUserId());
        hashMap.put("type","text");
        hashMap.put("isseen", false);

        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                if(notify){
                    sendNotification(user.getUserId(),user1.getUsername(),message);
                }

                notify=false;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void sendNotification(String receiver,String username,String message){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Token");

        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);

                    Data data = new Data("New Message",user.getAvatar(),username+": "+message,firebaseUser.getUid(),user.getUserId());

                    Sender sender = new Sender(data,token.getToken());

                    ApiBuilder.getApi().senNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                            if(response.code()==200){
                                if(response.body().success!=1){
                                    Toast.makeText(MessageActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readMessage() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Message");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if ((message.getSender().equals(firebaseUser.getUid()) && message.getReceiver().equals(user.getUserId())) ||
                            (message.getSender().equals(user.getUserId()) && message.getReceiver().equals(firebaseUser.getUid()))) {
                        data.add(message);


                    }

                }

                apdater.setData(data);
                binding.lvMessage.smoothScrollToPosition(binding.lvMessage.getAdapter().getItemCount());

                data.size();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Message", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseReference.removeEventListener(listener);
    }

    public void seenMessage(final String userId) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Message");

        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);

                    if (message.getReceiver().equals(firebaseUser.getUid()) && message.getSender().equals(userId)) {

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);

                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClicked(float alpha) {
        binding.layout.setAlpha(alpha);
        binding.proressBar.setVisibility(View.GONE);

    }
}
