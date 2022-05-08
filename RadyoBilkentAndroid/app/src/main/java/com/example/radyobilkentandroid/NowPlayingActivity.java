package com.example.radyobilkentandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Toast;

public class NowPlayingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        NowPlayingFragment nowPlayingFragment = NowPlayingFragment.newInstance(url, "Never Gonna Give You Up \n Rick Astley");
        ft.replace(R.id.nowPlayingFragment, nowPlayingFragment);
        ft.commit();


        setContentView(R.layout.activity_now_playing);


    }
}