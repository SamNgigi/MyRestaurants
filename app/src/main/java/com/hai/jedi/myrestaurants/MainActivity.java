package com.hai.jedi.myrestaurants;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

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

    private Button mFindRestaurantsButton;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Run default behaviours for an activity.
        super.onCreate(savedInstanceState);
        // Method below tells activity which layout to use for main screen.
        // R is short for resources. In this case we are using activity_main.xml
        setContentView(R.layout.activity_main);


        mFindRestaurantsButton = (Button) findViewById(R.id.findRestaurantsButton);

        // We add a listener to our button so that we can make it do something when it is clicked.
        mFindRestaurantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // An intent represents something our app "intends to do" by describing a simple
                // action we want the our app to perform.
                /**
                 * Most oftern, an intent is used to start another activity or hand something
                 * over to another app.
                 *
                 * So here we create an intent called "restaurant_activity_intent" which will
                 * instruct the startActivity method to navigate us to the RestaurantActivity.
                 *
                 * This intent will be triggered by a click event on our findRestaurantButton.
                 * */
                Intent restaurant_activity_intent = new Intent(
                        MainActivity.this, RestaurantsActivity.class);
                startActivity(restaurant_activity_intent);
            }
        });


    }

}
