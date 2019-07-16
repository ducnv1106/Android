package com.phongbm.musicplayer.acitivities;

import android.content.Intent;
import android.widget.Toast;

import com.phongbm.musicplayer.App;
import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.api.ApiBuilder;
import com.phongbm.musicplayer.api.response.UserResponse;
import com.phongbm.musicplayer.base.BaseActivity;
import com.phongbm.musicplayer.databinding.ActivityLoginBinding;
import com.phongbm.musicplayer.views.MP3ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements LoginListener, Callback<UserResponse> {

    private MP3ProgressDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initAct() {
        binding.setListener(this);
        dialog = new MP3ProgressDialog(this);
    }

    @Override
    public void onLogin() {
        String userName = binding.edtUserName.getText().toString();
        String password = binding.edtPassword.getText().toString();
        if (userName.isEmpty() || password.isEmpty()){
            Toast.makeText(this, R.string.input_invalid, Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        dialog.show();
        ApiBuilder.getApi(this).login(userName, password)
                .enqueue(this);
    }

    @Override
    public void onGuest() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        dialog.dismiss();
        if (response.code() == 200) {
            UserResponse user = response.body();
            App app = (App) getApplicationContext();
            app.setUser(user);
            onGuest();
        }else{
            Toast.makeText(this, response.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        dialog.dismiss();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }
}
