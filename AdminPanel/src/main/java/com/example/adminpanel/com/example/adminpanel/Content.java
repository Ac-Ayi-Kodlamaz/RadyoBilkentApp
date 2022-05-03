package com.example.adminpanel.com.example.adminpanel;

import com.example.adminpanel.com.example.adminpanel.Storable;

import java.awt.*;
import java.net.URL;
import java.time.Duration;
import java.util.Date;

public abstract class Content implements Storable<Content>
{

    public String title;
    public Image cover;
    public Date date;
    public Duration duration;
    public String creator;
    public int timesConsumed;
    public URL link;

    //constructors
    //default constuctor


    public Content() {
    }

    public Content(String title, Image cover, Date date, Duration duration, String creator, int timesConsumed, URL link) {
        this.title = title;
        this.cover = cover;
        this.date = date;
        this.duration = duration;
        this.creator = creator;
        this.timesConsumed = timesConsumed;
        this.link = link;
    }
    //setters and getters


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getTimesConsumed() {
        return timesConsumed;
    }

    public void setTimesConsumed(int timesConsumed) {
        this.timesConsumed = timesConsumed;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    /**
     * Sharing the original link of the consumed content to the social media applications such as WhatsApp and Twitter.
     * @return a boolean type for success feedback
     */
    public boolean share()
    {
        //TODO
        return false;
    }
}
