package com.hai.jedi.myrestaurants.UI;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.util.Log;

import android.widget.TextView;

import android.graphics.Typeface;

import com.hai.jedi.myrestaurants.R;

import butterknife.BindView;
import butterknife.ButterKnife;

// We implement the built in OnClickListener here
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Had not seen this. Had to initialize tag here.
    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView( R.id.sample_text) TextView welcome_text;

    @BindView(R.id.findRestaurantsButton) Button mFindRestaurantsButton;

    @BindView(R.id.editLocationText) EditText mLocationEditText;

    // Used to load the 'native-lib' library on application startup.
    protected void loadNativeLibraries() {
        try {
            System.loadLibrary("native-lib");
        } catch (UnsatisfiedLinkError error){
            String error_string = "STILL HAVE THIS ERROR: " + error;
            Log.d(TAG, error_string);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Run default behaviours for an activity.
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

       ButterKnife.bind(this);

        // Getting our font
        Typeface ostrich_font = Typeface.createFromAsset(
                               getAssets(), "fonts/ostrich-regular.ttf");
        // Setting our font at runtime.
        welcome_text.setTypeface(ostrich_font);

        // We listen for a click then implement View.OnClickListener's onClick() method.
        mFindRestaurantsButton.setOnClickListener(this);

    }

    // Implementing the View.OnClickListener onClick() method for our navigating to other activity
    @Override
    public void onClick(View v){
        // We check which view has been clicked and take action accordingly.
        if(v == mFindRestaurantsButton) {
            String location = mLocationEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, RestaurantsActivity.class);
            intent.putExtra("location_data", location);
            startActivity(intent);
        }
    }

}
