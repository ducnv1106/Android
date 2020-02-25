package com.ducnv1106.message;

import android.content.DialogInterface;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class AsyncTaskDowloadImage extends AsyncTask<String,Integer,String> {


    private DowloadCallback dowloadCallback;

    public AsyncTaskDowloadImage(DowloadCallback dowloadCallback) {
        this.dowloadCallback = dowloadCallback;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String link = strings[0];
        String path = strings[1];


        try {
            File file = new File(path);

            // tạo các thư mục;
            file.getParentFile().mkdirs();

            file.createNewFile();

            FileOutputStream outputStream = new FileOutputStream(file);

            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream=urlConnection.getInputStream();

            int total=urlConnection.getContentLength();
            int totalSave = 0;


            // dowload 1kb
            byte[] b = new byte[1024];
            int count = inputStream.read(b);

            while (count!=-1){
                totalSave+=count;

                float percent = (float) totalSave/total;

                publishProgress((int) (percent*100));


                outputStream.write(b,0,count);
                count=inputStream.read(b);
            }

            inputStream.close();
            outputStream.close();
           return file.getPath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        dowloadCallback.updatePercent(values[0]);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dowloadCallback.dowloadSuccess(0);
    }

    public interface DowloadCallback{
        void updatePercent(int percent);
        void dowloadSuccess(int percent);
    }
}
