package com.example.music_app.playsong;

public class Song {
    private String title ;
    private String file ;
    private String imageSong ;

    public Song(String title, String file, String imageSong) {
        this.title = title;
        this.file = file;
        this.imageSong = imageSong;
    }

    public Song(String title, String file) {
        this.title = title;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getImageSong() {
        return imageSong;
    }

    public void setImageSong(String imageSong) {
        this.imageSong = imageSong;
    }
}
