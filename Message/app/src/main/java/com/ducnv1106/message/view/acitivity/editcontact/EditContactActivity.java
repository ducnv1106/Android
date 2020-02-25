package com.ducnv1106.message.view.acitivity.editcontact;

import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.ducnv1106.message.R;
import com.ducnv1106.message.adapter.ContactAdapter;
import com.ducnv1106.message.databinding.ActivityEditcontactBinding;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.acitivity.BaseActivity;
import com.ducnv1106.message.viewmodel.MyViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class EditContactActivity extends BaseActivity<ActivityEditcontactBinding> implements EditContactListener {

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    private User user;
    private HashMap<String, Object> hashMap = new HashMap<>();

    private ContactAdapter<String> adapterPhone;
    private ArrayList<String> dataPhone = new ArrayList<>();

    private ContactAdapter<String> adapterWebsite;
    private ArrayList<String> dataWebsite = new ArrayList<>();

    private ContactAdapter<String> adapterEmail;
    private ArrayList<String> dataEmail = new ArrayList<>();

    private MyViewModel myViewModel;

    @Override
    protected int sytleId() {
        return R.style.Theme_Infomation;
    }

    @Override
    protected void initView() {

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        user = myViewModel.getUser().getValue();

        // read Data Firabase
        readDataUser(user, dataPhone, dataWebsite, dataEmail);

        // Save data HashMap
        hashMap.put("phone", dataPhone);
        hashMap.put("domin", dataWebsite);
        hashMap.put("email", dataEmail);

        adapterPhone = new ContactAdapter<>(this, R.layout.item_contact);
        adapterWebsite = new ContactAdapter<>(this, R.layout.item_contact);
        adapterEmail = new ContactAdapter<>(this, R.layout.item_contact);

        adapterPhone.setListener(this);
        adapterPhone.setNameData("phone");
        binding.lvPhone.setAdapter(adapterPhone);
        adapterPhone.setData(dataPhone);

        adapterWebsite.setListener(this);
        adapterWebsite.setNameData("domin");
        binding.lvWebsite.setAdapter(adapterWebsite);
        adapterWebsite.setData(dataWebsite);

        adapterEmail.setListener(this);
        adapterEmail.setNameData("email");
        binding.lvEmail.setAdapter(adapterEmail);
        adapterEmail.setData(dataEmail);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = binding.toobar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.setListener(this);
        binding.setUser(user);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_editcontact;
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

    @Override
    public void onAddOtherPhoneClicked() {
        binding.edtAddphone.setVisibility(View.VISIBLE);
        binding.layoutphone.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAddOtherWebsiteCliked() {
        binding.edtAddWebsite.setVisibility(View.VISIBLE);
        binding.layoutwebsite.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAddOtherEmailClicked() {

        binding.edtAddEmail.setVisibility(View.VISIBLE);
        binding.layoutEmail.setVisibility(View.VISIBLE);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSavePhoneClicked() {

        String phone = binding.edtAddphone.getText().toString();

        if (phone.isEmpty()) {
            Toast.makeText(this, "Number phone do not empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isContains(phone, dataPhone)) {
            Toast.makeText(EditContactActivity.this, "Add other phone failed,number phone contained list phone", Toast.LENGTH_SHORT).show();
        } else {

            dataPhone.add(phone);
            hashMap.replace("phone", dataPhone);
            adapterPhone.setData(dataPhone);
            Toast.makeText(EditContactActivity.this, "Add another phone successfly", Toast.LENGTH_SHORT).show();
            binding.edtAddphone.setText("");

        }
    }

    @Override
    public void onCancelPhoneClicked() {
        binding.edtAddphone.setVisibility(View.GONE);
        binding.layoutphone.setVisibility(View.GONE);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSaveWebsiteClicked() {


        String website = binding.edtAddWebsite.getText().toString();

        if (website.isEmpty()) {
            Toast.makeText(EditContactActivity.this, "Website do not empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isContains(website, dataWebsite)) {
            Toast.makeText(EditContactActivity.this, "Add another website failed, website contained list", Toast.LENGTH_SHORT).show();
        } else {
            dataWebsite.add(website);
            hashMap.replace("domin", dataWebsite);
            adapterWebsite.setData(dataWebsite);
            Toast.makeText(EditContactActivity.this, "Add another wevsite successfly", Toast.LENGTH_SHORT).show();
            binding.edtAddWebsite.setText("");

        }


    }

    @Override
    public void onCancelWebsiteClicked() {
        binding.edtAddWebsite.setVisibility(View.GONE);
        binding.layoutwebsite.setVisibility(View.GONE);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSaveEmailClicked() {
        String email = binding.edtAddEmail.getText().toString();

        HashMap<String, Object> hashMap = new HashMap<>();

        if (email.isEmpty()) {
            Toast.makeText(EditContactActivity.this, "Email do not empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isContains(email, dataEmail)) {
            Toast.makeText(EditContactActivity.this, "Add another email faile, email contained list", Toast.LENGTH_SHORT).show();
        } else {
            dataEmail.add(email);
            hashMap.replace("email", dataEmail);
            adapterEmail.setData(dataEmail);
            Toast.makeText(EditContactActivity.this, "Add another email successfly", Toast.LENGTH_SHORT).show();
            binding.edtAddEmail.setText("");

        }

    }

    @Override
    public void onCancelEmailClicked() {

        binding.edtAddEmail.setVisibility(View.GONE);
        binding.layoutEmail.setVisibility(View.GONE);

    }

    @Override
    public void onSaveClicked() {

        String address = binding.edtAddress.getText().toString();

        if (address.isEmpty()) {
            address = "default";
        }

        hashMap.put("address", address);

        binding.layout.setAlpha((float) 0.6);
        binding.proressBar.setVisibility(View.VISIBLE);

        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    binding.layout.setAlpha(1);
                    binding.proressBar.setVisibility(View.GONE);
                    Toast.makeText(EditContactActivity.this, "Save successfly", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditContactActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    binding.layout.setAlpha(1);
                    binding.proressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onCancelClicked() {

        finish();

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

    public boolean isContains(String s, ArrayList<String> data) {
        for (String s1 : data) {
            if (s.equals(s1)) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDeletePhoneClicked(String item, String nameData) {

        if (nameData.equals("phone")) {
            dataPhone.remove(item);
            adapterPhone.setData(dataPhone);
            hashMap.replace("phone", dataPhone);
        } else if (nameData.equals("domin")) {
            dataWebsite.remove(item);
            adapterWebsite.setData(dataWebsite);
            hashMap.replace("domin", dataWebsite);

        } else {
            dataEmail.remove(item);
            adapterEmail.setData(dataEmail);
            hashMap.replace("email", dataEmail);
        }


    }
}

