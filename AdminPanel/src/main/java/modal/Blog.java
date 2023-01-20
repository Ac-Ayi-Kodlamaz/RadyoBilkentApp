package modal;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Blog extends Content
{
    //Instance Variables
    private String content;

    //empty(default) constructor
    public Blog(){}

    //general constructor(all variables are initialized)
    public Blog(String title, URL url, Date date, long duration, String creator, int timesConsumed, URL link, String content)
    {
        super(title, url, date, duration, creator, timesConsumed, link);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean openInBrowser()
    {

        return false;
    }

    @Override
    public void addDoc()
    {
        DocumentReference reference = db.collection("blogs").document(title);
        Map<String, Object> map = new HashMap<>();
        try {
                map.put("title",title);
                map.put("image",cover.toString());
                map.put("date",date);
                map.put("duration",duration);
                map.put("creator",creator);
                map.put("timesConsumed",timesConsumed);
                map.put("link",link.toString());
                map.put("content",content);
                ApiFuture<WriteResult> result = reference.set(map);
                System.out.println("Writing time: " + result.get().getUpdateTime());
            } catch (ExecutionException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(int point)
    {
    // nothing to do
    }

    @Override
    public void deleteDoc()
    {
        DocumentReference reference = db.collection("blogs").document(title);
        ApiFuture<WriteResult> result = reference.delete();
    }

    public String toString(){
        String toString = "";
        toString = title + "\n"+
                "Date: " + date + "\n" +
                "Duration: " + duration + "\n"+
                "Creator: " +  creator + "\n"+
                "TimesConsumed: " + timesConsumed + "\n"+
                "image: " + cover.toString() + "\n" +
                "Link: " + link + "\n" +
                "Content: " +content  ;
        return toString;
    }

    @Override
    public  ArrayList<Content> getData()
    {
        ArrayList<Content> result = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> snapshotList = db.collection("blogs").get().get().getDocuments();
            for (QueryDocumentSnapshot document : snapshotList) {

                Blog blog = new Blog(document.getString("title"),
                        new URL(document.getString("image")), document.getDate("date"),
                        (long) document.getLong("duration"), document.getString("creator"),
                        document.getLong("timesConsumed").intValue(), new URL(document.getString("link")),
                        document.getString("content"));
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
