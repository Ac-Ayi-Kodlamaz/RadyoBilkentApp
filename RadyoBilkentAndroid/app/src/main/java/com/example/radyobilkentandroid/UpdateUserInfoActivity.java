package com.example.radyobilkentandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import data.Data;

public class UpdateUserInfoActivity extends AppCompatActivity {

    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CEVATERCAL", "leblebi");
        setContentView(R.layout.activity_update_user_info);
        data = (Data) getIntent().getParcelableExtra("data_key");
        Log.d("DATA ALDIM:", data.getmAuth().getUid());

    }

}