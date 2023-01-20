package modal;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static modal.Storable.db;

public class Programme
{
    String title;
    String explanation;
    Date startTime;
    String time;

    public Programme() {
    }

    public String getTitle() {
        return title;
    }

    public Programme(String title, String explanation, Date startTime, String time) {
        this.title = title;
        this.explanation = explanation;
        this.startTime = startTime;
        this.time = time;
    }

    //TODO
    public void addDoc() throws ExecutionException, InterruptedException
    {
        DocumentReference reference = db.collection("programms").document(title);
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("explanation", explanation);
        map.put("startTime", startTime);
        map.put("time", time);
        ApiFuture<WriteResult> result = reference.set(map);
        System.out.println("Written time: " + result.get().getUpdateTime());
    }

    public String display(){
        String toString = "";
        toString = title + "\n"+
                "Date: " + startTime.toString() + "\n" +
                "Explanation: " + explanation + "\n"+
                "Duration: " +  time + "\n";
        return toString;
    }

    public ArrayList<Programme> getData() throws ExecutionException, InterruptedException {

        ArrayList<Programme> result = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> snapshotList = db.collection("programms").get().get().getDocuments();
            for (QueryDocumentSnapshot document : snapshotList) {
                Programme programme = new Programme(document.getString("title"),
                        document.getString("explanation"),
                        document.getDate("startTime"),
                        document.getString("time"));
                    result.add(programme);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void deleteDoc(){
        DocumentReference reference = db.collection("programms").document(title);
        ApiFuture<WriteResult> result = reference.delete();
    }
}
