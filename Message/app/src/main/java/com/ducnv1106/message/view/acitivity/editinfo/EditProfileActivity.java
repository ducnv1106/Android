package com.ducnv1106.message.view.acitivity.editinfo;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.ducnv1106.message.R;

import com.ducnv1106.message.databinding.ActivityEditprofileBinding;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.acitivity.BaseActivity;
import com.ducnv1106.message.viewmodel.MyViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class EditProfileActivity extends BaseActivity<ActivityEditprofileBinding> implements EditProfileListener {

    private boolean isInterestedWomen = false;
    private boolean isInterestedMen = false;

    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private MyViewModel myViewModel;


    @Override
    protected int sytleId() {
        return R.style.Theme_Infomation;
    }

    @Override
    protected void initView() {
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        User user = myViewModel.getUser().getValue();
        binding.setUser(user);

        // get boolean isInterested
        setInterestedIn();
        Log.e("EditProfile", isInterestedWomen + "");
        Log.e("EditProfile", isInterestedMen + "");

        Toolbar toolbar = binding.toobar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        binding.setListener(this);


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_editprofile;
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
    public void onGenderMaleClicked() {

        binding.checkedGenderMale.setVisibility(View.VISIBLE);
        binding.checkedGenderFemale.setVisibility(View.GONE);
        binding.checkedGenderOther.setVisibility(View.GONE);

    }

    @Override
    public void onGenderFeMaleClicked() {

        binding.checkedGenderMale.setVisibility(View.GONE);
        binding.checkedGenderFemale.setVisibility(View.VISIBLE);
        binding.checkedGenderOther.setVisibility(View.GONE);
    }

    @Override
    public void onGenderOtherClicked() {

        binding.checkedGenderMale.setVisibility(View.GONE);
        binding.checkedGenderFemale.setVisibility(View.GONE);
        binding.checkedGenderOther.setVisibility(View.VISIBLE);

    }

    @Override
    public void onInterestedWomenClicked() {
        if (isInterestedWomen) {
            binding.checkedInterestedWomen.setVisibility(View.GONE);
            isInterestedWomen = false;
            Log.e("EditProfile", isInterestedWomen + "");
            Log.e("EditProfile", isInterestedMen + "");
        } else {
            binding.checkedInterestedWomen.setVisibility(View.VISIBLE);
            isInterestedWomen = true;
            Log.e("EditProfile", isInterestedWomen + "");
            Log.e("EditProfile", isInterestedMen + "");
        }
    }

    @Override
    public void onInterestedMenClicked() {

        if (isInterestedMen) {
            binding.checkedInterestedMen.setVisibility(View.GONE);
            isInterestedMen = false;
        } else {
            binding.checkedInterestedMen.setVisibility(View.VISIBLE);
            ;
            isInterestedMen = true;
        }

    }

    @Override
    public void onSaveClicked() {

       new AlertDialog.Builder(this).setTitle("Message").setMessage("Do you want to save?").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

               binding.layout.setAlpha((float) 0.6);
               binding.proressBar.setVisibility(View.VISIBLE);

               databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

               String date = binding.tvDate.getText().toString();
               String month = binding.tvMonth.getText().toString();
               String year = binding.tvYear.getText().toString();

               // Save gender of User
               String gender = checkGender();

               // Save interested in of User
               String interestedIn = checkIntertedIn();

               String country = binding.edtCountry.getText().toString();
               String religious = binding.edtReligious.getText().toString();
               String political = binding.edtPolitical.getText().toString();

               if (country.equals("")) {
                   country = "default";
               }
               if (religious.equals("")) {
                   religious = "default";
               }
               if (political.equals("")) {
                   political = "default";
               }

               HashMap<String, Object> hashMap = new HashMap<>();
               hashMap.put("birthday", date + "-" + month + "-" + year);
               hashMap.put("gender", gender);
               hashMap.put("interested", interestedIn);
               hashMap.put("country", country);
               hashMap.put("religious", religious);
               hashMap.put("political", political);

               databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()) {
                           Toast.makeText(EditProfileActivity.this, "Save successfly", Toast.LENGTH_SHORT).show();
                       }
                       binding.proressBar.setVisibility(View.GONE);
                       binding.layout.setAlpha(1);
                   }
               });

           }
       }).setNegativeButton("No", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

           }
       }).show();





    }

    @Override
    public void onCancelClicked() {

        finish();

    }

    @Override
    public void onDateOfBirthClicked() {

        Calendar calendar = Calendar.getInstance();

        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Log.e("EditProfile", year + "");
                Log.e("EditProfile", month + 1 + "");
                Log.e("EditProfile", dayOfMonth + "");
                binding.tvDate.setText(dayOfMonth + "");
                binding.tvMonth.setText((month + 1) + "");
                binding.tvYear.setText(year + "");

            }
        }, year, month, date);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        datePickerDialog.setCancelable(false);

        datePickerDialog.show();

    }

    // Check Gender of User
    public String checkGender() {
        String gender;
        if (binding.checkedGenderMale.getVisibility() == View.VISIBLE) {
            gender = "Male";
        } else if (binding.checkedGenderFemale.getVisibility() == View.VISIBLE) {
            gender = "FeMale";
        } else {
            gender = "Other";
        }
        return gender;
    }

    //
    public String checkIntertedIn() {

        String interestedIn;

        if (isInterestedWomen && !isInterestedMen) {
            interestedIn = "Women";
        } else if (isInterestedMen && !isInterestedWomen) {
            interestedIn = "Men";
        } else if (isInterestedWomen && isInterestedMen) {
            interestedIn = "Women,Men";
        } else {
            interestedIn = "Other";
        }

        return interestedIn;

    }

    // set boolean Interested
    public void setInterestedIn() {
        String interested = myViewModel.getUser().getValue().getInterested();

        if (interested.contains("Men")) {
            isInterestedMen = true;
        }
        if (interested.contains("Women")) {
            isInterestedWomen = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
