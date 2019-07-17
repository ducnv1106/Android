package com.t3h.readfile;

import android.os.Environment;

import java.io.File;

public class FileManager {
    public static final String PATH= Environment.getExternalStorageDirectory().getPath(); // lấy ra đường dẫn bộ nhớ ngoài.

    public File[] getFiles(String path){
        File file=new File(path);
        return file.listFiles();
    }
}
