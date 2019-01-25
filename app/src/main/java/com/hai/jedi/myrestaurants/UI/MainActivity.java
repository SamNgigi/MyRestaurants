package com.hai.jedi.myrestaurants.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Objects;

// We implement the built in OnClickListener here
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Firebase manenos
    private DatabaseReference mSearchedLocationsReference;
     private ValueEventListener mSearchedLocationReferenceListener;

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

        // We assign the listening method to a listener var .
        // We will use this to  destroy it when ou app exits.
       mSearchedLocationReferenceListener = mSearchedLocationsReference.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                /**
                 * Called whenever data at the specific note ( searchedLocation) changes
                 * such as when we add a new location.
                 * It will return a new dataSnapshot - a read-only copy of the Firebase state.
                 * */
                for(DataSnapshot locationSnapShot : dataSnapshot.getChildren()){
                    /**
                     * We loop through all the locations stored on the searchLocation node by calling
                     * the getChildren() method on the dataSnapshot of the node
                     *
                     * We then get the value of each child node.*/

                    String location = Objects.requireNonNull(locationSnapShot.getValue()).toString();
                    Log.d("Locations updated ->", "LOCATION: " + location);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){
                /**
                 * Called if the listener is unsuccessful for any reason.
                 * */
                // Do something
            }
        });

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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        // We call the removeEventListener method on the DatabaseReference
        // We then pass the ValueEventListener instance mSearchedLocation.
        mSearchedLocationsReference.removeEventListener(mSearchedLocationReferenceListener);
    }
}
