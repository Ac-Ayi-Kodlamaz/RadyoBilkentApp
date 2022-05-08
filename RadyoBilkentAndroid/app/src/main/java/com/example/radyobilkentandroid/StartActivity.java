package com.example.radyobilkentandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        firebaseAuth = FirebaseAuth.getInstance();

        //TODO

        /*startActivity(new Intent(this, NowPlayingActivity.class));
        finish();
        */

//        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, RegisterActivity.class));
//            finish();
//        }
//        else {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }


    }

}