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
            System.out.println("Connecting to DB");
            FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");
            FirestoreOptions firestoreOptions =
                    FirestoreOptions.getDefaultInstance().toBuilder()
                            .setProjectId("radyo-bilkent-app")
                            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .build();
            db = firestoreOptions.getService();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Firestore getDb(){return db;}
}
