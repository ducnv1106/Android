package com.ducnv1106.message.view.fragment.image;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.usage.ExternalStorageStats;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.ducnv1106.message.AsyncTaskDowloadImage;
import com.ducnv1106.message.Constants;
import com.ducnv1106.message.R;
import com.ducnv1106.message.databinding.FragmentImageBinding;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.fragment.BaseFragment;

public class FragmentImage extends BaseFragment<FragmentImageBinding> implements AsyncTaskDowloadImage.DowloadCallback,ImageListener {

    private String urlImage;

    private ProgressDialog progressDialog;


    @Override
    protected void initView() {

        Glide.with(binding.image).load(urlImage).into(binding.image);

        binding.setListener(this);



    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected String getTitle() {
        return "hhh";
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public void onImageSaveClicked() {

        final AsyncTaskDowloadImage taskDowloadImage = new AsyncTaskDowloadImage(this);

        new AlertDialog.Builder(getContext()).setTitle("Message").setMessage("Do you want to save image?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("FragmentImage","Ok");
                        Log.e("FragmentImage",urlImage);

                        String pathEternalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                        String path = pathEternalStorage +"/"+System.currentTimeMillis()+".jpeg";
//                        binding.proressBar.setVisibility(View.VISIBLE);
                        taskDowloadImage.execute(urlImage, path);

                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.setTitle("Please Wait...");
                        progressDialog.setMessage("Preparing to download...");
                        progressDialog.setMax(100);
                        progressDialog.setCancelable(false);

                        progressDialog.show();




                    }
                }).setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("FragmentImage","No");
            }
        }).show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void updatePercent(int percent) {
//        binding.proressBar.setProgress(percent);
        progressDialog.setProgress(percent);


    }

    @Override
    public void dowloadSuccess(int percent) {
//        binding.proressBar.setProgress(percent);
//        binding.proressBar.setVisibility(View.GONE);
        progressDialog.dismiss();
    }
}
