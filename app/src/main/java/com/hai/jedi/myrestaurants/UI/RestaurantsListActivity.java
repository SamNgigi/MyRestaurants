package com.hai.jedi.myrestaurants.UI;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.Response;

import com.hai.jedi.myrestaurants.Adapters.RestaurantListAdapter;
import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;

public class RestaurantsListActivity extends AppCompatActivity {
    // Logging Constant for debugging
    public static final String TAG = RestaurantsListActivity.class.getSimpleName();

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private RestaurantListAdapter mAdapter;

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
        getRestaurants(location);
        // Testing to see if the location can be retrieved from the SharedPreferences
        /*SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        String mRecentAddress = mSharedPreference.getString(Constants.PREFERENCE_LOCATION_KEY,
                null);*/
        // Log.d("Shared Pref Location", mRecentAddress);
        /*if(mRecentAddress != null){
            // Parse our shared preferences to our get restaurant method

        }*/
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

                RestaurantsListActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run(){
                        mAdapter = new RestaurantListAdapter(getApplicationContext(), mRestaurants);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                                RestaurantsListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        // This informs the RecyclerView that its width and height should always
                        // the same. Otherwise as individual list item views are continually recycled,
                        // it may attempt to rest its own size to best fit the content.
                        mRecyclerView.setHasFixedSize(true);
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


