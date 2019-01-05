package com.hai.jedi.myrestaurants;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;



public class RestaurantsActivity extends AppCompatActivity {

    private TextView mLocationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
    // Assigning our RestaurantActivity TextView
        mLocationTextView = findViewById(R.id.locationInfo);

    // We pull the data out of our restaurant_activity_intent that we declared in our MainActivity
        Intent restaurant_activity_intent = getIntent();

    // We retrieve the location info using our key "location_data"
        String location = restaurant_activity_intent.getStringExtra("location_data");

    // Displaying location info onto screen.
        mLocationTextView.setText("Here are all the restaurants near: " + location);
    }
}
