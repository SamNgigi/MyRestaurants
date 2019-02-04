package com.hai.jedi.myrestaurants.UI;

import com.google.android.gms.tasks.OnCompleteListener;
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
}
