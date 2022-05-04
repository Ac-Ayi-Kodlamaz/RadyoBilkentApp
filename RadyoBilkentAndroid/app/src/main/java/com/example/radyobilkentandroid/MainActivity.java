package com.example.radyobilkentandroid;

import androidx.appcompat.app.AppCompatActivity;

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

        mAuth = FirebaseAuth.getInstance();
        user = new User(mAuth.getCurrentUser());
        System.out.println(user.getUsername());
        System.out.println(user.getPoints());
        System.out.println(user.getGender());

    }
}