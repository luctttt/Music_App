package com.example.music_app.homeFragment;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "song")
public class SongModel implements Serializable {

    private String songName;
    private String artistName;
    private String linkSong;
    private String linkImage;
    private String linkMusic;

    //private int isFavorite;


    @PrimaryKey(autoGenerate = true)
    private int id;

//    @PrimaryKey(autoGenerate = true)
//    private int id ;


    public SongModel() {
    }

    public SongModel(String songName, String artistName, String linkSong, String linkImage, String linkMusic ) {
        this.songName = songName;
        this.artistName = artistName;
        this.linkSong = linkSong;
        this.linkImage = linkImage;
        this.linkMusic = linkMusic;
        //this.isFavorite = isFavorite;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getLinkSong() {
        return linkSong;
    }

    public void setLinkSong(String linkSong) {
        this.linkSong = linkSong;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
