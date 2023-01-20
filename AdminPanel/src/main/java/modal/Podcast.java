package modal;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;

import java.net.MalformedURLException;
import  java.util.List;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Podcast extends Content
{
    public String description;

    public Podcast(){}

    public Podcast(String title, URL cover, Date date, long duration, String creator, int timesConsumed, URL link, String description)
    {
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

    public String toString()
    {
        String toString = "";
        toString = title + "\n"+
                "Date: " + date + "\n" +
                "Duration: " + duration + "\n"+
                "Creator: " +  creator + "\n"+
                "TimesConsumed: " + timesConsumed + "\n"+
                "Link: " + link + "\n" +
                "Description: " +description  ;
        return toString;
    }
    @Override
    public void addDoc()
    {
        DocumentReference reference = db.collection("podcasts").document(title);
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("title",title);
            map.put("image",cover.toString());
            map.put("date",date);
            map.put("duration",duration);
            map.put("creator",creator);
            map.put("timesConsumed",timesConsumed);
            map.put("link",link.toString());
            map.put("description",description);
            ApiFuture<WriteResult> result = reference.set(map);
            System.out.println("Writing time: " + result.get().getUpdateTime());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int point)
    {
        //nothing to Do
    }

    @Override
    public void deleteDoc()
    {
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
                        new URL(document.getString("image")), document.getDate("date"),
                        (long) document.getLong("duration"), document.getString("creator"),
                        document.getLong("timesConsumed").intValue(), new URL(document.getString("link")),
                        document.getString("description"));
                result.add(p);
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
