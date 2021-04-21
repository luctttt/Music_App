package com.example.music_app.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent("Song")
                .putExtra("actionName" , intent.getAction()));
    }
}
