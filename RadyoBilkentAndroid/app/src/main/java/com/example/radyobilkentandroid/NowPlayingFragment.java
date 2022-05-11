package com.example.radyobilkentandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.media.MediaPlayer;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import model.Gender;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private boolean isPlaying = false;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    private String url;

    private Button start;

    private CustomMediaPlayer customMediaPlayer;
    private CountDownTimer timer;

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
    public static NowPlayingFragment newInstance(String param1, String param2, String param3) {
        NowPlayingFragment fragment = new NowPlayingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }
    //TODO
    // change fragment when url is changed

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //text
        TextView tv = (TextView) getView().findViewById(R.id.songName);
        tv.setText(mParam2);

        //image
        String imageURL = mParam3;
        ImageView image = (ImageView) getView().findViewById(R.id.songImage);
        new DownloadImageTask(image).execute(imageURL);
        //boolean[] isPlaying = {false};
        timer = new CountDownTimer(300000,1000){

            // for incrementing score
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser mUser = mAuth.getCurrentUser();
            FirebaseFirestore mDB = FirebaseFirestore.getInstance();

            DocumentReference mReference = mDB.collection("users").document(mUser.getUid());


            @Override
            public void onTick(long millisUntilFinished) {

                if(isPlaying){
                mReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists() && isPlaying) {

                            Map<String, Object> map = documentSnapshot.getData();
                            Long points = (Long) map.get("points");
                            String username = (String) map.get("username");
                            //TODO Gender burada string olunca DNWTD oluyor bu da enumda değer bulamayınca
                            //TODO null gönderiyor user düzelticek
                            //Gender gender = Gender.valueOfLabel("Do not wish to disclose");
                            Gender gender = Gender.valueOfLabel((String) map.get("gender"));


                            //TODO increment count
                            points += 10;
                            map.put("points",points);
                            mReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("USER_UPDATE:", "User updated successfully");
                                    }
                                    else {
                                        Log.d("USER_UPDATE:", "Could not update user");
                                    }
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // TODO make this more sensible
                        Log.d("GET_FIRESTORE_REFERENCE", "onFailure: could not get firestore reference");
                    }
                });}
            }


            @Override
            public void onFinish() {

            }

        };
        timer.start();

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

        customMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                customMediaPlayer.start();
                play();
            }
        });

        start = (Button) getView().findViewById(R.id.startButton);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isPlaying) {
                    play();
                }
                else{
                    pause();
                }
            }
        });

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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        customMediaPlayer.stop();
        timer.cancel();
    }

    private void play(){
        isPlaying = true;
        customMediaPlayer.start();
        customMediaPlayer.setVolume(1f,1f);
        start.setBackgroundResource(R.drawable.pause_icon);
    }

    private void pause() {
        isPlaying = false;
        customMediaPlayer.setVolume(0f,0f);
        customMediaPlayer.pause();
        start.setBackgroundResource(R.drawable.start_icon);
    }
}