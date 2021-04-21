package com.example.music_app.homeFragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_app.databinding.ItemSongHomeFragmentBinding;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private final List<SongModel>dsSong ;

    private final Inter inter ;

    public SongAdapter(List<SongModel> dsSong, Inter inter) {
        this.dsSong = dsSong;
        this.inter = inter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSongHomeFragmentBinding binding = ItemSongHomeFragmentBinding.inflate(inflater,parent,false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SongModel songModel = dsSong.get(position);

        holder.binding.setData(songModel);      // setData

        // click thì nhạc trước đó phải tắt
        holder.binding.getRoot().setOnClickListener(v -> inter.onClickItem(position));

        holder.binding.executePendingBindings();
    }



    @Override
    public int getItemCount() {
        return dsSong.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSongHomeFragmentBinding binding ;
        public ViewHolder (ItemSongHomeFragmentBinding binding){
            super(binding.getRoot());
            this.binding = binding ;
        }
    }
}
