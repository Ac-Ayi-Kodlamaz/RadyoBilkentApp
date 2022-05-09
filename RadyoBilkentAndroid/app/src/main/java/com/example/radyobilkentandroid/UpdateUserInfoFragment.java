package com.example.radyobilkentandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

import data.Data;
import model.Gender;
import model.User;

public class UpdateUserInfoFragment extends Fragment {

    private static final String MUSER_KEY = "mUser_key";
    private static final String DATA_KEY = "data_key";

    private FirebaseUser mUser;
    private Data data;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private EditText username;
    private Spinner genderSpinner;
    private Button confirm;

    public UpdateUserInfoFragment() {
        // Required empty public constructor
    }

    public static UpdateUserInfoFragment newInstance(FirebaseUser mUser, Data data) {
        UpdateUserInfoFragment fragment = new UpdateUserInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(MUSER_KEY, (Serializable) mUser);
        args.putSerializable(DATA_KEY, (Serializable) data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (FirebaseUser) getArguments().getSerializable(MUSER_KEY);
            data = (Data) getArguments().getSerializable(DATA_KEY);
        }
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDB = FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = view.findViewById(R.id.username_field);
        genderSpinner = view.findViewById(R.id.gender_spinner);
        confirm = view.findViewById(R.id.confirm_button);

        Toast.makeText(getContext(), "1234567", Toast.LENGTH_SHORT).show();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtUsername = username.getText().toString();
                Gender gender = Gender.valueOfLabel((String) genderSpinner.getSelectedItem());
                User user = new User(mDB,mUser);
                user.updateUser(mUser, mDB, txtUsername, gender);
                // TODO handle successful update and move back to the previous activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
//                getActivity().finish();

            }
        });
    }
}