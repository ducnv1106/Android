package com.t3h.asynctaskdemo;

import android.os.AsyncTask;
import android.os.SystemClock;

public class AsyncTaskTime extends AsyncTask<String, String, String> {
    private TimeCallBack callBack;


    public AsyncTaskTime(TimeCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        int index=strings[0].indexOf(":");
        int lastindex=strings[0].lastIndexOf(":");

        int hours=Integer.parseInt(strings[0].substring(0,index));
        int minute=Integer.parseInt(strings[0].substring(index+1,lastindex));
        int seconds=Integer.parseInt(strings[0].substring(lastindex+1));


        while (true){
            seconds++;
            if (seconds>59){
                seconds=0;
                minute++;
            }
            if(minute>59){
                minute=0;
                hours++;
            }
            if(hours>23){
                hours=0;
            }
            SystemClock.sleep(1000);
            String converHours=String.valueOf(hours);
            if(hours<10){
                converHours="0"+converHours;
            }
            String convertMinute=String.valueOf(minute);
            if(minute<10){
                convertMinute="0"+convertMinute;
            }
            String converSeconds=String.valueOf(seconds);
            if(seconds<10){
                converSeconds="0"+seconds;
            }
            publishProgress(converHours+":"+convertMinute+":"+converSeconds);

        }

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        callBack.ontTimeUpdate(values[0]);
    }

    public interface TimeCallBack{
        void ontTimeUpdate(String time);
        void onTimeSuccess(String time);
    }
}
