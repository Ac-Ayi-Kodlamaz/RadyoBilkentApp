package com.example.radyobilkentandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import data.Data;

public class UpdateUserInfoActivity extends AppCompatActivity {

    Data data;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_user_info);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Data data = new Data();
        data.setUser(mUser);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        UpdateUserInfoFragment fragment = UpdateUserInfoFragment.newInstance(mUser, data);
        ft.replace(R.id.update_user_info_fragment, fragment);
        ft.commit();

    }

}