package modal;

import java.net.URL;
import java.util.Date;

public abstract class Content implements Storable<Content>
{

    public String title;
    public URL cover;
    public Date date;
    public long duration;
    public String creator;
    public int timesConsumed;
    public URL link;


    public Content() {}

    public Content(String title, URL cover, Date date, long duration, String creator, int timesConsumed, URL link) {
        this.title = title;
        this.cover = cover;
        this.date = date;
        this.duration = duration;
        this.creator = creator;
        this.timesConsumed = timesConsumed;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getCover() {
        return cover;
    }

    public void setCover(URL cover) {
        this.cover = cover;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
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

}
