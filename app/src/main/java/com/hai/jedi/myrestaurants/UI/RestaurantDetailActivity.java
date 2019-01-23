package com.hai.jedi.myrestaurants.UI;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

// We have to import this now because everything is now  modularized and not in the
// default locations
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.Adapters.RestaurantPagerAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewPager) ViewPager mViewPager;
    private RestaurantPagerAdapter restaurantAdapterViewPager;
    ArrayList<Restaurant> mRestaurants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);

        mRestaurants = Parcels.unwrap(getIntent().getParcelableExtra("restaurants"));
        int starting_position = getIntent().getIntExtra("position", 0);

        /*
        * We instantiate an new pager adapter providing  our restaurant array as Arguments*/
        restaurantAdapterViewPager = new RestaurantPagerAdapter(getSupportFragmentManager(),
                mRestaurants);

        mViewPager.setAdapter(restaurantAdapterViewPager);
        mViewPager.setCurrentItem(starting_position);
    }
}
