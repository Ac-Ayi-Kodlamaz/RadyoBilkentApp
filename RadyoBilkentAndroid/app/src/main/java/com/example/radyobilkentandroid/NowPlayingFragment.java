package com.example.radyobilkentandroid;

import android.media.AudioManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.media.MediaPlayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String url;

    private Button start;
    private Button stop;
    private CustomMediaPlayer customMediaPlayer;

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        TextView tv = (TextView) getView().findViewById(R.id.songName);

        tv.setText(mParam2);
        url = mParam1; // your URL here
        customMediaPlayer = new CustomMediaPlayer ();
        customMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            customMediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            customMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        start = (Button) getView().findViewById(R.id.startButton);
        stop = (Button) getView().findViewById(R.id.stopButton);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customMediaPlayer.start();
                start.setAlpha(0.5f);
                stop.setAlpha(1f);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customMediaPlayer.pause();
                start.setAlpha(1f);
                stop.setAlpha(0.5f);
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }


    private class CustomMediaPlayer extends MediaPlayer{

        String dataSource;

        @Override
        public void setDataSource(String path) throws IOException,IllegalArgumentException, SecurityException, IllegalStateException{
            super.setDataSource(path);
            dataSource = path;
        }

        public String getDataSource(){return dataSource;}
    }
}