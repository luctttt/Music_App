package com.example.music_app.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;

import com.example.music_app.homeFragment.SongModel;
import com.example.music_app.notification.CreateNotification;
import com.example.music_app.playsong.MediaManager;
import com.example.music_app.room.SongDataBase;

import java.util.List;

public class MusicService extends LifecycleService  {

    public static MediaManager mediaManager = new MediaManager();

    int position;

    NotificationManager manager;

    Context context;


    @Override
    public void onCreate() {        // goi dau tien , taoj doi tuong quan sat
        super.onCreate();

        createChannel();

        Log.d("TAG", "onCreate: ..........");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {    // goi thu 2
        super.onStartCommand(intent, flags, startId);
        Log.d("TAG", "onStartCommand:........................ ");

        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        super.onBind(intent);
        return new MyBinder(this);
    }

    public static class MyBinder extends Binder {
        public MusicService musicService;

        public MyBinder(MusicService musicService) {
            this.musicService = musicService;
        }
    }

    public void play(Context context, String path, boolean isPause, int position, String nameMusic , String artist) {
        this.position = position;
        this.context = context;

        List<SongModel> dsSong = SongDataBase.getInstance(context).songDao().getListSong();

//        List<FavoriteModel> dsFavorite = SongDataBase.getInstance(context).songDao().getListSongFavorite();

        Log.d("TAG", "play ..............");

        createChannel();

        CreateNotification.createNotification(context, nameMusic , artist ,  position, dsSong.size() - 1, isPlay());

        mediaManager.setPath(path, isPause);
    }

    public void pause() {
        mediaManager.pauseSong();
    }

    public void start() {
        mediaManager.startSong();
    }

    public boolean isPlay() {
        return mediaManager.isPlayMedia();
    }

    public boolean songCompleted() {
        return mediaManager.songCompleted();
    }

    public int timeSong() {
        return mediaManager.timeSong();
    }

    public void updateProgressBar(int time) {
        mediaManager.updateProgressBar(time);
    }

    public int getCurrentPositionProgressbar() {
        return mediaManager.setTimeProgressbar();
    }

    public void createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("TAG", "createChannel: ................");
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NO", "NO", importance);
            channel.setDescription("NO");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }

}



