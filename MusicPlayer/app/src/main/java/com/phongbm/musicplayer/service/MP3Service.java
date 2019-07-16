package com.phongbm.musicplayer.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.model.Music;

import java.io.IOException;
import java.util.ArrayList;

public class MP3Service extends Service implements MediaPlayer.OnCompletionListener, Runnable {
    private Thread t;

    private ArrayList<Music> arrMusic;
    private MediaPlayer player;
    private int currentIndex;

    private MutableLiveData<Boolean> isPlaying = new MutableLiveData<>();
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLife = new MutableLiveData<>();
    private MutableLiveData<Music> music = new MutableLiveData<>();
    private MutableLiveData<Integer> current = new MutableLiveData<>();

    @Override
    public void onCreate() {
        super.onCreate();
        t = new Thread(this);
        t.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MP3Binder(this);
    }

    private void pushNotification() {
        Intent intent = new Intent(this, getClass());
        startService(intent);

        String CHANNEL_ID = "CHANNEL_ID";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.ui_notification);
        remoteViews.setTextViewText(R.id.tv_name, arrMusic.get(currentIndex).getTitle());
        if (player.isPlaying()) {
            remoteViews.setImageViewResource(R.id.im_play, R.drawable.ic_pause);
        } else {
            remoteViews.setImageViewResource(R.id.im_play, R.drawable.ic_play);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setCustomBigContentView(remoteViews);
        startForeground(1213232, builder.build());
    }


    public void setArrMusic(ArrayList<Music> arrMusic) {
        this.arrMusic = arrMusic;
    }

    public void create(final int index) {
        release();
        if (arrMusic == null) {
            new Throwable("Need call setArrMusic before call create");
            return;
        }
        try {
            player = new MediaPlayer();
            String data = arrMusic.get(index).getData();
            if (arrMusic.get(index).getAlbum() == null) {
                player.setDataSource("http://192.168.1.68/mp3music/" + data);
            } else {
                player.setDataSource(this, Uri.parse(data));
            }
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.setOnCompletionListener(MP3Service.this);
                    currentIndex = index;
                    name.postValue(arrMusic.get(index).getTitle());
                    isLife.postValue(true);
                    music.postValue(arrMusic.get(index));
                    start();
                }
            });
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void release() {
        if (player != null) {
            player.release();
            isPlaying.postValue(false);
        }
    }

    public void start() {
        if (player != null) {
            player.start();
            isPlaying.postValue(true);
            pushNotification();
        }
    }

    public void pause() {
        if (player != null) {
            player.pause();
            isPlaying.postValue(false);
            pushNotification();
        }
    }

    public void seekTo(int position) {
        if (player != null) {
            player.seekTo(position);
        }
    }

    public void loop(boolean isLoop) {
        if (player != null) {
            player.setLooping(isLoop);
        }
    }

    public int getDuration() {
        if (player != null) {
            return player.getDuration();
        }
        return 0;
    }

    public int getPosition() {
        if (player != null) {
            return player.getCurrentPosition();
        }
        return 0;
    }


    @Override
    public void run() {
        while (true) {
            current.postValue(getPosition());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static final int NEXT = 1;
    public static final int PREV = -1;

    @IntDef({NEXT, PREV})
    public @interface TypeChange {
    }

    public void change(@TypeChange int value) {
        currentIndex += value;
        if (currentIndex >= arrMusic.size()) {
            currentIndex = 0;
        } else if (currentIndex < 0) {
            currentIndex = arrMusic.size() - 1;
        }
        create(currentIndex);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        change(NEXT);
    }

    public class MP3Binder extends Binder {
        private MP3Service service;

        public MP3Binder(MP3Service service) {
            this.service = service;
        }

        public MP3Service getService() {
            return service;
        }
    }

    public MutableLiveData<Boolean> getIsPlaying() {
        return isPlaying;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<Boolean> getIsLife() {
        return isLife;
    }

    public MutableLiveData<Music> getMusic() {
        return music;
    }

    public ArrayList<Music> getArrMusic() {
        return arrMusic;
    }

    public MutableLiveData<Integer> getCurrent() {
        return current;
    }
}
