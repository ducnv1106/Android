package com.t3h.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {
    private final String TAG="MyService";
    private boolean isRunning=false;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind");
        return new MyBinder(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStart");
        isRunning=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while (isRunning){
                    Log.e(TAG,(i++)+"");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning=false;
        Log.e(TAG,"Destroy");

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        private MyService service;

        public MyBinder(MyService service) {
            this.service = service;
        }

        public MyService getService() {
            return service;
        }
    }
}
