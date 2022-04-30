package com.example.adminpanel;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.File;
import java.io.FileInputStream;

public class FireBaseService {
    private Firestore db;
    public FireBaseService(){
        try {
            File file = new File("trialFile.txt");
            System.out.println("Connecting to DB");
            file.createNewFile();
            FileInputStream serviceAccount = new FileInputStream("./serviceAccountKey.json");
            FirestoreOptions firestoreOptions =
                    FirestoreOptions.getDefaultInstance().toBuilder()
                            .setProjectId("trial-eae9f")
                            .setCredentials(GoogleCredentials.getApplicationDefault())
                            .build();
            db = firestoreOptions.getService();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Firestore getDb(){return db;}
}
