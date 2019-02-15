package com.hai.jedi.myrestaurants.Utils;

import com.hai.jedi.myrestaurants.Models.Restaurant;

import java.util.ArrayList;

public interface OnRestaurantSelectedInterface {
    /*
    * We add source param to the method. This will allows to know the activity the user
    * views our reusable fragment either RestaurantListActivity or SavedRestaurantList
    * */
    public void onRestaurantSelected(Integer position,
                                     ArrayList<Restaurant> restaurants,
                                     String source);

}
