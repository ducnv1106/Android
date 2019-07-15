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

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private final String TAG="RegisterFrament";
    private EditText editUsername;
    private EditText editPassword;
    private Button btnOK;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView");
        View v=inflater.inflate(R.layout.register,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onActivityCreate");
        super.onActivityCreated(savedInstanceState);
        editUsername=getActivity().findViewById(R.id.edit_register_username);
        editPassword=getActivity().findViewById(R.id.edit_register_password);
        btnOK=getActivity().findViewById(R.id.btn_OK);
        btnOK.setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
            MainActivity mainActivity= (MainActivity) getActivity();
            mainActivity.showFragment(mainActivity.getFmLogin());
    }
    public void setData(String username,String password){
        editUsername.setText(username);
        editPassword.setText(password);
    }
}
