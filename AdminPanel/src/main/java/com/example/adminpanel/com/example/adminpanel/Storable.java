package com.example.adminpanel.com.example.adminpanel;

/**
 * this interface provides acces to FireStore Database. Datas can be read written and updated
 * also with static db variable one can acces db reference for other purposes
 */

import com.google.cloud.firestore.Firestore;

import java.util.ArrayList;

public interface Storable<T> {
    static FireBaseService fs = new FireBaseService();
    static Firestore db =  fs.getDb();
    void addDoc();
    void update(int point);
    void deleteDoc();
    ArrayList<T> getData();
    /*
MyObject obj = new MyObject();
obj.myInt = 1; obj.myString = "string";
Map<String, Object> map = new HashMap<>();
// Use MyObject.class.getFields() instead of getDeclaredFields()
// If you are interested in public fields only
for (Field field : MyObject.class.getDeclaredFields()) {
    // Skip this if you intend to access to public fields only
    if (!field.isAccessible()) {
        field.setAccessible(true);
    }
    map.put(field.getName(), field.get(obj));
}
System.out.println(map);
 */
}
