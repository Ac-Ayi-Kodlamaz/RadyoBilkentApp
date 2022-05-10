package model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.radyobilkentandroid.RegisterWithEmailActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private DocumentReference mReference;
    private Long points;
    private String username;
    private Gender gender;

    private boolean initialized;

//    private Avatar avatar;
//    private ArrayList<Item> items;

    public User() {
    }

    public User(FirebaseFirestore db, FirebaseUser firebaseUser) {
        this();
        if (firebaseUser != null) {
            prepareDatabase(firebaseUser, db);
            // we get info from the database
            mReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        points = (Long) map.get("points");
                        username = (String) map.get("username");
                        gender = Gender.valueOfLabel((String) map.get("gender"));
//                        avatar = (Avatar) map.get("avatar");
//                        items = (ArrayList<Item>) map.get("items");

                        initialized = true;
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // TODO make this more sensible
                    Log.d("GET_FIRESTORE_REFERENCE", "onFailure: could not get firestore reference");
                }
            });
        }
    }

    public Long getPoints() {
        return points;
    }

    public String getUsername() {
        return username;
    }

    public Gender getGender() {
        return gender;
    }

    public DocumentReference getmReference() {
        return mReference;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void updateUser(FirebaseUser mUser, FirebaseFirestore db, String username, Gender gender) {
        prepareDatabase(mUser, db);
        // we set info to the database
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username); // key, value
        points = points == null ? 0: points;
        map.put("points", points);
        map.put("gender", gender.toString());
//        map.put("avatar", new Avatar());
//        map.put("items", new ArrayList<Item>());
        mReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("USER_UPDATE:", "User updated successfully");
                }
                else {
                    Log.d("USER_UPDATE:", "Could not update user");
                }
            }
        });
    }

    private void prepareDatabase(FirebaseUser mUser, FirebaseFirestore db) {
        mReference = db.collection("users").document(mUser.getUid());
    }

}
