package com.example.radyobilkentandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import model.Blog;

public class BlogActivity extends AppCompatActivity {

    public static ArrayList<Blog> blogs;

    private FirebaseFirestore db;
    private CollectionReference mCollection;

    private ViewPager2 pager;

    private BlogFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        blogs = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        mCollection = db.collection("blogs");

        pager = findViewById(R.id.blog_pager);

        receiveData();

    }

    private void receiveData() {

        mCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (int i = 0; i < task.getResult().size(); i++) {
                    Map<String, Object> map = task.getResult().getDocuments().get(i).getData();
                    Blog newBlog = new Blog();
                    newBlog.setTitle((String) map.get("title"));
                    newBlog.setCreator((String) map.get("creator"));
                    newBlog.setCoverImageURL((String) map.get("image"));
                    newBlog.setDate(((Timestamp) map.get("date")).toDate());
                    newBlog.setDuration((Long) map.get("duration"));
                    newBlog.setUrl((String) map.get("link"));
                    newBlog.setContent((String) map.get("content"));
                    newBlog.setTimesConsumed((Long) map.get("timesConsumed"));
                    blogs.add(newBlog);
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                adapter = new BlogFragmentAdapter(fragmentManager, getLifecycle(), blogs.size());
                pager.setAdapter(adapter);
            }
        });

    }

}