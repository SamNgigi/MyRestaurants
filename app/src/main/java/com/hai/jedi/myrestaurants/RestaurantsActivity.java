package com.hai.jedi.myrestaurants;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;



public class RestaurantsActivity extends AppCompatActivity {
    // Var that stores view we will display the welcome data.
    private TextView mLocationTextView;
    private ListView mListView;

    // String Array of dummy restaurants that we want to display
    private String[] restaurants = new String[]{
            "Kibandanski", "Mother's Bistro", "Life of Pie", "Screen Door", "Luc Lac",
            "Sweet Basil", "Fudge Cakes", "Equinox", "Miss Delta's", "Bae's Kitchen",
            "Edo", "Nai City Grill", "Fat Head's Brewery", "Chipotle", "Polo"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

    // Our list view
        mListView = findViewById(R.id.listView);

    // Assigning our RestaurantActivity TextView Where we will display text when the activity
    // launched
        mLocationTextView = findViewById(R.id.locationInfo);

    /**
     * So basically what an ArrayAdapter does is that it parses data from our Array to our ListView
     * in the format of our choosing. It can be a simple list, drop down, or any other option of
     * list.
     *
     * In other words it is responsible for taking an ArrayList of objects from our business logic
     * and converts them into View objects to be displayed in our User interface.
     * */
    // Our ArrayAdapter
        ArrayAdapter adapter = new ArrayAdapter(
                       this, android.R.layout.simple_list_item_1, restaurants);

    // Setting data to ListView
        mListView.setAdapter(adapter);

    // Adding a simple toast of the restaurant name when someone clicks on a list item
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                String restaurant = ((TextView)view).getText().toString();
                Toast.makeText(RestaurantsActivity.this, restaurant, Toast.LENGTH_LONG).show();
            }
        });


    // We pull the data out of our restaurant_activity_intent that we declared in our MainActivity
        Intent restaurant_activity_intent = getIntent();

    // We retrieve the location info using our key "location_data"
        String location = restaurant_activity_intent.getStringExtra("location_data");

    // Displaying location info onto screen.
        mLocationTextView.setText("Here are all the restaurants near: " + location + ".");
    }
}

/**
 * NOTES ON ADAPTERS.
 *
 * ADAPTER
 * - Essentially a bridge between components of our UI and data from our backend to be displayed in
 * the UI.
 *
 * ARRAY ADAPTER
 * - Responsible for taking ArrayList objects from backend and converting them to View objects to be
 * displayed.
 *
 * RECYCLING.
 * - This is the act of an Adapter re-using individual Views of a ListView as user scrolls through
 * list. After the ListView has created enough individual list items to fit the full height of the
 * screen, it simply re-uses existing list items to display the new list items instead of creating
 * more.
 *
 * SCRAP-VIEWS.
 * - This is a View that leaves the screen while scrolling but is kept in memory, to be re-used to
 * display other items of data, during the recycling process.
 *
 * */


