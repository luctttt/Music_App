package com.example.music_app.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.music_app.favoriteFragment.FavoriteModel;
import com.example.music_app.homeFragment.SongModel;

import java.util.List;

@Dao
public interface SongDao {
    @Insert
    void insertListSong(List<SongModel>songModels);

    @Insert
    void insertSong(SongModel songModels);


    @Query("SELECT *FROM song")        // songModel : bang o model
    List<SongModel> getListSong();

    @Delete
    void deleteSong(SongModel songModels);

    @Query("DELETE FROM song")
    void deleteAllSong();

    @Query("SELECT *FROM song WHERE songName LIKE '%' || :nameSong || '%' ")
    List<SongModel> searchSong(String nameSong);



    @Insert
    void insertListSongFavorite(List<FavoriteModel> favoriteModels);

    @Insert
    void insertSongFavorite(FavoriteModel favoriteModel);


    @Query("SELECT *FROM album")        // songModel : bang o model
    List<FavoriteModel> getListSongFavorite();

    @Delete
    void deleteSong(FavoriteModel favoriteModel);

    @Query("DELETE FROM album")
    void deleteAllSongFavorite();

    @Query("SELECT *FROM album WHERE id = :nameSong")
    FavoriteModel searchSongFavoriteWithId(int nameSong);

    @Query("SELECT *FROM album WHERE nameMusic LIKE :nameSong")
    FavoriteModel searchSongFavoriteWithNameSong(String nameSong);

    //@Query("SELECT COUNT(*) FROM album")




}
