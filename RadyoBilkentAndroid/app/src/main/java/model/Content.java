package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public abstract class Content
{

    protected String title;
    protected String coverImageURL;
    protected String url;
    protected Bitmap cover;
    protected Date date;
    protected Long duration;
    protected String creator;
    protected Long timesConsumed;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImageURL() {
        return coverImageURL;
    }

    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getTimesConsumed() {
        return timesConsumed;
    }

    public void setTimesConsumed(Long timesConsumed) {
        this.timesConsumed = timesConsumed;
    }

    public void consume() {
        timesConsumed++;
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

    public void downloadImage() {
        new DownloadImageTask();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            cover = result;
        }
    }

}