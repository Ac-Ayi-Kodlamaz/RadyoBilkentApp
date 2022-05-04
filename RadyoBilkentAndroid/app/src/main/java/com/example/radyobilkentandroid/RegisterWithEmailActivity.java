package com.example.radyobilkentandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import model.Gender;
import model.User;

public class RegisterWithEmailActivity extends AppCompatActivity {

    private EditText email;
    private EditText username;
    private EditText password;
    private Spinner genders;
    private Button button;

    private User user;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_with_email);

        email = findViewById(R.id.email_field);
        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
        genders = findViewById(R.id.gender_spinner);
        button = findViewById(R.id.register_button);

        firebaseAuth = FirebaseAuth.getInstance();

        user = new User();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail = email.getText().toString();
                String txtUsername = username.getText().toString();
                String txtPassword = password.getText().toString();
                Gender gender = Gender.valueOfLabel((String) genders.getSelectedItem());

                //TODO check for email & password validity
                user.registerUser(txtEmail, txtPassword, txtUsername, gender);
                // TODO check success
                startActivity(new Intent(RegisterWithEmailActivity.this, MainActivity.class));


            }
        });
    }

}