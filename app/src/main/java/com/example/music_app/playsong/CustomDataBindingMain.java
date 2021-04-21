package com.example.music_app.playsong;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class CustomDataBindingMain {
    @BindingAdapter({"setNamSong"})
    public static void setNameSong(TextView nameSong, String name) {
        nameSong.setText(name);
    }

    @BindingAdapter({"setImageSong"})
    public static void setImageSong(ImageView img, String url) {
        Glide.with(img.getContext()).load(url).into(img);
    }
}
