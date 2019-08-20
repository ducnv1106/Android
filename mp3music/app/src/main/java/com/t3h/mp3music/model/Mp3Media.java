package com.t3h.mp3music.model;

import android.content.Context;
import android.graphics.LinearGradient;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.t3h.mp3music.systemdata.SystemData;

import java.util.ArrayList;
import java.util.Random;

public class Mp3Media implements MediaPlayer.OnCompletionListener {
    private Song song;
    private MediaPlayer mediaPlayer;
    private Context context;
    private ArrayList<Song> data;



    public Mp3Media(Context context, Song song) {
        this.context = context;
        this.song = song;
        release();
        Uri uri = Uri.parse(song.getData());
        mediaPlayer = MediaPlayer.create(context, uri);
        start();
        mediaPlayer.setOnCompletionListener(this);
        initData();
    }
    public void initData(){

        SystemData systemData=new SystemData(context);
        systemData.getAlbum();
        data=systemData.getDataSong();
        int a=data.size();

    }
    public void create(int position) {
        release();
        Uri uri=Uri.parse(data.get(position).getData());
        mediaPlayer=MediaPlayer.create(context,uri);
        start();

    }

    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void release(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
    }

    public int getDuration() {
        return mediaPlayer == null ? 0 : mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer == null ? 0 : mediaPlayer.getCurrentPosition();
    }

    public void seek(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }

    public void loop(boolean isLooping) {
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(isLooping);
        }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.e("Mp3Media","onComp");
        Random random=new Random();
        int position=random.nextInt(data.size());
        create(position);
    }
}
