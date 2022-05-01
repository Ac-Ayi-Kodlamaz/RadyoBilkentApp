package com.example.adminpanel;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;

import java.awt.Image;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Song extends Content {

    public final int MAX_SIZE = 10^6;
    public String album;
    public String genre;
    public ArrayList<Integer> points;

    //constructors
    //default constructor
    public Song(){
        points = new ArrayList<>();
    }
    //general constructor
    public Song(String title, Image cover, Date date,
                Duration duration, String creator, int timesConsumed, URL link,
                String album, String genre, ArrayList<Integer> points) {
        super(title, cover, date, duration, creator, timesConsumed, link);
        this.album = album;
        this.genre = genre;
        this.points = points;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
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

    /**
     * Adding the recent points to the song for statistical and voting purposes
     * @param point : User's points to be added
     */
    public void addVote( int point)
    {
        //TODO
        update(point);//this method will both update arrayList points
        // and update the points field in Firestore

    }

    /**
     * Returning last ten points for statistical purposes
     * @return long for last ten points
     */
    public long getLastTen()
    {
        long sum = 0;
        for ( int i = 0 ; i < points.size();i++)
            sum = sum + points.get(i);
        return sum;
    }


    /**
     * Returning all points for statistical purposes
     * @return long for all points
     */
    public long getPoints()
    {
        return points.stream().mapToLong(value -> value).sum();
    }


    @Override
    public void addDoc() {
        DocumentReference reference = db.collection("songs").document(title);
        Map<String, Object> map = new HashMap<>();
        /*for(Field field: Content.class.getDeclaredFields()){
            try {
                if(field.canAccess(this))
                    map.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for(Field field: Song.class.getDeclaredFields())
        {
            if(field.canAccess(this))
            {
                try {
                    map.put(field.getName(), field.get(this));
                }
                catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }*/
        map.put("title", title);
        map.put("cover", cover);
        map.put("date", date);
        map.put("duration", duration);
        map.put("creator", creator);
        map.put("timesConsumed", timesConsumed);
        map.put("link", link.toString());
        map.put("album", album);
        map.put("genre", genre);
        map.put("points", points);
        System.out.println(map);
        ApiFuture<WriteResult> result = reference.set(map);
        try {
            System.out.println("Adding time: " + result.get().getUpdateTime());
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(int point) {
        DocumentReference reference = db.collection("songs").document(title);
        points.add((Integer) point);
        ApiFuture<WriteResult> result = reference.update("points", points);
        try {
            System.out.println("update time: " + result.get().getUpdateTime());
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
    }
    @Override
    public void deleteDoc() {
        DocumentReference reference = db.collection("songs").document(title);
        ApiFuture<WriteResult> result = reference.delete();
        try {
            System.out.println("Deleting time: " + result.get().getUpdateTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Content> getData() {
        ArrayList<Content> result = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = db.collection("songs").get();
        try {
            List<QueryDocumentSnapshot> list = querySnapshot.get().getDocuments();
            for(QueryDocumentSnapshot document: list)
            {
                Song song = new Song();
                song.setDate(document.getDate("date"));
                song.setLink(new URL(document.getString("link")));
                song.setCover((Image)document.get("cover"));
                song.setCreator(document.getString("creator"));
                song.setAlbum(document.getString("album"));
                song.setGenre(document.getString("genre"));
                song.setTitle(document.getString("title"));
                song.setPoints((ArrayList<Integer>) document.get("points"));
                song.setTimesConsumed(document.getLong("timesConsumed").intValue());
                song.setDuration((Duration)document.get("duration"));
                result.add(song);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    return result;
    }
    //toString method

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", cover=" + cover +
                ", date=" + date +
                ", duration=" + duration +
                ", creator='" + creator + '\'' +
                ", timesConsumed=" + timesConsumed +
                ", link=" + link +
                ", MAX_SIZE=" + MAX_SIZE +
                ", album='" + album + '\'' +
                ", genre='" + genre + '\'' +
                ", points=" + points +
                '}';
    }
}
