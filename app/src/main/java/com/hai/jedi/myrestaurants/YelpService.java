package com.hai.jedi.myrestaurants;

import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class YelpService {

    public static void findRestaurants(String location, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.YELP_BASE_URL + Constants.YELP_LOCATION_PARAM)
                .addHeader("Authorization", Constants.YELP_API_KEY)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
