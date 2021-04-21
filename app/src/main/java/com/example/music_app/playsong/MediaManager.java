package com.example.music_app.playsong;

import android.media.MediaPlayer;
import android.util.Log;

import com.example.music_app.R;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class MediaManager implements MediaPlayer.OnErrorListener {
    private static MediaPlayer mediaPlayer = new MediaPlayer();

    boolean isStart;
    boolean isSongComplete = false;

    public void setPath(String path, boolean isPause) {       // download du lieu tu link mp3

        try {

            if (mediaPlayer.isPlaying() || isPause) {
                stop();
                mediaPlayer = new MediaPlayer();
            }

            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setDataSource(path);

            mediaPlayer.prepareAsync();
            // ham lay du lieu thuc hien o thread khac

            // sau khi tai nhac xong thi se tu dong duoc goi ham nay
            mediaPlayer.setOnPreparedListener(mp -> {
                Log.d("TAG", "onPrepared: Load is successfully !!!");
                startSong();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSong() {
        isStart = true;
        mediaPlayer.start();
    }

    public void pauseSong() {
        isStart = false;
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public boolean isPlayMedia() {
        return mediaPlayer.isPlaying();
    }

    public boolean songCompleted() {
        // khi play xong 1 bai hat
        mediaPlayer.setOnCompletionListener(mp -> {
            isSongComplete = true;
            mediaPlayer.setLooping(true);
        });
        return isSongComplete;
    }

    public int timeSong() {      // tong time
        return mediaPlayer.getDuration();
    }

    public void updateProgressBar(int time) {            // gan time phat nhac cua media theo seekbar
        mediaPlayer.seekTo(time);
    }

    public int setTimeProgressbar() {        // tra ve vi tri hien tai cua media theo miligiay
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.d("TAG", "onError: ......");
        return false;
    }

}
