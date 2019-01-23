package com.hai.jedi.myrestaurants.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.UI.RestaurantDetailFragment;

import java.util.ArrayList;

public class RestaurantPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Restaurant> mRestaurants;
    /*
    * A constructor where we set the required FragmentManager*/
    public RestaurantPagerAdapter(FragmentManager fm, ArrayList<Restaurant> restaurants){
        super(fm);
        mRestaurants = restaurants;
    }

    // Below returns a Non null instance of the RestaurantDetailFragment
    @NonNull
    @Override
    public Fragment getItem(int position){
        return RestaurantDetailFragment.newInstance(mRestaurants.get(position));
    }
    // How my restaurants being returned
    @Override
    public int getCount(){
        return mRestaurants.size();
    }

    // Updates the title that appears in the scrolling tabs at the top of the screen.
    @Override
    public CharSequence getPageTitle(int position){
        return mRestaurants.get(position).getmName();
    }
}
