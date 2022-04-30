package com.example.adminpanel;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class demo implements Storable<demo> {
    public String name;
    public int hi;
    public String color;

    public demo(String name, int hi, String color) {
        this.name = name;
        this.hi = hi;
        this.color = color;
    }

    public demo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHi() {
        return hi;
    }

    public void setHi(int hi) {
        this.hi = hi;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void addDoc() {
        DocumentReference reference = db.collection("demo").document(this.name);
        Map<String, Object> map = new HashMap<>();
        for(Field field : demo.class.getDeclaredFields())
        {
            try {
                map.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        /*Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("hi", hi);
        map.put("color", color);*/
        ApiFuture<WriteResult> result = reference.set(map);
        try {
            System.out.println("update time: " + result.get().getUpdateTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uptade(int point) {

    }

    @Override
    public void deleteDoc() {

    }

    @Override
    public String toString() {
        return "demo{" +
                "name='" + name + '\'' +
                ", hi=" + hi +
                ", Color='" + color + '\'' +
                '}';
    }
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
    @Override
    public ArrayList<demo> getData() {
        ArrayList<demo> result = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = db.collection("demo").get();
        try {
            List<QueryDocumentSnapshot> query = querySnapshot.get().getDocuments();
            for(QueryDocumentSnapshot q: query)
            {
                result.add(q.toObject(demo.class));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
}

