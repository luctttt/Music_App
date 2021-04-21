package com.example.music_app.homeFragment;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class CustomDataBindingHomeFragment {
    @BindingAdapter({"setTextNameSong"})
    public static void setTextNameSong(TextView nameSong,String name) {
        nameSong.setText(name);
    }
    @BindingAdapter({"setTextArtistSong"})
    public static void setTextArtistSong(TextView artistSong,String name) {
        artistSong.setText(name);
    }
    @BindingAdapter({"setImageSong"})
    public static void setTextNameSong(ImageView img,String url) {
        Glide.with(img.getContext()).load(url).into(img);
    }
}
