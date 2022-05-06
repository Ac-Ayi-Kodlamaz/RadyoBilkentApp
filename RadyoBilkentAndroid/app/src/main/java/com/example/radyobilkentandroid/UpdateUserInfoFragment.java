package com.example.radyobilkentandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

import data.Data;
import model.Gender;

public class UpdateUserInfoFragment extends Fragment {

    private static final String MUSER_KEY = "mUser_key";
    private static final String DATA_KEY = "data_key";

    private FirebaseUser mUser;
    private Data data;

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

        username = getActivity().findViewById(R.id.username_field);
        genderSpinner = getActivity().findViewById(R.id.gender_spinner);
        confirm = getActivity().findViewById(R.id.confirm_button);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtUsername = username.getText().toString();
                Gender gender = Gender.valueOfLabel((String) genderSpinner.getSelectedItem());
                if (data.getUser().updateUser(mUser, data.getDB(), txtUsername, gender)) {
                    // TODO handle successful update and move back to the previous activity
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_user_info, container, false);
    }

    private void setUser(FirebaseUser mUser) {
        this.setUser(mUser);
    }

}