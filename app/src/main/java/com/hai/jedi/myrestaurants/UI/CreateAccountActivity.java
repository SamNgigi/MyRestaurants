package com.hai.jedi.myrestaurants.UI;

import com.hai.jedi.myrestaurants.R;

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

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{
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

        mLoginHere.setText(Html.fromHtml(getString(R.string.login_here)));

        mCreateUserButton.setOnClickListener(this);
        mLoginHere.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == mCreateUserButton){
            // createNewUser();
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
}
