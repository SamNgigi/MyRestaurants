package com.hai.jedi.myrestaurants.UI;

import androidx.appcompat.app.AppCompatActivity;;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Utils.OnRestaurantSelectedInterface;

import org.parceler.Parcels;

import java.util.ArrayList;

public class RestaurantsListActivity
        extends AppCompatActivity
        implements OnRestaurantSelectedInterface {

    private Integer mPosition;
    ArrayList<Restaurant> mRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        if(savedInstanceState != null){
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mRestaurants = Parcels.unwrap(savedInstanceState
                                .getParcelable(Constants.EXTRA_KEY_RESTAURANTS));
                if(mPosition != null && mRestaurants != null){
                    // Passing our position and restaurants to the RestaurantDetail Activity and
                    // therefore to its fragment.
                    Intent intent = new Intent(this, RestaurantDetailFragment.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));

                    startActivity(intent);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if(mPosition != null && mRestaurants !=null){
            outState.putInt(Constants.EXTRA_KEY_RESTAURANTS, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));
        }
    }

    // We implement this method to get hold of the correct restaurant that was clicked on
    @Override
    public void onRestaurantSelected(Integer position, ArrayList<Restaurant> restaurants){
        mPosition = position;
        mRestaurants = restaurants;
    }

}



