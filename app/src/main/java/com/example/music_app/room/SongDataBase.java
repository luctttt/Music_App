package com.example.music_app.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.music_app.favoriteFragment.FavoriteModel;
import com.example.music_app.homeFragment.SongModel;

@Database(entities = {SongModel.class, FavoriteModel.class}, version = 2, exportSchema = false)
public abstract class SongDataBase extends RoomDatabase {


    private static final String DATABASENAME = "Song.db";

    private static SongDataBase instance;

    static Migration migration_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE  IF NOT EXISTS album (id INTERGER PRIMARY KEY , " +
                    "linkImage TEXT  , " +
                    "linkMusic TEXT, " +
                    "artist TEXT  ," +
                    " nameMusic TEXT  )");
        }
    };


    public static synchronized SongDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SongDataBase.class, DATABASENAME)
                    .allowMainThreadQueries()
                    .addMigrations(migration_1_2)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract SongDao songDao();
}

