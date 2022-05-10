package com.example.radyobilkentandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    private DocumentReference mCurrentSong;

    private Button votingButton;
    private String songURL;

    private String imageURL;
    private String songName;
    private String songPath;
    private String oldPath;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDB = FirebaseFirestore.getInstance();

        user = new User(mDB, mUser);

        startTopBarFragment();

        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ProgramFlowFragment programFlowFragment = ProgramFlowFragment.newInstance();
        ft2.replace(R.id.program_flow_frame, programFlowFragment);
        ft2.commit();

        votingButton = findViewById(R.id.voting_session_button);
        votingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToVoting();
            }
        });

        timer = new CountDownTimer(100000000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (songPath == null){
                    readCurrentSong(true);
                }
                else{
                    readCurrentSong(false);
                }
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();

        mCurrentSong = mDB.collection("currentSong").document("trial");


    }
    private void goToVoting(){
        Intent intent = new Intent(this, VotingActivity.class);
        startActivity(intent);
    }


    private void readCurrentSong(boolean isFirst){
        mCurrentSong.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    Map<String, Object> map = documentSnapshot.getData();
                    songPath = (String) map.get("name");

                    if (isFirst){
                        changeFrangment();
                        oldPath = songPath;
                    }
                    else{
                        if (!oldPath.equals(songPath)){
                            Toast.makeText(MainActivity.this, "music changed", Toast.LENGTH_SHORT).show();
                            changeFrangment();
                            oldPath = songPath;
                        }
                    }

                }
            }
        });
    }

    private void changeFrangment(){
        mSongReference = mDB.collection("songs").document(songPath);

        mSongReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    Map<String, Object> map = documentSnapshot.getData();

                    songURL = (String) map.get("link");
                    imageURL = (String) map.get("cover");
                    songName = (String) map.get("creator");
                    reloadFragment();
                }
                else{
                    Log.d("hataa", "dosya olmadı");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("GET_FIRESTORE_REFERENCE", "onFailure: could not get firestore reference");
            }
        });
    }
    private void reloadFragment(){
        //TODO değişim

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        NowPlayingFragment nowPlayingFragment = NowPlayingFragment.newInstance(songURL, songPath + "\n"+songName, imageURL);
        ft.replace(R.id.nowPlayingFragment, nowPlayingFragment);
        ft.commit();
    }

    private void readDatabase(){

    }

    private void startTopBarFragment() {
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        TopBarFragment topBarFragment = TopBarFragment.newInstance();
        ft1.replace(R.id.top_bar_frame, topBarFragment);
        ft1.commit();
    }




}