package com.example.radyobilkentandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import model.Gender;
import model.User;

public class MainActivity extends AppCompatActivity {

    private User user;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private FirebaseUser mUser;
    private DocumentReference mReference;

    private String songURL;
    private String imageURL;
    private String songName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDB = FirebaseFirestore.getInstance();

        //TODO make it dynamic
        String songPath = "Test";
        //
        mReference = mDB.collection("songs").document(songPath);

        mReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    Map<String, Object> map = documentSnapshot.getData();

                    songURL = (String) map.get("song_url");
                    imageURL = (String) map.get("image_url");
                    songName = (String) map.get("name");


                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    NowPlayingFragment nowPlayingFragment = NowPlayingFragment.newInstance(songURL, songName);
                    ft.replace(R.id.nowPlayingFragment, nowPlayingFragment);
                    ft.commit();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // TODO make this more sensible
                Log.d("GET_FIRESTORE_REFERENCE", "onFailure: could not get firestore reference");
            }
        });



//        String url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        NowPlayingFragment nowPlayingFragment = NowPlayingFragment.newInstance(url, songName);
//        ft.replace(R.id.nowPlayingFragment, nowPlayingFragment);
//        ft.commit();
    }

}