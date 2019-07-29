package com.t3h.miniproject.dowlnoad;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.widget.PopupMenu;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadAsync extends AsyncTask<String, Integer, String> {

    private DownloadCallback callback;

    public DownloadAsync(DownloadCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected String doInBackground(String... strings) {
        String link = strings[0];
        return download(link);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        callback.onDownloadUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callback.onDownloadSuccess(s);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String download(String link) {
        try {
            // connect file internet
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream in = connection.getInputStream();
            // create local file
            String path = Environment.getExternalStorageDirectory().getPath()
                    + "/ducnv1106/" + System.currentTimeMillis() + ".html";
            File f = new File(path);
            f.getParentFile().mkdirs();
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            // write file
            byte[] b = new byte[1024];
            int total=connection.getContentLength();
            int current = 0;
            int count = in.read(b);
            while (count > 0) {
                // calculator percent of downloaded
                current += count;
                int percent = (int) ((float)current/ total * 100);
                // update ui
                publishProgress(percent);
                // write file
                out.write(b, 0, count);
                count = in.read(b);
            }
            in.close();
            out.close();
            return f.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface DownloadCallback {
        void onDownloadUpdate(int percent);
        void onDownloadSuccess(String path);
    }
}