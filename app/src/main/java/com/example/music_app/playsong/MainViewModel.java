package com.example.music_app.playsong;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music_app.favoriteFragment.FavoriteModel;
import com.example.music_app.homeFragment.SongModel;
import com.example.music_app.room.SongDataBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel extends ViewModel {

    Map<String, String> map = new HashMap<>();

    @SuppressLint("StaticFieldLeak")
    private final Context context;

    private int position;

    MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Map<String, String>> liveData1 = new MutableLiveData<>();

    public MutableLiveData<Map<String, String>> getLiveDataMap() {
        return liveData1;
    }


    public MutableLiveData<Boolean> isMax() {
        return booleanMutableLiveData;
    }

    public MainViewModel(int position, Context context, boolean isAlbum) {
        this.context = context;
        this.position = position;

        if (isAlbum) {
            loadListAlbum();
        } else {
            loadListSong();
        }

    }

    private void loadListAlbum() {
        List<FavoriteModel> listFavorite = SongDataBase.getInstance(context).songDao().getListSongFavorite();

        if (position < 0 || position > listFavorite.size() - 1) {
            position = 0;
            booleanMutableLiveData.setValue(false);
        } else {
            booleanMutableLiveData.setValue(true);
        }


        if (map.size() != 0) {
            map.clear();
        }

        map.put("linkImage", listFavorite.get(position).getLinkImage());
        map.put("linkMusic", listFavorite.get(position).getLinkMusic());
        map.put("nameMusic", listFavorite.get(position).getNameMusic());
        map.put("artist", listFavorite.get(position).getArtist());

        liveData1.setValue(map);

    }

    private void loadListSong() {
        List<SongModel> list = SongDataBase.getInstance(context).songDao().getListSong();

        if (position < 0 || position > list.size() - 1) {
            position = 0;
            booleanMutableLiveData.setValue(false);
        } else {
            booleanMutableLiveData.setValue(true);
        }

        if (map.size() != 0) {
            map.clear();
        }

        map.put("linkImage", list.get(position).getLinkImage());
        map.put("linkMusic", list.get(position).getLinkMusic());
        map.put("nameMusic", list.get(position).getSongName());
        map.put("artist", list.get(position).getArtistName());

        liveData1.setValue(map);
    }


}
