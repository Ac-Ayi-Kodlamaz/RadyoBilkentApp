package com.example.radyobilkentandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import model.Blog;
import model.Podcast;

public class PodcastActivity extends AppCompatActivity {

    public static ArrayList<Podcast> podcasts;

    private FirebaseFirestore db;
    private CollectionReference mCollection;

    private ViewPager2 pager;

    private PodcastFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        podcasts = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        mCollection = db.collection("podcasts");

        pager = findViewById(R.id.podcast_pager);

        receiveData();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void receiveData() {

        mCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (int i = 0; i < task.getResult().size(); i++) {
                    Map<String, Object> map = task.getResult().getDocuments().get(i).getData();
                    Podcast newPodcast = new Podcast();
                    newPodcast.setTitle((String) map.get("title"));
                    newPodcast.setCreator((String) map.get("creator"));
                    newPodcast.setCoverImageURL((String) map.get("image"));
                    newPodcast.setDate(((Timestamp) map.get("date")).toDate());
                    newPodcast.setDuration((Long) map.get("duration"));
                    newPodcast.setUrl((String) map.get("link"));
                    newPodcast.setDescription((String) map.get("description"));
                    newPodcast.setTimesConsumed((Long) map.get("timesConsumed"));
                    podcasts.add(newPodcast);
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                adapter = new PodcastFragmentAdapter(fragmentManager, getLifecycle(), podcasts.size());
                pager.setAdapter(adapter);
            }
        });

    }

}