package com.example.music_app.favoriteFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_app.databinding.ItemFavoriteFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<FavoriteModel>dsSong = new ArrayList<>();
     ClickItemFavorite clickItemFavorite ;

    Context context ;

    public FavoriteAdapter(Context context , List<FavoriteModel>dsSong , ClickItemFavorite clickItemFavorite) {
        this.context = context;
        this.dsSong = dsSong ;
        this.clickItemFavorite = clickItemFavorite;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemFavoriteFragmentBinding binding = ItemFavoriteFragmentBinding.inflate(inflater , parent , false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteModel songModel = dsSong.get(position);
        holder.binding.setData(songModel);

        holder.binding.getRoot().setOnClickListener(v -> {
            clickItemFavorite.click(position);
        });
    }

    @Override
    public int getItemCount() {
        return dsSong.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemFavoriteFragmentBinding binding ;

        public ViewHolder(ItemFavoriteFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
