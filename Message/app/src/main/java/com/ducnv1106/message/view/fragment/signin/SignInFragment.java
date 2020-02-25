package com.ducnv1106.message.view.fragment.signin;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ducnv1106.message.Constants;
import com.ducnv1106.message.R;
import com.ducnv1106.message.databinding.FragmentSigninBinding;

import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.MainActivity;
import com.ducnv1106.message.view.acitivity.StartActivity;
import com.ducnv1106.message.view.fragment.BaseFragment;
import com.ducnv1106.message.viewmodel.MyViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SignInFragment extends BaseFragment<FragmentSigninBinding> implements SignInListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private MyViewModel myViewModel;
    private StartActivity start;


    @Override
    protected void initView() {

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();
        binding.setListener(this);
        start = (StartActivity) getContext();

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_signin;
    }

    @Override
    protected String getTitle() {
        return "Sign in";
    }

    @Override
    public void onSignInCliked() {
        String email = binding.edtEmail.getText().toString();
        String password = binding.edtPassword.getText().toString();

        if (!confirmInput()) {
            return;
        }

        start.getBinding().proressBar.setVisibility(View.VISIBLE);
        start.getBinding().layout.setAlpha((float) 0.7);
        signin(email, password);

    }

    private void signin(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    updateDataViewModel();
//                    if (firebaseUser.isEmailVerified()) {
//
//
//
//
//                    } else {
//                        Toast.makeText(getContext(), "Please verify your email address ", Toast.LENGTH_SHORT).show();
//                    }
                } else {

                    start.getBinding().proressBar.setVisibility(View.GONE);
                    start.getBinding().layout.setAlpha(1);

                    Toast.makeText(getContext(), "Login fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateDataViewModel() {
        myViewModel.initUser();
        myViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra(Constants.EXTRA_USER, user);
                start.getBinding().proressBar.setVisibility(View.GONE);
                start.getBinding().layout.setAlpha(1);
                startActivity(intent);
                start.finish();

            }
        });
    }

    public boolean validateEmail() {
        String emailInput = binding.textinputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            binding.textinputEmail.setError("Field can't be empty");
            return false;
        } else {
            binding.textinputEmail.setError(null);
            return true;
        }

    }

    public boolean validatePassword() {
        String passwordInput = binding.textinputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            binding.textinputPassword.setError("Field can't be empty");
            return false;
        } else if (passwordInput.length() > 15) {
            binding.textinputPassword.setError("Password too long");
            return false;

        } else if(passwordInput.length()<7){
            binding.textinputPassword.setError("Password too short");
            return false;
        }
        binding.textinputPassword.setError(null);
        return true;
    }

    public boolean confirmInput() {

        if (!validateEmail() | !validatePassword()) {
            return false;
        }
        return true;
//        String input = "Email: " + binding.textinputEmail.getEditText().getText().toString();
//        input += "\n";
//        input += "Password: " +binding.textinputPassword.getEditText().getText().toString();
//        Toast.makeText(getContext(),input,Toast.LENGTH_SHORT).show();

    }

}
