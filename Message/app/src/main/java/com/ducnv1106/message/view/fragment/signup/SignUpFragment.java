package com.ducnv1106.message.view.fragment.signup;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ducnv1106.message.R;

import com.ducnv1106.message.databinding.FragmentSignupBinding;

import com.ducnv1106.message.view.acitivity.StartActivity;
import com.ducnv1106.message.view.fragment.BaseFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUpFragment extends BaseFragment<FragmentSignupBinding> implements SignUpListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StartActivity start;

    @Override
    protected void initView() {
        firebaseAuth = FirebaseAuth.getInstance();

        binding.setListener(this);
        start = (StartActivity) getContext();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_signup;
    }

    @Override
    protected String getTitle() {
        return "Sign up";
    }

    @Override
    public void onSignUpClicked() {
        String username = binding.edtName.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String phone = binding.edtPhone.getText().toString();
        String password = binding.edtPassword.getText().toString();
        String cofirmpassword = binding.edtConfirmPassword.getText().toString();


        if (!confirmInput()) {
            return;
        }

        start.getBinding().proressBar.setVisibility(View.VISIBLE);
        start.getBinding().layout.setAlpha((float) 0.7);
        signup(username, email, phone, password, cofirmpassword);

    }

    private void signup(final String username, final String email, final String phone, final String password, String cofirmpassword) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                start.getBinding().layout.setAlpha(1);
                                start.getBinding().proressBar.setVisibility(View.GONE);

                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                                HashMap<String, Object> hashMap = new HashMap<>();

                                ArrayList<String> listPhone = new ArrayList<>();
                                listPhone.add(phone);

                                ArrayList<String> listDomin = new ArrayList<>();
                                listDomin.add("https://Mrkiller.com.vn");

                                ArrayList<String> listEmail = new ArrayList<>();
                                listEmail.add(email);

                                hashMap.put("avatar", "default");
                                hashMap.put("coverimage", "default");
                                hashMap.put("userId", userId);
                                hashMap.put("username", username);
                                hashMap.put("email", listEmail);
                                hashMap.put("phone", listPhone);
                                hashMap.put("password", password);
                                hashMap.put("birthday", "default");
                                hashMap.put("gender", "default");
                                hashMap.put("interested", "default");
                                hashMap.put("country", "default");
                                hashMap.put("religious", "default");
                                hashMap.put("political", "default");
                                hashMap.put("domin", listDomin);
                                hashMap.put("address", "163/33 Ngoc Dai - Dai Mo - Nam Tu Niem - Ha Noi");


                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });

                                Toast.makeText(getContext(), "Please check your email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {
                    start.getBinding().layout.setAlpha(1);
                    start.getBinding().proressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "You can't sign up with email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validateUsername() {
        String userNameInput = binding.textinputUsername.getEditText().getText().toString().trim();

        if (userNameInput.isEmpty()) {
            binding.textinputUsername.setError("Field can't not empty");
            return false;
        }
        binding.textinputUsername.setError(null);
        return true;
    }

    public boolean validateEmail() {
        String emailInput = binding.textinputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            binding.textinputEmail.setError("Field can't not empty");
            return false;
        }
        binding.textinputEmail.setError(null);
        return true;
    }

    public boolean validatePhoneNumber() {
        String phoneNumberInput = binding.textinputPhonenumber.getEditText().getText().toString().trim();
        if (phoneNumberInput.isEmpty()) {
            binding.textinputPhonenumber.setError("Field can't not empty");
            return false;
        }
        binding.textinputEmail.setError(null);
        return true;
    }

    public boolean validatePassword() {
        String passwordInput = binding.textinputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            binding.textinputPassword.setError("Field can't not empty");
            return false;
        } else if (passwordInput.length() > 15) {
            binding.textinputPassword.setError("Password too long");
            return false;
        } else if (passwordInput.length() < 7) {
            binding.textinputPassword.setError("Password too short");
            return false;
        }
        binding.textinputPassword.setError(null);
        return true;
    }

    public boolean validateConfirmPassword() {
        String confirmPasswordInput = binding.textinputConfirmpassword.getEditText().getText().toString().trim();

        if (confirmPasswordInput.isEmpty()) {
            binding.textinputConfirmpassword.setError("Field can't not empty");
            return false;
        } else if (confirmPasswordInput.length() > 15) {
            binding.textinputConfirmpassword.setError("Confirm Password too long");
            return false;
        } else if (confirmPasswordInput.length() < 7) {
            binding.textinputConfirmpassword.setError("Password too short");
            return false;
        }
        binding.textinputConfirmpassword.setError(null);
        return true;
    }

    public boolean confirmInput() {
        if (!validateEmail() | !validateUsername() | !validatePhoneNumber() | !validatePassword() | !validateConfirmPassword()) {

            return false;
        }

        return true;
    }
}
