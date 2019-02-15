package com.hai.jedi.myrestaurants.UI;

import com.hai.jedi.myrestaurants.Adapters.FirebaseRestaurantListAdapter;
import com.hai.jedi.myrestaurants.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import com.google.firebase.database.DatabaseReference;

import android.os.Bundle;

public class SavedRestaurantsListActivity extends AppCompatActivity{


    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_restaurants_list);

    }
}