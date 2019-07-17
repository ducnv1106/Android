package com.t3h.readfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.t3h.readfile.databinding.ActivityMainBinding;

import java.io.File;
import java.security.Permission;

public class MainActivity extends AppCompatActivity implements FileAdapter.ItemFileOnClickListener {
    private ActivityMainBinding binding;
    private FileAdapter adapter;
    private FileManager manager=new FileManager();

    private final String[] PEMISSONS={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private String currentFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        if (checkPermisson()==true){
            initView();
        }else {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(PEMISSONS,0);
            }
        }

    }

    private boolean checkPermisson(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            for (String p: PEMISSONS){
                int check=checkSelfPermission(p);
                if(check!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(checkPermisson()){
//            initView();
//        }else {
//            finish();
//        }
//    }

    private void initView() {
        adapter=new FileAdapter(this);
        binding.lvFile.setAdapter(adapter);
        adapter.setItemFileOnClickListener(this);
        readFile(FileManager.PATH);
    }

    private void readFile(String path){
        currentFolder=path;
        File [] files=manager.getFiles(path);
        adapter.setData(files);
    }

    @Override
    public void onItemFileClicked(File file) {
            if(file.isDirectory()){
                readFile(file.getPath());
            }
    }

    @Override
    public void onBackPressed() {
        if(currentFolder.equals(FileManager.PATH)){
            super.onBackPressed();
        }else {
            File file=new File(currentFolder);
            readFile(file.getParent());
        }
    }
}
