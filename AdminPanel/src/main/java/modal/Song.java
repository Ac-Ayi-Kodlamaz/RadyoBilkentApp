package modal;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Song extends Content {

    public final int MAX_SIZE = 10^6;
    public String album;
    public String genre;


    public Song() {}

    //general constructor
    public Song(String title, URL cover, Date date,
                long duration, String creator, int timesConsumed, URL link,
                String album, String genre, ArrayList<Integer> points)
    {
        super(title, cover, date, duration, creator, timesConsumed, link);
        this.album = album;
        this.genre = genre;
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
    /*public long getLastTen()
    {
        long sum = 0;
        for ( int i = 0 ; i < points.size();i++)
            sum = sum + points.get(i);
        return sum;
    }*/


    /**
     * Returning all points for statistical purposes
     * @return long for all points
     */
    /*public long getPoints()
    {
        return points.stream().mapToLong(value -> value).sum();
    }*/


    @Override
    public void addDoc()
    {
        DocumentReference reference = db.collection("songs").document(title);
        Map<String, Object> map = new HashMap<>();

        map.put("title", title);
        map.put("cover", cover.toString());
        map.put("date", date);
        map.put("duration", duration);
        map.put("creator", creator);
        map.put("timesConsumed", timesConsumed);
        map.put("link", link.toString());
        map.put("album", album);
        map.put("genre", genre);
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
    public void update(int point) {}
        /*DocumentReference reference = db.collection("songs").document(title);
        points.add((Integer) point);
        ApiFuture<WriteResult> result = reference.update("points", points);
        try {
            System.out.println("update time: " + result.get().getUpdateTime());
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
    }*/
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
                System.out.println("result of the document: " + document.getString("link"));
                song.setLink(new URL(document.getString("link")));
                song.setCover((URL)document.get("cover"));
                song.setCreator(document.getString("creator"));
                song.setAlbum(document.getString("album"));
                song.setGenre(document.getString("genre"));
                song.setTitle(document.getString("title"));
                song.setTimesConsumed(document.getLong("timesConsumed").intValue());
                song.setDuration((long)document.get("duration"));
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
                '}';
    }

    /**
     * this method will search the whole name of the song from database(Firestore)
     * @param text song name
     * @return Song object
     */
    public static Song searchFullName(String text)
    {
        Song song = new Song();
        try {
            List<QueryDocumentSnapshot> query = db.collection("songs")
                    .whereEqualTo("title", text).get().get().getDocuments();
            QueryDocumentSnapshot document = query.get(0);//since every song is unique only one results will be
            //taken for the result
                song.setDate(document.getDate("date"));
                song.setLink(new URL(document.getString("link")));
                if(document.getString("cover") == null)
                    song.setCover(new URL("https://stackoverflow.com/questions/23040582/custom-listcell-implements-invalidationlistener-repaint-components/23059340#23059340"));
                else
                    song.setCover(new URL(document.getString("cover")));
                song.setCreator(document.getString("creator"));
                song.setAlbum(document.getString("album"));
                song.setGenre(document.getString("genre"));
                song.setTitle(document.getString("title"));
                song.setTimesConsumed(document.getLong("timesConsumed").intValue());
                song.setDuration((long)document.getLong("duration"));
                System.out.println(song);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return song;
    }
    public ArrayList<String> getAllTitle() throws ExecutionException, InterruptedException {

        ArrayList<String> result = new ArrayList<>();
        List<QueryDocumentSnapshot> list = db.collection("songs").get().get().getDocuments();

        for (QueryDocumentSnapshot document: list)
        {
            result.add(document.getString("title"));
        }

        return result;
    }

    /**
     * This method will query songs that start with the given text.
     * @param text String that the wanted song starts with
     * @return arraylist that contains all the song that starts with this text
     */
    public static List<Song> searchSong(String text)
    {
        List<Song> searchResult = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> query = db.collection("songs")
                    .orderBy("title").whereGreaterThanOrEqualTo("title", text)
                    .whereLessThanOrEqualTo("title", text + "z").get().get().getDocuments();
            for(QueryDocumentSnapshot document: query)
            {
                Song song = new Song();
                song.setDate(document.getDate("date"));
                song.setLink(new URL(document.getString("link")));
                song.setCover(new URL(document.getString("cover")));
                song.setCreator(document.getString("creator"));
                song.setAlbum(document.getString("album"));
                song.setGenre(document.getString("genre"));
                song.setTitle(document.getString("title"));
                song.setTimesConsumed(document.getLong("timesConsumed").intValue());
                song.setDuration((long)document.getLong("duration"));
                searchResult.add(song);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return searchResult;
    }
}
