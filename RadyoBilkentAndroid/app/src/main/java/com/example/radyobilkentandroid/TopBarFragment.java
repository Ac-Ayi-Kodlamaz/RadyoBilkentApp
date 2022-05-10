package com.example.radyobilkentandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;

import model.User;

public class TopBarFragment extends Fragment {

    private ImageView profilePic;
    private TextView username;
    private TextView pointText;
    private ImageButton hamburgerMenuButton;

    public TopBarFragment() {
        // Required empty public constructor
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

        // TODO use one of them to see the Avatar in the top right corner
//        profilePic.setImageURI();
//        profilePic.setImageDrawable();
//        profilePic.setImageIcon();
//        profilePic.setImageResource();
//        profilePic.setImageBitmap();

        username.setText(MainActivity.user.getUsername());
        pointText.setText(MainActivity.user.getPoints() + " points");
        hamburgerMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO hamburger menu slides in
            }
        });
    }
}