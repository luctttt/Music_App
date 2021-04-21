package com.example.music_app.playsong;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.music_app.R;
import com.example.music_app.databinding.ActivityMainBinding;
import com.example.music_app.favoriteFragment.FavoriteModel;
import com.example.music_app.notification.CreateNotification;
import com.example.music_app.notification.Playable;
import com.example.music_app.room.SongDataBase;
import com.example.music_app.service.MusicService;

import java.text.SimpleDateFormat;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Playable {

    private ActivityMainBinding binding;

    @SuppressLint("StaticFieldLeak")
    static MusicService mMusicService;

    private int position;

    Animation animation;

    boolean isPause = true;

    NotificationManager manager;

    boolean isAlbum = false;

    Map<String, String> map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMusicService = new MusicService();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getPositionSongFromHomeActivity();

        openServiceUnBound();
        createConnectService();     // ket noi service

        createChannel();
        registerReceiver(broadcastReceiver, new IntentFilter("Song"));      // register action
        startService(new Intent(getBaseContext(), MusicService.class));

        createSongViewModel();      // goi sang ham viewmodel de nhan linkmp3

        songCompleted();

        addEvents();

        progressBar();

    }

    private void getPositionSongFromHomeActivity() {

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");

        String fragment = bundle.getString("fragment");

        if (fragment.equals("favoriteFragment")) {
            isAlbum = true;

        } else if (fragment.equals("homeFragment")) {

            isAlbum = false;
        }
        position = bundle.getInt("position");
    }

    private void openServiceUnBound() {     // service unbound 9 forge k chet
        // service unbound ( k can doi dky )
        Intent intent = new Intent();
        intent.setClass(this, MusicService.class);
        this.startService(intent);
    }

    private void createConnectService() {       // ket noi toi service
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                MusicService.MyBinder myBinder = (MusicService.MyBinder) service;

                mMusicService = myBinder.musicService;  // service duoc ket noi

                Log.d("TAG", "onServiceConnected: ........ : connect successfully !!");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };

        // service bound ( can dky ) , goi unbound truoc -> service unbound
        Intent intent = new Intent();
        intent.setClass(this, MusicService.class);
        this.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


    private void createSongViewModel() {
        MainViewModel mainViewModel = new MainViewModel(position, MainActivity.this, isAlbum);

        mainViewModel.getLiveDataMap().observe(this, map -> {

            this.map = map;
            mMusicService.play(MainActivity.this, map.get("linkMusic"), isPause, position, map.get("nameMusic"), map.get("artist"));

            FavoriteModel favoriteModels = SongDataBase.getInstance(this).songDao().searchSongFavoriteWithNameSong(map.get("nameMusic"));

            if (favoriteModels != null) {
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
            }

            binding.setData(new Song(map.get("nameMusic"), map.get("linkMusic"), map.get("linkImage")));
            updateTimeSong();
        });

        mainViewModel.isMax().observe(this, aBoolean -> {
            if (!aBoolean) {
                position = 0;
            }
        });

    }

    private void songCompleted() {

        binding.btnPlay.setImageResource(R.drawable.ic_baseline_pause_24);
        mMusicService.songCompleted();
    }

    private void updateTimeSong() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                binding.tvTimeRun.setText(simpleDateFormat.format(mMusicService.getCurrentPositionProgressbar()));

                binding.tvSumTime.setText(simpleDateFormat.format(mMusicService.timeSong()));
                binding.seekBar.setMax(mMusicService.timeSong());

                binding.seekBar.setProgress(mMusicService.getCurrentPositionProgressbar());

                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionName");

            switch (action) {
                case CreateNotification.ACTION_PREVIOUS:
                    onSongPrevious();
                    Log.d("TAG", "onReceive: Previous.....");
                    break;
                case CreateNotification.ACTION_PLAY:
                    if (mMusicService.isPlay()) {
                        onSongPause();
                    } else {
                        onSongPlay();
                    }
                    Log.d("TAG", "onReceive: Play.....");
                    break;
                case CreateNotification.ACTION_NEXT:
                    onSongNext();
                    Log.d("TAG", "onReceive: Next.....");
                    break;

            }
        }
    };


    private void addEvents() {

        animation = AnimationUtils.loadAnimation(this, R.anim.ani_rotate);

        if (mMusicService.isPlay()) {
            binding.imgSong.startAnimation(animation);
        } else {
            binding.imgSong.clearAnimation();
        }

        binding.btnPlay.setOnClickListener(v -> {
            if (mMusicService.isPlay()) {
                onSongPause();
            } else {
                onSongPlay();
            }
            updateTimeSong();
        });

        binding.btnNext.setOnClickListener(v -> onSongNext());

        binding.btnPrevious.setOnClickListener(v -> onSongPrevious());

        binding.btnFavorite.setOnClickListener(v -> {       // add favorite

            FavoriteModel favoriteModels = SongDataBase.getInstance(this).songDao().searchSongFavoriteWithNameSong(map.get("nameMusic"));

            if (favoriteModels == null) {
                Toast.makeText(this, "Đã thêm vào album !!", Toast.LENGTH_SHORT).show();
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);

                SongDataBase.getInstance(this).songDao()
                        .insertSongFavorite(
                                new FavoriteModel(map.get("linkImage"),
                                        map.get("linkMusic"),
                                        map.get("artist"),
                                        map.get("nameMusic"), null));

            } else {
                Toast.makeText(this, "Đã xóa khỏi album !!", Toast.LENGTH_SHORT).show();
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                //SongDataBase.getInstance(this).songDao().deleteSong(songModel);
                SongDataBase.getInstance(this).songDao().deleteSong(favoriteModels);

            }

        });

    }

    @Override
    public void onSongNext() {
        position++;
        createSongViewModel();
        updateTimeSong();
        songCompleted();
        CreateNotification.createNotification(MainActivity.this, map.get("nameMusic"), map.get("artist"), position, 10, mMusicService.isPlay());
    }

    @Override
    public void onSongPlay() {
        binding.btnPlay.setImageResource(R.drawable.ic_baseline_pause_24);
        mMusicService.start();
        CreateNotification.createNotification(MainActivity.this, map.get("nameMusic"), map.get("artist"), position, 10, !mMusicService.isPlay());
    }

    @Override
    public void onSongPause() {
        binding.btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        mMusicService.pause();
        CreateNotification.createNotification(MainActivity.this, map.get("nameMusic"), map.get("artist"), position, 10, !mMusicService.isPlay());
    }

    @Override
    public void onSongPrevious() {
        position--;
        createSongViewModel();
        updateTimeSong();
        songCompleted();
        CreateNotification.createNotification(MainActivity.this, map.get("nameMusic"), map.get("artist"), position, 10, mMusicService.isPlay());
    }

    private void progressBar() {
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {      // event seekBar
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {      // khi keo seekBar
                mMusicService.updateProgressBar(binding.seekBar.getProgress());
            }
        });
    }


    public void createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("TAG", "createChannel: ................");
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NO", "NO", importance);
            channel.setDescription("NO");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

}