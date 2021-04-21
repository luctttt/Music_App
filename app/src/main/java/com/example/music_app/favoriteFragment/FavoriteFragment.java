package com.example.music_app.favoriteFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.music_app.databinding.FragmentFavoriteBinding;
import com.example.music_app.playsong.MainActivity;
import com.example.music_app.playsong.Song;
import com.example.music_app.room.SongDataBase;

import java.util.List;


public class FavoriteFragment extends Fragment implements ClickItemFavorite {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        com.example.music_app.databinding.FragmentFavoriteBinding binding = FragmentFavoriteBinding.inflate(inflater, container, false);

        binding.recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getContext()));

        List<FavoriteModel>dsSong = SongDataBase.getInstance(getActivity()).songDao().getListSongFavorite();

        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(getActivity() , dsSong , this);

        binding.recyclerViewFavorite.setAdapter(favoriteAdapter);

        return binding.getRoot();
    }

    @Override
    public void click(int position) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt("position", position);
        bundle.putString("fragment", "favoriteFragment");
        intent.putExtra("BUNDLE", bundle);

        startActivity(intent);
    }

}