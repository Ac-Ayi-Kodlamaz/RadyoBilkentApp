package com.example.radyobilkentandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {
    private Button feedbackButton;
    private EditText feedbackEdit;
    private EditText feedbackTitle;
    private static int id = 0;

    private FirebaseFirestore mDB;
    private FirebaseAuth mAuth;
    private DocumentReference mReference;
    private CollectionReference cReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedbackButton = findViewById(R.id.feedback_button);
        feedbackEdit = findViewById(R.id.feedback_editText);
        feedbackTitle = findViewById(R.id.feedbackTitle);
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();
        id += (int) (Math.random() * 1000);

        cReference = mDB.collection("feedbacks");
        mReference = mDB.collection("feedbacks").document(id + "" + mAuth.getCurrentUser().getUid());

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = feedbackTitle.getText().toString();
                String content = feedbackEdit.getText().toString();

                if(title.equals("") || content.equals("")){
                    Toast.makeText(FeedbackActivity.this, "Please enter valid texts", Toast.LENGTH_SHORT).show();
                }
                else{
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("Title", title);
                    map.put("Content",content);
                    map.put("User", mAuth.getCurrentUser().getUid());
                    Toast.makeText(FeedbackActivity.this, "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                    cReference.add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Log.d("USER_UPDATE:", "User updated successfully");
                            FeedbackActivity.this.finish();
                        }
                    });

                }
            }
        });




    }
}