package com.t3h.buoi12.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.t3h.buoi12.MainActivity;
import com.t3h.buoi12.R;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnRegister;
    private final String TAG = "LoginFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreate");
        super.onActivityCreated(savedInstanceState);
        edtUsername = getActivity().findViewById(R.id.edit_login_username);
        edtPassword = getActivity().findViewById(R.id.edit_login_password);
        btnLogin = getActivity().findViewById(R.id.btn_login);
        btnRegister = getActivity().findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    public void setData(String username, String password) {
        edtPassword.setText(password);
        edtUsername.setText(username);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                break;
            case R.id.btn_register:
                MainActivity main = (MainActivity) getActivity();
                main.showFragment(main.getFmRegister());
                break;
        }
    }
}
