package com.example.radyobilkentandroid;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import model.Content;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PodcastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PodcastFragment extends Fragment {

    private static final String IMAGE_KEY = "IMAGE_KEY";
    private static final String TITLE_KEY = "TITLE_KEY";
    private static final String CREATOR_KEY = "CREATOR_KEY";
    private static final String DATE_KEY = "DATE_KEY";
    private static final String DURATION_KEY = "DURATION_KEY";
    private static final String CONSUMPTION_KEY = "CONSUMPTION_KEY";
    private static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";
    private static final String URL_KEY = "URL_KEY";
    private static final String POSITION_KEY = "POSITION_KEY";

    private ImageView coverImage;
    private TextView podcastTitle;
    private TextView podcastDuration;
    private TextView podcastCreator;
    private TextView podcastTimesConsumed;
    private TextView podcastDate;
    private TextView podcastDescription;
    private Button spotifyButton;

    private String coverImageURL;
    private String title;
    private String creator;
    private String date;
    private Long duration;
    private Long timesConsumed;
    private String description;
    private String url;
    private int position;

    private Bitmap bmCover;

    private boolean spotifyFound;

    public PodcastFragment() {
        // Required empty public constructor
    }

    public static PodcastFragment newInstance(String coverImageURL, String title, String creator, Date date, Long duration, Long timesConsumed, String description, String url, int position) {
        PodcastFragment fragment = new PodcastFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_KEY, coverImageURL);
        args.putString(TITLE_KEY, title);
        args.putString(CREATOR_KEY, creator);
        DateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
        args.putString(DATE_KEY, dateFormat.format(date));
        args.putLong(DURATION_KEY, duration);
        args.putLong(CONSUMPTION_KEY, timesConsumed);
        args.putString(DESCRIPTION_KEY, description);
        args.putString(URL_KEY, url);
        args.putInt(POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            coverImageURL = getArguments().getString(IMAGE_KEY);
            title = getArguments().getString(TITLE_KEY);
            creator = getArguments().getString(CREATOR_KEY);
            date = getArguments().getString(DATE_KEY);
            duration = getArguments().getLong(DURATION_KEY);
            timesConsumed = getArguments().getLong(CONSUMPTION_KEY);
            description = getArguments().getString(DESCRIPTION_KEY);
            url = getArguments().getString(URL_KEY);
            position = getArguments().getInt(POSITION_KEY);
            bmCover = Content.downloadImage(coverImageURL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_podcast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spotifyFound = isSpotifyInstalled();

        podcastTitle = view.findViewById(R.id.podcast_title_text);
        podcastCreator = view.findViewById(R.id.podcast_creator_text);
        podcastDate = view.findViewById(R.id.podcast_date_text);
        podcastDuration = view.findViewById(R.id.podcast_duration_text);
        podcastTimesConsumed = view.findViewById(R.id.podcast_times_consumed_text);
        podcastDescription = view.findViewById(R.id.podcast_description);
        spotifyButton = view.findViewById(R.id.podcast_spotify_button);

        DownloadImageTask dit = new DownloadImageTask();
        dit.execute(coverImageURL);
        setupView();
    }

    private void setupView() {
        podcastTitle.setText(title);
        podcastCreator.setText(creator);
        podcastDate.setText(date);
        podcastDuration.setText(String.format("%d:%02d:%02d", duration / 3600, (duration % 3600) / 60, (duration % 60)));
        podcastTimesConsumed.setText(timesConsumed + " listeners");
        podcastDescription.setText(description);
        if (spotifyFound) {
            spotifyButton.setText(R.string.spotify_found);
        }
        else {
            spotifyButton.setText(R.string.spotify_missing);
        }
        spotifyButton.setOnClickListener(new SpotifyListener());
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
            coverImage = getView().findViewById(R.id.podcast_cover);
            coverImage.setImageBitmap(bmCover);
        }

    }

    class SpotifyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            openInSpotify(url);
        }
    }

    private boolean isSpotifyInstalled() {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo("com.spotify.music", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void openInSpotify(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (spotifyFound) {
            PodcastActivity.podcasts.get(position).consume();
            //intent.setData(Uri.parse("spotify:album:0sNOF9WDwhWunNAHPD3Baj"));
            String linkSuffix = processLink(link);
            intent.setData(Uri.parse("spotify:episode:" + linkSuffix));
            intent.putExtra(Intent.EXTRA_REFERRER,
                    Uri.parse("android-app://" + getActivity().getPackageName()));
            Log.d("OPEN IN SPOTIFY: ", "open spotify attempt");
        }
        else {
            intent.setData(Uri.parse("market://details?id=com.spotify.music"));
            Log.d("OPEN IN SPOTIFY: ", "get spotify attempt");
        }
        startActivity(intent);
    }

    // https://open.spotify.com/episode/6RT8FPKtvd4Bm3KjbFKQTP?si=db42c2c981d54342&nd=1
    private String processLink(String link) {
        int index = link.lastIndexOf('/');
        return link.substring(index + 1);
    }

}