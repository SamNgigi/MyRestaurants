package com.hai.jedi.myrestaurants;

public class Constants {
    public static final String YELP_API_KEY = BuildConfig.ApiKey;
    public static final String YELP_CLIENT_ID = BuildConfig.ClientID;
    public static final String YELP_BASE_URL = "https://api.yelp.com/v3/businesses/search?term=food";
    public static final String YELP_LOCATION_PARAM="location";
    public static final String PREFERENCE_LOCATION_KEY = "location_data";
    // Firebase Child node reference. This will be the key of our node's key-value pair
    public static final String FIREBASE_CHILD_SEARCHED_LOCATION = "searchedLocation";
    // Firebase restaurants object child node reference. This will hold our json of restaurant objects
    public static final String FIREBASE_CHILD_RESTAURANTS = "restaurants";
    // We add our new string to our Constants class so that we can reference the key of our
    // Restaurant objects when we go to sort them in our Query
    public static final String FIREBASE_QUERY_INDEX = "index";

    // Passing in the Array position of a restaurant
    public static final String EXTRA_KEY_POSITION = "position";
    public static final String EXTRA_KEY_RESTAURANTS = "restaurants";

    // These constants will help us distinguish which fragment we are in
    public static final String KEY_SOURCE = "source";
    public static final String SOURCE_SAVED = "saved";
    public static final String SOURCE_FIND = "find";
}
