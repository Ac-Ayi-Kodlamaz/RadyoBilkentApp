package com.example.radyobilkentandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Gender;

public class VotingActivity extends AppCompatActivity {

    private EditText points;
    private Button song1;
    private Button song2;
    private Button song3;
    private Button song4;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mDB;
    private DocumentReference mReference;
    private DocumentReference uReference;

    private ArrayList<String> songNames;
    private ArrayList<Long> songPoints;

    private long userPoints;
    //TODO
    // check is number valid
    // check user has enough points
    // update voting count

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDB = FirebaseFirestore.getInstance();

        points = findViewById(R.id.points_field);

        song1 = findViewById(R.id.song1);
        song2 = findViewById(R.id.song2);
        song3 = findViewById(R.id.song3);
        song4 = findViewById(R.id.song4);

        mReference = mDB.collection("votingSession").document("test");
        uReference = mDB.collection("users").document(mUser.getUid());

        mReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Map<String, Object> map = documentSnapshot.getData();
                    songNames = (ArrayList<String>) map.get("SongNames");
                    songPoints = (ArrayList<Long>) map.get("songPoints");
                    song1.setText(songNames.get(0));
                    song2.setText(songNames.get(1));
                    song3.setText(songNames.get(2));
                    song4.setText(songNames.get(3));


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // TODO make this more sensible
                Log.d("GET_FIRESTORE_REFERENCE", "onFailure: could not get firestore reference");
            }
        });

        song1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long point;
                String point_string = points.getText().toString();
                if (point_string.equals("")){
                    point = 0l;
                }
                else{
                    point = Long.parseLong(points.getText().toString());
                }
                uReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> map = documentSnapshot.getData();
                            userPoints = (Long) map.get("points");
                            if(userPoints > point){
                                userPoints = userPoints - point;
                                updateUserPoints(userPoints);

                                Long num = songPoints.get(0);
                                songPoints.set(0,num+point);
                                updateVotingSession();
                                goToResult();
                            }
                            else{
                                Toast.makeText(VotingActivity.this, "Not Enough Points", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("GET_FIRESTORE_REFERENCE", "onFailure: could not get firestore reference");
                    }
                });

            }
        });

        song2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long point;
                String point_string = points.getText().toString();
                if (point_string.equals("")){
                    point = 0l;
                }
                else{
                    point = Long.parseLong(points.getText().toString());
                }
                uReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> map = documentSnapshot.getData();
                            userPoints = (Long) map.get("points");
                            if(userPoints > point){
                                userPoints = userPoints - point;
                                updateUserPoints(userPoints);

                                Long num = songPoints.get(1);
                                songPoints.set(1,num+point);
                                updateVotingSession();
                                goToResult();
                            }
                            else{
                                Toast.makeText(VotingActivity.this, "Not Enough Points", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("GET_FIRESTORE_REFERENCE", "onFailure: could not get firestore reference");
                    }
                });

            }
        });

        song3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long point;
                String point_string = points.getText().toString();
                if (point_string.equals("")){
                    point = 0l;
                }
                else{
                    point = Long.parseLong(points.getText().toString());
                }
                uReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> map = documentSnapshot.getData();
                            userPoints = (Long) map.get("points");
                            if(userPoints > point){
                                userPoints = userPoints - point;
                                updateUserPoints(userPoints);

                                Long num = songPoints.get(2);
                                songPoints.set(2,num+point);
                                updateVotingSession();
                                goToResult();
                            }
                            else{
                                Toast.makeText(VotingActivity.this, "Not Enough Points", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("GET_FIRESTORE_REFERENCE", "onFailure: could not get firestore reference");
                    }
                });

            }
        });

        song4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long point;
                String point_string = points.getText().toString();
                if (point_string.equals("")){
                    point = 0l;
                }
                else{
                    point = Long.parseLong(points.getText().toString());
                }
                uReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> map = documentSnapshot.getData();
                            userPoints = (Long) map.get("points");
                            if(userPoints > point){
                                userPoints = userPoints - point;
                                updateUserPoints(userPoints);

                                Long num = songPoints.get(3);
                                songPoints.set(3,num+point);
                                updateVotingSession();
                                goToResult();
                            }
                            else{
                                Toast.makeText(VotingActivity.this, "Not Enough Points", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("GET_FIRESTORE_REFERENCE", "onFailure: could not get firestore reference");
                    }
                });

            }
        });



    }

    public void updateUserPoints(Long newPoint){
        uReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Map<String, Object> map = documentSnapshot.getData();
                    Long points = (Long) map.get("points");
                    String username = (String) map.get("username");
                    Gender gender = Gender.valueOfLabel("Do not wish to disclose");

                    HashMap<String, Object> new_map = new HashMap<>();

                    new_map.put("username",username);
                    new_map.put("gender", gender);
                    new_map.put("points",newPoint);

                    uReference.set(new_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("USER_UPDATE:", "User updated successfully");
                            }
                            else {
                                Log.d("USER_UPDATE:", "Could not update user");
                            }
                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("GET_FIRESTORE_REFERENCE", "onFailure: could not get firestore reference");
            }
        });
    }

    public void goToResult(){
        Intent intent = new Intent(this, VotingResultActivity.class);
        startActivity(intent);
        finish();
    }
    public void updateVotingSession(){

        HashMap<String, Object> new_map = new HashMap<>();

        new_map.put("SongNames",songNames);
        new_map.put("songPoints", songPoints);

        mReference.set(new_map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("USER_UPDATE:", "User updated successfully");
                }
                else {
                    Log.d("USER_UPDATE:", "Could not update user");
                }
            }
        });
    }
}