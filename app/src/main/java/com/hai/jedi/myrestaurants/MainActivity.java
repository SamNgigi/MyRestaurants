package com.hai.jedi.myrestaurants;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.util.Log;

import android.widget.TextView;

import android.graphics.Typeface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    /**
     * An activity is an application component that provides a screen with which users can interact
     * with in order to do something (dial a phone, take photo, view map, send email). Each activity
     * is given a window/screen in which to draw its users interface.
     *
     * In other words an activity can be considered as a a screen of an Android app's UI. In that
     * ways it is very similar to a window in a desktop application.
     *
     * An Android app can have one or more activities meaning one or more screens.
     *
     * The MainActivity is thus the first screen to appear when an app is launched. Within the main
     * activity other activities can be launched.
     *
     * All other activities terminate in a cascade from within the main activity.
     * */


    // Had not seen this. Had to initialize tag here.
    public static final String TAG = MainActivity.class.getSimpleName();

    // Welcome text
    // private TextView welcomeText;
    @BindView(R.id.sample_text) TextView welcome_text;

    // Initializing our button
    // private Button mFindRestaurantsButton;
    @BindView(R.id.findRestaurantsButton) Button mFindRestaurantsButton;

    // Initializing our Input variable
    // private EditText mLocationEditText;
    @BindView(R.id.editLocationText) EditText mLocationEditText;

    /**
     *  Had to comment this out because of this error when doing tests
     *  "java.lang.UnsatisfiedLinkError: no native-lib in java.library.path"
     *  "java.lang.NoClassDefFoundError: Could not initialize class com.hai.jedi.myrestaurants.MainActivity"
     *
     *  Uncommented the loading of "native-lib". The errors we because we have
     *  to load the native-lib in a try/catch block for it to work.
     *  */
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
        // Method below tells activity which layout to use for main screen.
        // R is short for resources. In this case we are using activity_main.xml
        setContentView(R.layout.activity_main);


       /**
        * This:
        *  welcomeText = findViewById(R.id.sample_text);
        *  mLocationEditText = findViewById(R.id.editLocationText);
        *  mFindRestaurantsButton = findViewById(R.id.findRestaurantsButton);
        *
        * To be replaced by this
        *
        * */
       ButterKnife.bind(this);

        // Getting our font
        Typeface ostrich_font = Typeface.createFromAsset(
                               getAssets(), "fonts/ostrich-regular.ttf");
        // Setting our font at runtime.
        welcome_text.setTypeface(ostrich_font);




        // We add a listener to our button so that we can make it do something when it is clicked.
        mFindRestaurantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String location = mLocationEditText.getText().toString();
                Log.d(TAG, location);

                /**
                 * An intent represents something our app "intends to do" by describing a simple
                 * action we want the our app to perform.
                 *
                 * Most often, an intent is used to start another activity or hand something
                 * over to another app.
                 *
                 * So here we create an intent called "restaurant_activity_intent" which will
                 * instruct the startActivity method to navigate us to the RestaurantActivity.
                 *
                 * This intent will be triggered by a click event on our findRestaurantButton.
                 * */
                Intent restaurant_activity_intent = new Intent(
                        MainActivity.this, RestaurantsActivity.class);

                /**
                 *  Here we pass location data to the RestaurantActivity by calling  the putExtra
                 *  method on restaurant_activity_intent.
                 *
                 *  putExtra stores the information in a hash-map like, key value pair.
                 * */
                restaurant_activity_intent.putExtra("location_data", location);
                startActivity(restaurant_activity_intent);
            }
        });


    }

}
