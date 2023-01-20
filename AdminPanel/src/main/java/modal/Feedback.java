package modal;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static modal.Storable.db;

//this class will store all the variables of the feedback message that user writes
public class Feedback
{
    String id;
    String title;
    String content;
    String user;

    public Feedback(String content, String user, String title, String id)
    {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public Feedback() {
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Feedback> getData() throws ExecutionException, InterruptedException {
        ArrayList<Feedback> result = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> list = db.collection("feedbacks").get().get().getDocuments();
            for (QueryDocumentSnapshot document : list) {
                Feedback feedback = new Feedback(document.getString("Content"),
                                document.getString("User"),
                                document.getString("Title"),
                        document.getId());
                result.add(feedback);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
    public void deleteDoc(){
        DocumentReference reference = db.collection("feedbacks").document(id);
        ApiFuture<WriteResult> result = reference.delete();
        try{
            System.out.println("Removed in " + result.get().getUpdateTime());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    public String display()
    {
        String output = "";
        output = content + "\n\n" + "Sent by:" + user;

        return output;
    }
}
