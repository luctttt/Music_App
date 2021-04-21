package com.example.music_app.notification;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.music_app.R;
import com.example.music_app.service.MusicService;

public class PendingIntent {

    public static void createPendingIntentMusic(Context context, RemoteViews remoteViews, boolean play) {
        // button previous
        Intent intentPrevious = new Intent();
        intentPrevious.setClass(context.getApplicationContext(), MusicService.class);
        intentPrevious.setAction("PREVIOUS");
        android.app.PendingIntent pendingIntentPrevious = android.app.PendingIntent.getService(
                context.getApplicationContext(), 1,
                intentPrevious, android.app.PendingIntent.FLAG_UPDATE_CURRENT
        );
        remoteViews.setOnClickPendingIntent(R.id.btn_previous, pendingIntentPrevious);

        // button play/pause
        Intent intentPlay = new Intent();
        intentPlay.setClass(context.getApplicationContext(), MusicService.class);
        if (play) {
            intentPlay.setAction("PAUSE");
        } else {
            intentPlay.setAction("PLAY");
        }
        android.app.PendingIntent pendingIntentPlay = android.app.PendingIntent.getService(
                context.getApplicationContext(), 2,
                intentPlay, android.app.PendingIntent.FLAG_UPDATE_CURRENT
        );
        remoteViews.setOnClickPendingIntent(R.id.btn_play, pendingIntentPlay);

        // button next
        Intent intentNext = new Intent();
        intentNext.setClass(context.getApplicationContext(), MusicService.class);
        intentNext.setAction("NEXT");
        android.app.PendingIntent pendingIntentNext = android.app.PendingIntent.getService(
                context.getApplicationContext(), 3,
                intentNext, android.app.PendingIntent.FLAG_UPDATE_CURRENT
        );
        remoteViews.setOnClickPendingIntent(R.id.btn_next, pendingIntentNext);
    }
}
