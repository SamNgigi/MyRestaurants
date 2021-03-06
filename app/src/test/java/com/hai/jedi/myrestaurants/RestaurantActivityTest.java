package com.hai.jedi.myrestaurants;

import android.widget.ListView;

import com.hai.jedi.myrestaurants.UI.RestaurantsListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class RestaurantActivityTest {

    private RestaurantsListActivity restaurant_activity;
    private ListView mRestaurantListView;

    @Before
    public void setUp() {
        // Setting up the activity we are testing
        restaurant_activity = Robolectric.setupActivity(RestaurantsListActivity.class);
        // Setting up the ListView on the activity that we are testing.
        // We modified this view to use RecyclerView.
        // mRestaurantListView = restaurant_activity.findViewById(R.id.listView);
    }

    @Test
    public void restaurantListViewPopulatedTest(){
        // Making sure that the ListView is populated with data from our adapter.
        assertNotNull(mRestaurantListView.getAdapter());
        // Making sure that the view is returning the correct number of items from our String Array.
        assertEquals(15, mRestaurantListView.getAdapter().getCount());
    }
}
