package com.example.radyobilkentandroid;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

import model.Gender;
import model.User;

public class TopBarFragment extends Fragment {

    private ImageView profilePic;
    private TextView username;
    private TextView pointText;
    public ImageButton hamburgerMenuButton;

    private String strUsername;
    private Long lngPoints;
    private boolean navDrawerOpen;

    private CountDownTimer timer;

    public TopBarFragment() {
        // Required empty public constructor
        navDrawerOpen = false;
    }

    public static TopBarFragment newInstance() {
        TopBarFragment fragment = new TopBarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profilePic = view.findViewById(R.id.top_bar_avatar);
        username = view.findViewById(R.id.top_bar_username);
        pointText = view.findViewById(R.id.top_bar_points);
        hamburgerMenuButton = view.findViewById(R.id.hamburger_button);
        profilePic.setImageResource(R.drawable.nice_avatar);

        // TODO use one of them to see the Avatar in the top right corner
//        profilePic.setImageURI();
//        profilePic.setImageDrawable();
//        profilePic.setImageIcon();
//        profilePic.setImageResource();
//        profilePic.setImageBitmap();
        timer = new CountDownTimer(100000000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                changeTexts();
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();



        hamburgerMenuButton.setOnClickListener(new HamburgerListener());
    }

    class HamburgerListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Activity activity = TopBarFragment.this.getActivity();
            MainActivity mainActivity = (MainActivity) activity;

            //TODO
            // nawDrawer is now navigationView

            if (mainActivity != null) {
                if (mainActivity.navDrawer.isOpen()) {
                    mainActivity.navDrawer.closeDrawer(GravityCompat.START, true);
                } else {
                    mainActivity.navDrawer.openDrawer(GravityCompat.START, true);
                }
            }
            else {
                Log.d("HAMBURGER MENU CLICK: ", "mainActivity is null");
            }
        }
    }

    private void changeTexts(){
        MainActivity.user.getmReference().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Map<String, Object> map = documentSnapshot.getData();
                    lngPoints = (Long) map.get("points");
                    strUsername = (String) map.get("username");

                    username.setText(strUsername);
                    pointText.setText(lngPoints.toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // TODO make this more sensible
                Log.d("GET_FIRESTORE_REFERENCE", "onFailure: could not get firestore reference");
            }
        });
    }
}