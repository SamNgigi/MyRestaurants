package com.hai.jedi.myrestaurants;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyRestaurantsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mRestaurants;
    private String[] mCuisines;

    public MyRestaurantsArrayAdapter(Context mContext, int resource, String[] mRestaurants,
    String[] mCuisines){
        // Context represents the current state of an application.
        /**
         * Calling the super() from a subclass allows us to reference a method that was overridden.
         *
         * It also allows us to call both the original and the overridden method from a subclass, or
         * call the method from the superclass, from within the overriding method from the subclass.
         *
         * The resource is the type of list view we wil use in this case simple_list_item_1
         *
         * */
        super(mContext, resource);
        this.mContext = mContext;
        this.mRestaurants = mRestaurants;
        this.mCuisines = mCuisines;
    }

    @Override
    public Object getItem(int index){
        // The index comes from the our ArrayAdapter iterating through the Restaurants & Cuisines Arr
        String restaurant = mRestaurants[index];
        String cuisine = mCuisines[index];
        return String.format("%s \nTags: %s", restaurant, cuisine);
    }

    @Override
    public int getCount(){
        // Our ListView requires this count in order to know how many items to draw into the UI
        return mRestaurants.length;
    }
}
