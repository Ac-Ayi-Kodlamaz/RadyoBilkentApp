package com.example.adminpanel;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;

import java.awt.Image;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Blog extends Content
{
    public boolean isMarked;
    public String content;
    //constructors
    //empty(default) constructor
    public Blog(){}
    //general constructor(all variables are initialized)
    public Blog(String title, Image cover, Date date, Duration duration, String creator, int timesConsumed, URL link, boolean isMarked, String content) {
        super(title, cover, date, duration, creator, timesConsumed, link);
        this.isMarked = isMarked;
        this.content = content;
    }
    //getters and setters

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Leading the user to the related Spotify link of the current song.
     * @return a boolean type for successful openings
     */
    public boolean openInBrowser()
    {
        //TODO (Android Studio)

        return false;
    }

    @Override
    public void addDoc() {
        DocumentReference reference = db.collection("blogs").document(title);
        Map<String, Object> map = new HashMap<>();
        try {
            for (Field field : Blog.class.getDeclaredFields()) {
                if (!field.getName().equals("link")) {
                    map.put(field.getName(), field.get(this));
                } else
                    map.put("link", link.toString());
            }
            ApiFuture<WriteResult> result = reference.set(map);
            System.out.println("update time: " + result.get().getUpdateTime());
        }catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int point) {
    // nothing to do
    }

    @Override
    public void deleteDoc() {
        DocumentReference reference = db.collection("blogs").document(title);
        ApiFuture<WriteResult> result = reference.delete();
    }

    @Override
    public ArrayList<Content> getData() {
        ArrayList<Content> result = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> snapshotList = db.collection("blogs").get().get().getDocuments();
            for (QueryDocumentSnapshot document : snapshotList) {

                Blog blog = new Blog(document.getString("title"),
                        (Image) document.get("cover"), document.getDate("date"),
                        (Duration) document.get("duration"), document.getString("creator"),
                        document.getLong("timesConsumed").intValue(), new URL(document.getString("link")),
                        document.getBoolean("isMarked"), document.getString("content"));
                result.add(blog);

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
