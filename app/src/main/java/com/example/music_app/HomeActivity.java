package com.example.music_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.music_app.favoriteFragment.FavoriteFragment;
import com.example.music_app.homeFragment.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.music_app.databinding.ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , new HomeFragment()).commit();

        // event click item bottomNavigation
        binding.bottomNav.setOnNavigationItemSelectedListener(item -> {
           int id = item.getItemId();

           Fragment fragmentSelected = null ;
           if (id==R.id.mn_home){
               fragmentSelected = new HomeFragment();
           } else if (id==R.id.mn_favote) {
               fragmentSelected = new FavoriteFragment();
           }

            FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
            assert fragmentSelected != null;
            transaction.replace(R.id.frameLayout , fragmentSelected);
            transaction.commit();

           return false ;
        });
    }
}