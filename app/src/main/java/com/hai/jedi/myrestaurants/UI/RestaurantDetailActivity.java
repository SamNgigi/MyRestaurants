package com.hai.jedi.myrestaurants.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// We have to import this now because everything is now  modularized and not in the
// default locations
import com.hai.jedi.myrestaurants.R;

public class RestaurantDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
    }
}
