package com.example.radyobilkentandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.io.Serializable;

import data.Data;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    private EditText email;
    private EditText password;
    private EditText passwordConfirm;
    private Button signInButton;
    private ImageButton googleSignInButton;

    private static final int RC_SIGN_IN = 5;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        mAuth = FirebaseAuth.getInstance();
        createRequest();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.email_field);
        password = view.findViewById(R.id.password_field);
        passwordConfirm = view.findViewById(R.id.password_confirmation_field);
        signInButton = view.findViewById(R.id.sign_in_button);
        googleSignInButton = view.findViewById(R.id.google_sign_in);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
                String txtConfirmation = passwordConfirm.getText().toString();

                if (validateInput(txtEmail, txtPassword, txtConfirmation)) {
                    mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser mUser = mAuth.getCurrentUser();
                                Toast.makeText(SignInFragment.this.getContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                signedIn(getView(), mUser);
                            }
                            else {
                                Toast.makeText(SignInFragment.this.getContext(), "Could not register. Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this is deprecated but hey ho
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
    }


    private void signedIn(View view, FirebaseUser mUser) {
        Log.d("SIGN IN BUTTON", "annen");
        //Data data = new Data();
        //data.setUser(mUser);
        Intent intent = new Intent(getActivity(), UpdateUserInfoActivity.class);
        //intent.putExtra("data_key", data);
        startActivity(intent);
        getActivity().finish();

//        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        UpdateUserInfoFragment fragment = UpdateUserInfoFragment.newInstance(mUser, data);
//        ft.replace(R.id.register_pager, fragment);
//        ft.commit();

//        Navigation.findNavController(view).navigate(R.id.fragment_update_user_info);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);
            }
            catch (Exception e) {

            }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            signedIn(getView(), mUser);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInFragment.this.getContext(), "Google Sign In Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void createRequest() {
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    private boolean validateInput(String txtEmail, String txtPassword, String txtConfirmation) {
        // email address cannot contain space
        if (txtEmail.contains(" "))
            return false;

        int indexOfAt = txtEmail.indexOf('@');
        // email address must contain @ and a recipient name
        if (indexOfAt < 1 || indexOfAt < txtEmail.lastIndexOf('@'))
            return false;
        int lastIndexOfDot = txtEmail.lastIndexOf('.');
        // email address must contain a top-level domain
        if (lastIndexOfDot < indexOfAt)
            return false;
        int indexOfDomainDot = txtEmail.indexOf('.', indexOfAt);
        // email address must contain a domain
        if (indexOfDomainDot - indexOfAt < 2)
            return false;
        // password must be at least 6 and at most 20 characters long
        if (txtPassword.length() < 6 || txtPassword.length() > 20)
            return false;
        // passwords do not match
        return txtPassword.equals(txtConfirmation);
    }

}