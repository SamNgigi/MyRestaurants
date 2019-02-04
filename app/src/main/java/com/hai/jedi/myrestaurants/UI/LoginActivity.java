package com.hai.jedi.myrestaurants.UI;

import com.hai.jedi.myrestaurants.R;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.registerHere) TextView signUpHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        signUpHere.setText(Html.fromHtml(getString(R.string.register_here)));

        signUpHere.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if (view == signUpHere){
            Toast.makeText(
                    LoginActivity.this,
                    "Working!!",
                    Toast.LENGTH_LONG)
                .show();
        }
    }
}
