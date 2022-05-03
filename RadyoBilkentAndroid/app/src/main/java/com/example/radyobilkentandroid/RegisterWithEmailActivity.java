package com.example.radyobilkentandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterWithEmailActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button button;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_with_email);

        email = findViewById(R.id.email_field);
        password = findViewById(R.id.password_field);
        button = findViewById(R.id.register_button);

        firebaseAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();

                //TODO check for email & password validity
                registerUser(txtEmail, txtPassword);

            }
        });
    }

    private void registerUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterWithEmailActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterWithEmailActivity.this, "Signed in new user", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterWithEmailActivity.this, "Sign in attempt unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}