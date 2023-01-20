package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

public abstract class Content
{

    protected FirebaseFirestore db;
    protected DocumentReference mReference;
    protected boolean referenceReady;

    protected String key;
    protected String title;
    protected String coverImageURL;
    protected String url;
    protected Date date;
    protected Long duration;
    protected String creator;
    protected Long timesConsumed;

    public Content (){
        db = FirebaseFirestore.getInstance();
    }

    public void updateDatabase() {
        if (!referenceReady) {
            mReference = db.collection(key).document(title);
            referenceReady = true;
        }
        mReference.update("timesConsumed", timesConsumed);
    }

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
        updateDatabase();
    }

    public static Bitmap downloadImage(String url) {
        DownloadImageTask dit = new DownloadImageTask();
        dit.execute(url);
        return dit.getBmImage();
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        Bitmap bmImage;

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
            bmImage = result;
        }

        public Bitmap getBmImage() {
            return bmImage;
        }
    }

}