package com.example.adminpanel;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;

import java.awt.Image;
import java.net.MalformedURLException;
import  java.util.List;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Podcast extends Content
{
    public String description;

    //constructors
    //empty constructors
    public Podcast(){}

    public Podcast(String title, Image cover, Date date, Duration duration, String creator, int timesConsumed, URL link, String description) {
        super(title, cover, date, duration, creator, timesConsumed, link);
        this.description = description;
    }

    /**
     * Leading the user to the related Spotify link of the current song.
     * @return a boolean type for successful openings
     */
    public boolean openInSpotify()
    {
        //TODO (Android Studio)
        return false;
    }

    @Override
    public void addDoc() {
        DocumentReference reference = db.collection("podcasts").document(title);
        Map<String, Object> map = new HashMap<>();
        try {
            for (Field field : Podcast.class.getDeclaredFields()) {
                if (field.getName().equals("link")) {
                    map.put("link", link.toString());
                } else {
                    map.put(field.getName(), field.get(this));
                }
            }
            ApiFuture<WriteResult> result = reference.set(map);
            System.out.println("Writing time: " + result.get().getUpdateTime());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int point) {
        //nothing to Do
    }

    @Override
    public void deleteDoc() {
        DocumentReference reference = db.collection("podcasts").document(title);
        ApiFuture<WriteResult> result = reference.delete();
        try{
            System.out.println("Deletion time: " + result.get().getUpdateTime());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Content> getData() {
        ArrayList<Content> result = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> list = db.collection("podcasts").get().get().getDocuments();
            for (QueryDocumentSnapshot document : list) {
                Podcast p = new Podcast(document.getString("title"),
                        (Image) document.get("cover"), document.getDate("date"),
                        (Duration) document.get("duration"), document.getString("creator"),
                        document.getLong("timesConsumed").intValue(), new URL(document.getString("link")),
                        document.getString("description"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
