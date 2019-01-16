package com.hai.jedi.myrestaurants;

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

public class YelpService {

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
            String json_data = response.body().string();
            // If the api call was successful
            if (response.isSuccessful()){
                // Json Object from the Json String
                JSONObject yelp_response = new JSONObject(json_data);
                // Return the array of business. NOTE business is an array so we use getJSONArray
                JSONArray biz_json_array = yelp_response.getJSONArray("businesses");
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
                    // We get our addressJson from the location "object" in the api response.
                    // We the find the addresses in the display_address "array"
                    JSONArray addressesJson = restaurantJSON.getJSONObject("location")
                                                             .getJSONArray("display_address");
                    for(int x = 0; x < addressesJson.length(); x++){
                        addresses.add(addressesJson.get(x).toString());
                    }

                    // Where we will store our categories
                    ArrayList<String> categories = new ArrayList<>();
                    // We get our categories from the categories array
                    JSONArray categoriesJSON = restaurantJSON.getJSONArray("categories");
                    for(int z = 0; z < categoriesJSON.length(); z++){
                        categories.add(categoriesJSON.getJSONArray(z).get(0).toString());
                    }

                    Restaurant restaurant = new Restaurant(name, phone, website, rating, imageUrl,
                            latitude, longitude, addresses, categories);
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
