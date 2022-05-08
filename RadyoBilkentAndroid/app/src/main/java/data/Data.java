package data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

import model.User;

public class Data {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    static private User user;

    public Data () {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public FirebaseFirestore getDB() {
        return db;
    }

    public User getUser() {
        return user;
    }

    public void setUser(FirebaseUser fUser) {
        user = new User(db, fUser);
    }

}
