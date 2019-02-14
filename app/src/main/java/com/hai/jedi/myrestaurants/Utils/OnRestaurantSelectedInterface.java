package com.hai.jedi.myrestaurants.Utils;

import com.hai.jedi.myrestaurants.Models.Restaurant;

import java.util.ArrayList;

public interface OnRestaurantSelectedInterface {

    public void onRestaurantSelected(Integer position, ArrayList<Restaurant> restaurants);

}
