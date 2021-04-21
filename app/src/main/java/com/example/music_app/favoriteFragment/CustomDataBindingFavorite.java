package com.example.music_app.favoriteFragment;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class CustomDataBindingFavorite {
    @BindingAdapter({"setNameSongFavorite"})
    public static void setNameSong(TextView nameSong, String name) {
        nameSong.setText(name);
    }

    @BindingAdapter({"setImageSongFavorite"})
    public static void setImageSong(ImageView img, String url) {
        Glide.with(img.getContext()).load(url).into(img);
    }
}
