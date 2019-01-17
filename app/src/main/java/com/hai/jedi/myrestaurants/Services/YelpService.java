package com.hai.jedi.myrestaurants.Services;

import android.util.Log;

import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.Models.Restaurant;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class YelpService {
    public static final String TAG = YelpService.class.getSimpleName();
    public static void findRestaurants(String location, Callback callback){
        OkHttpClient client = new OkHttpClient();

        // Constructing our url
        HttpUrl.Builder url_builder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        // Passing through parameters. This should result to "location=whatever_we_pass"
        // location param from our method will allow us to pass a location name.
        url_builder.addQueryParameter(Constants.YELP_LOCATION_PARAM, location);
        // Build the url with parameters then convert to string
        String request_url = url_builder.build().toString();

        // Making a request to our url and adding headers.
        Request request = new Request.Builder()
                .url(request_url)
                .addHeader("Authorization", Constants.YELP_API_KEY)
                .build();

        // Making the api call that should receive a call back
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Restaurant> processResults(Response response){
        // Where we will add all our restaurant objects from our api call
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try{
            // The json response as a String
            ResponseBody responseBody = response.body();
            String json_data = responseBody.string();
            Log.d(TAG, json_data);
            // If the api call was successful
            if (response.isSuccessful()){
                // Json Object from the Json String
                JSONObject yelp_response = new JSONObject(json_data);
                // Return the array of business. NOTE business is an array so we use getJSONArray
                JSONArray biz_json_array = yelp_response.getJSONArray("businesses");
                Log.d(TAG, biz_json_array.toString());
                // We loop through the business array
                for(int i = 0; i < biz_json_array.length(); i++){
                    // Get each restaurant
                    JSONObject restaurantJSON = biz_json_array.getJSONObject(i);
                    // Get name
                    String name = restaurantJSON.getString("name");
                    // Get phone with fall back string in-case we don't get a phone number.
                    String phone = restaurantJSON.optString("display_phone",
                                                           "Phone not available");
                    // Get website
                    String website = restaurantJSON.getString("url");
                    // Get rating
                    double rating = restaurantJSON.getDouble("rating");
                    // Get image url
                    String imageUrl = restaurantJSON.getString("image_url");
                    // Get latitude
                    double latitude = restaurantJSON.getJSONObject("coordinates")
                                                    .getDouble("latitude");
                    // Get longitude
                    double longitude = restaurantJSON.getJSONObject("coordinates")
                                                     .getDouble("longitude");

                    // Where we will store all our addresses
                    ArrayList<String> addresses = new ArrayList<>();
                    // We get our addressJson from the location
                    JSONArray addressessJson = restaurantJSON.getJSONObject("location")
                                                             .getJSONArray("display_address");
                    for(int x = 0; x < addressessJson.length(); x++){
                        addresses.add(addressessJson.get(x).toString());
                    }

                    ArrayList<String> categories = new ArrayList<>();
                    JSONArray categoriesJSON = restaurantJSON.getJSONArray("categories");
                    for(int z = 0; z < categoriesJSON.length(); z++){
                        JSONObject categoryObject = categoriesJSON.getJSONObject(z);
                        categories.add(categoryObject.getString("alias"));
                    }
                    Log.d(TAG, categories.toString());

                    Restaurant restaurant = new Restaurant(name, phone, website, rating, imageUrl,
                            latitude, longitude, addresses, categories);
                    Log.d(TAG, String.valueOf(restaurant));
                    restaurants.add(restaurant);
                }
            }

        } catch (IOException exception){
            exception.printStackTrace();
        }catch (JSONException exception){
            exception.printStackTrace();
        }

        return restaurants;
    }
}
