package com.hai.jedi.myrestaurants.UI;

import android.content.Intent;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.core.view.MenuItemCompat;
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
//import android.widget.SearchView;

public class RestaurantsListActivity extends AppCompatActivity {
    // Logging Constant for debugging
    public static final String TAG = RestaurantsListActivity.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddresses;

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
        // Log.d("WE ARE HERE: " , location);
        getRestaurants(location);
        // Testing to see if the location can be retrieved from the SharedPreferences
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddresses = mSharedPreferences.getString(Constants.PREFERENCE_LOCATION_KEY,
                null);
        // Log.d("Shared Pref Location", mRecentAddress);
        if(mRecentAddresses != null){
            // Parse our shared preferences to our get restaurant method
            getRestaurants(location);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Binding our views
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        // Accessing our shared preferences and enabling edits
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        // Retrieving a users search
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                /**
                 * This method is run automatically when the user submits a query into our SearchView.
                 *
                 * Because we only want to gather the input after the user has submitted something
                 * and not every time they type a single character into the field, we'll place our
                 * logic in the onQueryTextSubmit and leave the onQueryTextChange fairly empty.
                 * */
                // Adding our query to sharedPreferences
                addToSharedPreferences(query);
                //getting our restaurants
                getRestaurants(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                // This method is run whenever any changes to the SearchView content occurs
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // This ensures that all functionality from the parent class will still apply despite us
        // manually overriding portions of the menu functionality
        return super.onOptionsItemSelected(item);
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

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCE_LOCATION_KEY, location).apply();
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


