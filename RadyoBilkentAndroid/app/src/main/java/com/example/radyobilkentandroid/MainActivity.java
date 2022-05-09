package com.example.radyobilkentandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import model.User;

public class MainActivity extends AppCompatActivity {

    private User user;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        NowPlayingFragment nowPlayingFragment = NowPlayingFragment.newInstance(url, "Never Gonna Give You Up \n Rick Astley");
        ft.replace(R.id.nowPlayingFragment, nowPlayingFragment);
        ft.commit();
    }
}