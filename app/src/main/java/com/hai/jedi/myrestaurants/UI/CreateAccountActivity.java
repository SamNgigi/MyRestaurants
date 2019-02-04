package com.hai.jedi.myrestaurants.UI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.hai.jedi.myrestaurants.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.BindView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

import com.google.android.gms.tasks.Task;

import android.util.Log;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = CreateAccountActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    // Add an mAuthListener member var
    private FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.signUpButton) Button mCreateUserButton;
    @BindView(R.id.nameEditText) EditText nNameEditText;
    @BindView(R.id.emailEditText) EditText nEmailEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
    @BindView(R.id.loginHere) TextView mLoginHere;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mLoginHere.setText(Html.fromHtml(getString(R.string.login_here)));

        mCreateUserButton.setOnClickListener(this);
        mLoginHere.setOnClickListener(this);

        createAuthStateListener();
    }

    @Override
    public void onClick(View view){
        if(view == mCreateUserButton){
             createNewUser();
            Toast.makeText(
                    CreateAccountActivity.this,
                    "About to create user",
                    Toast.LENGTH_LONG
            ).show();
        }
        if(view == mLoginHere){
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);

            startActivity(intent);
            finish();
        }
    }

    private void createNewUser(){
        final String name = nNameEditText.getText().toString().trim();
        final String email = nEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();

        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        // Firebase method to create a new user account in Firebase passing user's
        // password and email
        mAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if (task.isSuccessful()){
                            Log.d(TAG, "Authentication successful");
                        }
                        else {
                            Toast.makeText(
                                    CreateAccountActivity.this,
                                    "Authentication Failed.",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
             }
         );
    }

    private void createAuthStateListener () {
        // This listens to changes in the current AuthState.
        // Whenever there is a change (i.e a user is authenticated or signs out)
        // this interfaces triggers the onAuthStateChanged() method.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            // This method returns FirebaseAuth data
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Using this data we can create a FirebaseUser by calling the get
                // current user method.
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                // Check that the above is not null
                if(user != null){
                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    @Override
    public void onStart(){
        super.onStart();
        // When we add this listener in the onStart method our app immediately brings us to the Main
        // Activity. This is because this method checks to see if a user has already been
        // authenticated.

        // Because we create a user moments ago our authStateListener brings them to the Main
        // Activity ,.
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
