package com.hai.jedi.myrestaurants;

import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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
}
