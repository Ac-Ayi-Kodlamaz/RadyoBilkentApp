package com.example.radyobilkentandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import model.User;

public class MainActivity extends AppCompatActivity {

    public static User user;

    public static FirebaseAuth mAuth;
    public static FirebaseFirestore mDB;
    public static FirebaseUser mUser;
    private DocumentReference mSongReference;

    private Button votingButton;

    private Button voting;
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
        voting = findViewById(R.id.goToVoting);

        voting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToVoting();
            }
        });


        user = new User(mDB, mUser);

        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        TopBarFragment topBarFragment = TopBarFragment.newInstance();
        ft1.replace(R.id.top_bar_frame, topBarFragment);
        ft1.commit();

        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ProgramFlowFragment programFlowFragment = ProgramFlowFragment.newInstance();
        ft2.replace(R.id.program_flow_frame, programFlowFragment);
        ft2.commit();

        votingButton = findViewById(R.id.voting_session_button);
        votingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VotingActivity.class);
            }
        });

        //TODO make it dynamic
        String songPath = "Test";
        //
        mSongReference = mDB.collection("songs").document(songPath);

        mSongReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    Map<String, Object> map = documentSnapshot.getData();

                    songURL = (String) map.get("song_url");
                    imageURL = (String) map.get("image_url");
                    songName = (String) map.get("name");


                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    NowPlayingFragment nowPlayingFragment = NowPlayingFragment.newInstance(songURL, songName, imageURL);
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
    private void goToVoting(){
        Intent intent = new Intent(this, VotingActivity.class);
        startActivity(intent);
    }

}