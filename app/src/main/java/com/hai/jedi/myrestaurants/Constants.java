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
}
