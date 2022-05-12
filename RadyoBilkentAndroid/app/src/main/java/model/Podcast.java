package model;

import java.util.HashMap;

public class Podcast extends Content
{

    private final static String PODCAST_KEY = "podcasts";

    public Podcast() {
        key = PODCAST_KEY;
    }

    protected String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
