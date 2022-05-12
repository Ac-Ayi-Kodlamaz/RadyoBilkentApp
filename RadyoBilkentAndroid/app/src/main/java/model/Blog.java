package model;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Blog extends Content {

    private final static String BLOG_KEY = "blogs";

    public Blog() {
        key = BLOG_KEY;
    }

    protected String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

