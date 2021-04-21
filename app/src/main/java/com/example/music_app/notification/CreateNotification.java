package com.example.music_app.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.music_app.R;

public class CreateNotification {

    public static Notification notification;
    public static final String CHANNEL_ID = "channel";

    public static final String ACTION_PLAY = "actionPlay";
    public static final String ACTION_PREVIOUS = "actionPrevious";
    public static final String ACTION_NEXT = "actionNext";

    public static void createNotification(Context context, String nameMusic , String artist, int pos, int size, boolean isPlay) {


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bong_hoa_khong_thuoc_ve_ta);


        PendingIntent pendingIntentPrevious;
        int drawable_previous = R.drawable.ic_baseline_skip_previous_24;
        if (pos == 0) {
            pendingIntentPrevious = null;
            //drawable_previous = 0;
        } else {
            Intent intentPrevious = new Intent(context, MyBroadCast.class).setAction(ACTION_PREVIOUS);

            pendingIntentPrevious = android.app.PendingIntent.getBroadcast(context, 0,
                    intentPrevious, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
        }

        Intent intentPlay = new Intent(context, MyBroadCast.class).setAction(ACTION_PLAY);

        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);
        int drawable_play;
        if (isPlay) {
            drawable_play = R.drawable.ic_baseline_play_arrow_24;
        } else {
            drawable_play = R.drawable.ic_baseline_pause_24;
        }

        PendingIntent pendingIntentNext = null;
        int drawable_next = R.drawable.ic_skip_next;
        if (pos == size) {
            pendingIntentNext = null;
            //drawable_next = 0;
        } else {
            Intent intentNext = new Intent(context, MyBroadCast.class).setAction(ACTION_NEXT);
            pendingIntentNext = android.app.PendingIntent.getBroadcast(context, 0,
                    intentNext, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
        }


        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.bong_hoa_khong_thuoc_ve_ta)
                .setContentTitle(nameMusic)
                .setLargeIcon(icon)
                .setContentText(artist)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .addAction(drawable_previous, "previous", pendingIntentPrevious)
                .addAction(drawable_play, "play", pendingIntentPlay)
                .addAction(drawable_next, "next", pendingIntentNext)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                //.setStyle(new Notification.BigTextStyle().bigText(longText))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManagerCompat.notify(1, notification);


    }
}
