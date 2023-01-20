package modal;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.File;
import java.io.FileInputStream;

public class FireBaseService extends Programme {
    private Firestore db;
    public FireBaseService(){
        try {
            File file = new File("root.txt");
            file.createNewFile();
            FileInputStream serviceAccount = new FileInputStream("radyo-bilkent-app-firebase-adminsdk-noq60-aac9a82e0a.json");
            FirestoreOptions firestoreOptions =
                    FirestoreOptions.getDefaultInstance().toBuilder()
                            .setProjectId("radyo-bilkent-app")
                            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .build();

            db = firestoreOptions.getService();
            System.out.println("Successfully connected");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Firestore getDb(){return db;}
}
