package com.example.radyobilkentandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Blog;
import model.Content;

public class BlogFragment extends Fragment {

    private static final String IMAGE_KEY = "IMAGE_KEY";
    private static final String TITLE_KEY = "TITLE_KEY";
    private static final String CREATOR_KEY = "CREATOR_KEY";
    private static final String DATE_KEY = "DATE_KEY";
    private static final String DURATION_KEY = "DURATION_KEY";
    private static final String CONSUMPTION_KEY = "CONSUMPTION_KEY";
    private static final String CONTENT_KEY = "CONTENT_KEY";

    private ImageView coverImage;
    private TextView blogTitle;
    private TextView blogDuration;
    private TextView blogCreator;
    private TextView blogTimesConsumed;
    private TextView blogDate;
    private TextView blogContent;

    private String urlImage;
    private String strTitle;
    private String strCreator;
    private String strDate;
    private Long lngDuration;
    private Long lngTimesConsumed;
    private String strContent;

    private Bitmap bmCover;

    public BlogFragment() {
    }


    public static BlogFragment newInstance(String imageURL, String title, String creator, Date date, Long duration, Long lngTimesConsumed, String content) {
        BlogFragment fragment = new BlogFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_KEY, imageURL);
        args.putString(TITLE_KEY, title);
        args.putString(CREATOR_KEY, creator);
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        args.putString(DATE_KEY, dateFormat.format(date));
        args.putLong(DURATION_KEY, duration);
        args.putLong(CONSUMPTION_KEY, lngTimesConsumed);
        args.putString(CONTENT_KEY, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            urlImage = getArguments().getString(IMAGE_KEY);
            strTitle = getArguments().getString(TITLE_KEY);
            strCreator = getArguments().getString(CREATOR_KEY);
            strDate = getArguments().getString(DATE_KEY);
            lngDuration = getArguments().getLong(DURATION_KEY);
            lngTimesConsumed = getArguments().getLong(CONSUMPTION_KEY);
            strContent = getArguments().getString(CONTENT_KEY);
            bmCover = Content.downloadImage(urlImage);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        coverImage = view.findViewById(R.id.blog_cover);
        blogTitle = view.findViewById(R.id.blog_title_text);
        blogCreator = view.findViewById(R.id.blog_creator_text);
        blogDate = view.findViewById(R.id.blog_date_text);
        blogDuration = view.findViewById(R.id.blog_duration_text);
        blogTimesConsumed = view.findViewById(R.id.blog_times_consumed_text);
        blogContent = view.findViewById(R.id.blog_content);

        DownloadImageTask dit = new DownloadImageTask();
        dit.execute(urlImage);
    }

    private void setupView() {
        coverImage.setImageBitmap(bmCover);
        blogTitle.setText(strTitle);
        blogCreator.setText(strCreator);
        blogDate.setText(strDate);
        blogDuration.setText(lngDuration + " mins");
        blogTimesConsumed.setText(lngTimesConsumed + " readers");
        blogContent.setText(strContent);
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
            bmCover = result;
            setupView();
        }

    }

}