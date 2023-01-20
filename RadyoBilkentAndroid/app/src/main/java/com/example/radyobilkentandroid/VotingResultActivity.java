package com.example.radyobilkentandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;

public class VotingResultActivity extends AppCompatActivity {

    private TextView song1;
    private TextView song2;
    private TextView song3;
    private TextView song4;

    private FirebaseFirestore mDB;
    private DocumentReference mReference;
    private ArrayList<Long> songPoints;
    private ArrayList<String> songNames;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_result);

        song1 = (TextView) findViewById(R.id.songStat1);
        song2 = (TextView) findViewById(R.id.songStat2);
        song3 = (TextView) findViewById(R.id.songStat3);
        song4 = (TextView) findViewById(R.id.songStat4);

        mDB = FirebaseFirestore.getInstance();
        mReference = mDB.collection("votingSession").document("Voting Session: 1");
        updateTextViews();

        timer = new CountDownTimer(1000000, 5000){
            @Override
            public void onTick(long millisUntilFinished) {
                updateTextViews();
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();

    }

    public void updateTextViews(){
        mReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> map = documentSnapshot.getData();
                songPoints = (ArrayList<Long>) map.get("votes");
                songNames = (ArrayList<String>) map.get("songList");
                Long total_votes = 0l;
                for (Long l: songPoints) {
                    total_votes += l;

                }
                //TODO division by 0
                song1.setText(songNames.get(0) + " " + songPoints.get(0) + " / " + total_votes);
                song2.setText(songNames.get(1) + " " + songPoints.get(1) + " / " + total_votes);
                song3.setText(songNames.get(2) + " " + songPoints.get(2) + " / " + total_votes);
                song4.setText(songNames.get(3) + " " + songPoints.get(3) + " / " + total_votes);
            }
        });
    }
}