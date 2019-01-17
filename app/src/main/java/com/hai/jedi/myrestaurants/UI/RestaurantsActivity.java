package com.hai.jedi.myrestaurants.UI;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.Response;

import android.util.Log;

import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.Adapters.MyRestaurantsArrayAdapter;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

public class RestaurantsActivity extends AppCompatActivity {
    // Logging Constant for debugging
    public static final String TAG = RestaurantsActivity.class.getSimpleName();

    // Var that stores view we will display the welcome data.
    // private TextView mLocationTextView;
    @BindView( R.id.locationInfo) TextView mLocationTextView;
    // private ListView mListView;
    @BindView(R.id.listView) ListView mListView;

    // String Array of dummy restaurants that we want to display
    private String[] restaurants = new String[]{
            "Kibandanski", "Mother's Bistro", "Life of Pie", "Screen Door", "Luc Lac",
            "Sweet Basil", "Fudge Cakes", "Equinox", "Miss Delta's", "Bae's Kitchen",
            "Edo", "Nai City Grill", "Fat Head's Brewery", "Chipotle", "Polo"
    };

    // String Array of Cuisines
    private String[] cuisines = new String[] {
            "Vegan Food", "Breakfast", "Fish Dish", "Scandinavian", "Coffee", "English",
            "Burgers", "Fast Food", "Noodle Soups", "Mexican", "BBQ", "Cuban", "Bar Food",
            "Sports Bar", "Pizza", "Pastries"
    };

    public ArrayList<Restaurant> mRestaurants = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

    // We pull the data out of our restaurant_activity_intent that we declared in our MainActivity
        Intent restaurant_activity_intent = getIntent();

    // We retrieve the location info using our key "location_data"
        String location = restaurant_activity_intent.getStringExtra("location_data");

    // Displaying location info onto screen.
        mLocationTextView.setText("Here are all the restaurants near: " + location + ".");
        getRestaurants(location);
    }


    private void getRestaurants(String location) {
        final YelpService yelpService = new YelpService();

        yelpService.findRestaurants(location, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mRestaurants = yelpService.processResults(response);
                Log.d(TAG, mLocationTextView.toString());

                RestaurantsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        String[] restaurantNames = new String[mRestaurants.size()];
                        for (int i = 0; i < restaurantNames.length; i++) {
                            restaurantNames[i] = mRestaurants.get(i).getmName();
                        }
                        Log.d(TAG, restaurantNames.toString());

                        String[] restaurantCuisines = new String[mRestaurants.size()];
                        for(int i = 0; i < restaurantCuisines.length; i++){
                            restaurantCuisines[i] = android.text.TextUtils.join(", ",
                                    mRestaurants.get(i).getmCategories());
                        }
                        /**
                         * Simple ArrayAdapter
                         * ArrayAdapter adapter = new ArrayAdapter<>(RestaurantsActivity.this,
                                android.R.layout.simple_list_item_1, restaurantNames);
                         */
                        // Custom ArrayAdapter
                        MyRestaurantsArrayAdapter adapter = new MyRestaurantsArrayAdapter(
                                RestaurantsActivity.this,
                                android.R.layout.simple_list_item_1,
                                restaurantNames, restaurantCuisines
                                );
                        mListView.setAdapter(adapter);

                        for (Restaurant restaurant : mRestaurants) {
                            Log.d(TAG, "Name: " + restaurant.getmName());
                            Log.d(TAG, "Phone: " + restaurant.getmPhone());
                            Log.d(TAG, "Website: " + restaurant.getmWebsite());
                            Log.d(TAG, "Image url: " + restaurant.getmImageUrl());
                            Log.d(TAG, "Rating: " + Double.toString(restaurant.getmRating()));
                            Log.d(TAG, "Address: " + android.text.TextUtils.join(", ",
                                    restaurant.getmAddresses()));
                            Log.d(TAG, "Categories: " + restaurant.getmCategories().toString());
                        }
                    }
                });
            }
        });
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
 *
 * */


