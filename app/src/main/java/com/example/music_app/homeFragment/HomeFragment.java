package com.example.music_app.homeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.music_app.databinding.FragmentHomeBinding;
import com.example.music_app.playsong.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements Inter {

    private FragmentHomeBinding binding;
    private SongAdapter adapter;

    private List<SongModel> dsSong = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));

        getDataViewModel();       // lay data tu service

        return binding.getRoot();
    }


    private void getDataViewModel() {
        SongViewModel songViewModel = new SongViewModel(getContext());
        binding.progressBar.setVisibility(View.VISIBLE);

        songViewModel.getListSongLiveData().observe(this, songModels -> {
            if (songModels != null) {
                //SongDataBase.getInstance(getContext()).songDao().insertSong(songModels);        // add database
                dsSong.addAll(songModels);
            }
            binding.progressBar.setVisibility(View.GONE);
            adapter = new SongAdapter(songModels, this);
            binding.recyclerview.setAdapter(adapter);

        });
    }

    @Override
    public void onClickItem(int position) {

        Intent intent = new Intent(getContext(), MainActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt("position", position);
        bundle.putString("fragment","homeFragment");
        intent.putExtra("BUNDLE", bundle);

        startActivity(intent);
    }

}