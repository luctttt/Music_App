package com.example.music_app.favoriteFragment;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "album")
public class FavoriteModel implements Serializable {
    private String linkImage;
    private String linkMusic;
    private String artist;
    private String nameMusic;

    static int idSong = -1;

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    public FavoriteModel(String linkImage, String linkMusic, String artist, String nameMusic , Integer id) {

        this.linkImage = linkImage;
        this.linkMusic = linkMusic;
        this.artist = artist;
        this.nameMusic = nameMusic;
        this.id = id ;

    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getLinkMusic() {
        return linkMusic;
    }

    public void setLinkMusic(String linkMusic) {
        this.linkMusic = linkMusic;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getNameMusic() {
        return nameMusic;
    }

    public void setNameMusic(String nameMusic) {
        this.nameMusic = nameMusic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
