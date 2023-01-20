package modal;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;

import java.time.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class VotingSession implements Storable<VotingSession> {

    public static int counter = 0;
    private ArrayList<String> songList;
    private String title;
    private Duration time;
    private Date startTime;
    private ArrayList<Integer> votes;

    public VotingSession() {
    }

    public String getTitle() {
        return title;
    }

    public VotingSession(String title, int second, Date startTime){
        this.startTime = startTime;
        this.title = title;
        time = Duration.ofSeconds(second);
        songList = new ArrayList<>();
        votes = new ArrayList<>();
        //timer = new Timer();

    }
    public VotingSession(String title, int second, Date startTime, ArrayList<Integer> points, ArrayList<String> songs)
    {
        this(title, second, startTime);
        this.votes = points;
        this.songList = songs;
    }

    /**
     * adding songs to the voting session
     * @param song
     */
    public void addSongs(Song song)
    {
        if(songList.size()<4)
        {
            songList.add(song.title);
            votes.add(0);
        }
        else{
            System.out.println("Song is exceeded the max number of songs");
        }
    }

    /**
     * setting the duration of the voting session
     * @param seconds
     */
    public void setTime(int seconds){
        time = Duration.ofSeconds(seconds);
    }
    public String display(){

        String toString = "";
        toString = title + "\n"+
                "Date: " + startTime + "\n" +
                "Song1: " +  songList.get(0)+ "  Points: " + votes.get(0) +  "\n"+
                "Song2: " +  songList.get(1)+ "  Points: " + votes.get(1) +   "\n" +
                "Song3: " +  songList.get(2)+ "  Points: " + votes.get(2) +  "\n" +
                "Song4: " +  songList.get(3)+ "  Points: " + votes.get(3) ;
        return toString;
    }

    /**
     * this method will update the points of the given song in both votes array
     * and in Firestore database
     * @param song song that its vote will be updated
     * @param points
     */
    public void voteFor(Song song, int points)
    {
        int index = songList.indexOf(song);
        votes.set(index, votes.get(index) + points);
        song.addVote(points);
    }

    /*public void startSession()
    {
        isActive = true;
    }*/
    public void endSession()
    {
        SongHandler songHandler = new SongHandler();

        getVotesOfSession();

        int indexSong = 0;
        for ( int i = 1 ; i < votes.size(); i++)
        {
            if ( votes.get(indexSong) < votes.get(i))
                indexSong = i;
        }

        String chosen = songList.get(indexSong);
        songHandler.setPlayingSong(Song.searchFullName(chosen).link);


    }

    //This will set the votes of the session from database
    public void getVotesOfSession()
    {

    }

    /**
     * this method checks whether this session is finished or not and updates them accordingly
     */
    /*private void checkSessionFinished(){
        if(LocalTime.now().minusSeconds(time.toSeconds()).isBefore(LocalTime.ofInstant(startTime.toInstant(),ZoneId.systemDefault())))
        {
            isFinished = true;
        }
    }*/
    public static List<VotingSession> getSongsNotFinished()
    {
        List<VotingSession> list = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> ActiveSessions = db.collection("votingSession").whereEqualTo("isFinished", false).get().get().getDocuments();
            for (QueryDocumentSnapshot document : ActiveSessions) {
                VotingSession v = new VotingSession(document.getString("title"),
                        document.getLong("time").intValue(), document.getDate("date"));
                list.add(v);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void addDoc() {
        DocumentReference reference = db.collection("votingSession").document(title);
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("time", time.getSeconds());
        map.put("songList", songList);
        map.put("startTime",startTime);
        map.put("votes", votes);
        ApiFuture<WriteResult> result = reference.set(map);
        try{
            System.out.println("Adding time: " + result.get().getUpdateTime());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(int point) {
    //no code
    }

    @Override
    public void deleteDoc() {
        DocumentReference reference = db.collection("votingSession").document(title);
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
    public ArrayList<VotingSession> getData() {
        ArrayList<VotingSession> result = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> list = db.collection("votingSession").get().get().getDocuments();
            for(QueryDocumentSnapshot document : list)
            {
                if(document.contains("title") && document.contains("startTime")) {
                    VotingSession v = new VotingSession(document.getString("title"),
                            document.getLong("time").intValue(),
                            document.getDate("date"),(ArrayList<Integer>) document.get("votes"), (ArrayList<String>) document.get("songList"));
                    result.add(v);
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String toString() {
        return "VotingSession{" +
                "songList=" + songList +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", startTime=" + startTime +
                ", votes=" + votes +
                '}';
    }
}
