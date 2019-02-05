package com.hai.jedi.myrestaurants.UI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.hai.jedi.myrestaurants.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = CreateAccountActivity.class.getSimpleName();

    @BindView(R.id.signUpButton) Button mCreateUserButton;
    @BindView(R.id.nameEditText) EditText mNameEditText;
    @BindView(R.id.emailEditText) EditText mEmailEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
    @BindView(R.id.loginHere) TextView mLoginHere;

    private FirebaseAuth mAuth;
    // Add an mAuthListener member var
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog mAuthProgressDialog;

    private String mName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mLoginHere.setText(Html.fromHtml(getString(R.string.login_here)));

        createAuthStateListener();
        createAuthProgressDialog();

        mLoginHere.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);

    }

    // Progress animation
    public void createAuthProgressDialog(){
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
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

    private void createAuthStateListener () {
        // This listens to changes in the current AuthState.
        // Whenever there is a change (i.e a user is authenticated or signs out)
        // this interfaces triggers the onAuthStateChanged() method.
        // This method returns FirebaseAuth data
        mAuthListener = firebaseAuth -> {
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
        };
    }

    @Override
    public void onClick(View view){
        if(view == mLoginHere){
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if(view == mCreateUserButton){
            createNewUser();
        }
    }


    private void createNewUser(){
        mName = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(mName);
        boolean validPassword = isValidPassword(password, confirmPassword);
        // Not exactly how below code would work.
        if(!validEmail || !validName || !validPassword) return;

        // Showing the progress dialog
        mAuthProgressDialog.show();

        // Firebase method to create a new user account in Firebase passing user's
        // password and email
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task){

                                // Dismissing the progress dialog
                                mAuthProgressDialog.dismiss();

                                if (task.isSuccessful()){
                                    Log.d(TAG, "Authentication successful");
                                    createFirebaseUserProfile(task.getResult().getUser());
                                }
                                else {
                                    Toast.makeText(
                                            CreateAccountActivity.this,
                                            "Authentication Failed.",
                                            Toast.LENGTH_LONG
                                    ).show();
                                    Log.d(TAG, "Authentication unsuccessful");
                                }
                            }
                        }
                );
    }

    public void createFirebaseUserProfile(final FirebaseUser user){
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                                                      .setDisplayName(mName)
                                                      .build();
        user.updateProfile(addProfileName)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, user.getDisplayName());
                    }
                }
            });
    }



    // VALIDATING USER EMAIL AND PASSWORD.
    private boolean isValidEmail(String email){
        boolean validEmail = (email !=null &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!validEmail){
            mEmailEditText.setError("Please enter a valid email address");
            return false;
        }
        return validEmail;
    }

    private boolean isValidName(String name){
        if(name.equals("")){
            mNameEditText.setError("Please enter you name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword){
        if(password.length() < 8){
            mPasswordEditText.setError("Please create a password containing at least 8 characters");
            return false;
        }
        else if (!password.equals(confirmPassword)){
            mPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}
