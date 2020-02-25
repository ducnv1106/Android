package com.ducnv1106.message.view.acitivity.profile;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ducnv1106.message.Constants;
import com.ducnv1106.message.R;
import com.ducnv1106.message.adapter.ContactAdapter;
import com.ducnv1106.message.databinding.ActivityProfileBinding;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.acitivity.BaseActivity;
import com.ducnv1106.message.view.acitivity.editcontact.EditContactActivity;
import com.ducnv1106.message.view.acitivity.editinfo.EditProfileActivity;
import com.ducnv1106.message.viewmodel.MyViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding> implements ProfileListener {

    private final static int PICK_AVATAR = 1;
    private final static int PICK_COVER_IMAGE=2;

    private Uri uri;
    private MyViewModel myViewModel;
    private User user;

    private StorageReference storageReference;
    private StorageTask storageTask;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    private ContactAdapter<String> adapterPhone;
    private ArrayList<String> dataPhone = new ArrayList<>();

    private ContactAdapter<String> adapterWebstie;
    private ArrayList<String> dataWebsite = new ArrayList<>();

    private ContactAdapter<String> adapterEmail;
    private ArrayList<String> dataEmail = new ArrayList<>();


    @Override
    protected int sytleId() {
        return 0;
    }

    @Override
    protected void initView() {

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        user = myViewModel.getUser().getValue();

        readDataUser(user, dataPhone, dataWebsite, dataEmail);

        adapterPhone = new ContactAdapter<>(this, R.layout.item_profile);
        adapterPhone.setData(dataPhone);
        binding.lvPhone.setAdapter(adapterPhone);

        adapterWebstie = new ContactAdapter<>(this, R.layout.item_profile);
        adapterWebstie.setData(dataWebsite);
        binding.lvWebsite.setAdapter(adapterWebstie);

        adapterEmail = new ContactAdapter<>(this, R.layout.item_profile);
        adapterEmail.setData(dataEmail);
        binding.lvEmail.setAdapter(adapterEmail);

        // path file store
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        binding.collapsingToolbar.setTitle(user.getUsername());
        binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.setListener(this);
        binding.setUser(user);


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public String getReadPathFromUri(Uri uri) {

        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_AVATAR && data != null) {
            uri = data.getData();
            uploadImage("avatar",requestCode);
        }
        if(resultCode==RESULT_OK && requestCode == PICK_COVER_IMAGE && data!=null){
            uri=data.getData();
            uploadImage("coverimage",requestCode);
        }
    }

    public void uploadImage(final String str, final int requestCode) {

        if (uri != null) {
            binding.coordinatorLayout.setAlpha((float) 0.4);
            binding.proressBar.setVisibility(View.VISIBLE);
            final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getReadPathFromUri(uri));
            storageTask = reference.putFile(uri);
            storageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if (!task.isSuccessful()) {
                        binding.proressBar.setVisibility(View.GONE);
                        binding.coordinatorLayout.setAlpha(1);
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }

            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {

                        String url = task.getResult().toString();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put(str, url);

                        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    binding.proressBar.setVisibility(View.GONE);
                                        if(requestCode==PICK_AVATAR){
                                            binding.imgAvatar.setImageURI(uri);
                                        }else {
                                            binding.coverImage.setImageURI(uri);
                                        }
                                    binding.coordinatorLayout.setAlpha(1);
                                    ;
                                }
                            }
                        });

                    }
                }
            });

        }
    }


    @Override
    public void onAvatarClicked() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, PICK_AVATAR);

    }

    @Override
    public void onEditInfoCliked() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra(Constants.EXTRA_USER, user);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onEditContactCliked() {
        Intent intent = new Intent(this, EditContactActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.EXTRA_USER, user);
        startActivity(intent);

    }

    @Override
    public void onPhotoCameraClicked() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, PICK_COVER_IMAGE);



    }

    public void realoadData() {
        myViewModel.initUser();
        myViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.setUser(user);
                dataPhone.clear();
                dataWebsite.clear();
                readDataUser(user, dataPhone, dataWebsite, dataEmail);
                adapterPhone.setData(dataPhone);
                adapterWebstie.setData(dataWebsite);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Profile", "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Profile", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Profile", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        realoadData();
    }

    public void readDataUser(User user, ArrayList<String> dataPhone, ArrayList<String> dataWebsite, ArrayList<String> dataEmail) {

        if (user.getPhone() != null) {
            for (String phone : user.getPhone()) {
                dataPhone.add(phone);
            }

        }
        if (user.getDomin() != null) {
            for (String website : user.getDomin()) {
                dataWebsite.add(website);
            }
        }
        if (user.getEmail() != null) {
            for (String email : user.getEmail()) {
                dataEmail.add(email);
            }
        }
    }
}
