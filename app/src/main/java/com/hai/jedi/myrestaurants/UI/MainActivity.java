package com.hai.jedi.myrestaurants.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.util.Log;

import android.widget.TextView;

import android.graphics.Typeface;

import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

// We implement the built in OnClickListener here
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Firebase manenos
    private DatabaseReference mSearchedLocationsReference;

    // Had not seen this. Had to initialize tag here.
    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView( R.id.sample_text) TextView welcome_text;

    @BindView(R.id.findRestaurantsButton) Button mFindRestaurantsButton;

    @BindView(R.id.editLocationText) EditText mLocationEditText;

    // What we will use to persist primitive data to phone memory.
    // Variable to reference the the shared preference tool itself
    private SharedPreferences mSharedPreferences;
    // Dedicated tool to edit the shared preferences
    private SharedPreferences.Editor mEditor;


    // Used to load the 'native-lib' library on application startup.
    protected void loadNativeLibraries() {
        try {
            System.loadLibrary("native-lib");
        } catch (UnsatisfiedLinkError error){
            String error_string = "STILL HAVE THIS ERROR: " + error;
            Log.d(TAG, error_string);
        }
    }

    @SuppressLint( "CommitPrefEdits" )
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // We configure our database first
        mSearchedLocationsReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);
        // Run default behaviours for an activity.
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

       ButterKnife.bind(this);

       mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
       mEditor = mSharedPreferences.edit();

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

            if(!(location).equals("")){
                // addToSharedPreferences(location);
                // We call Firebase method instead to save location for now.
                saveLocationToFirebase(location);
            }
            Intent intent = new Intent(MainActivity.this, RestaurantsListActivity.class);
            intent.putExtra("location_data", location);
            startActivity(intent);
        }
    }

    // We define this method which calls upon the editor to write user's location input to
    // shared preferences.
    private void addToSharedPreferences(String location) {
        // We call the apply method to persist the location info in a key - value pair
        mEditor.putString(Constants.PREFERENCE_LOCATION_KEY, location).apply();
    }

    // For practice purposes we will test out firebase with persisting location data
    public void saveLocationToFirebase(String location){
        mSearchedLocationsReference.push().setValue(location);
    }
}
