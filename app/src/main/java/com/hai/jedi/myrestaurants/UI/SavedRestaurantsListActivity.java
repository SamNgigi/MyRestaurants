package com.hai.jedi.myrestaurants.UI;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.Adapters.FirebaseRestaurantViewHolder;
import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SavedRestaurantsListActivity extends AppCompatActivity {

    // Initializing our DatabaseReference and FirebaseRecyclerAdapter
    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    // Initializing our RecyclerView member
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_restaurants_list);

        ButterKnife.bind(this);

        // Our database reference instance
        mRestaurantReference = FirebaseDatabase
                               .getInstance()
                               .getReference(Constants.FIREBASE_CHILD_RESTAURANTS);

        // Calling our firebase adapter that we create below
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter(){

        // Declaring our firebase options which include the db restaurant reference
        // and the restaurant class
        FirebaseRecyclerOptions restaurantOptions = new FirebaseRecyclerOptions
                                                    .Builder<Restaurant>()
                                                    .setQuery(mRestaurantReference, Restaurant.class)
                                                    .build();

        // Binding our persisted restaurants to our firebase restaurant vie holder.
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder>(restaurantOptions) {

            // Defining how and where our persisted restaurant data will be viewed.
            @NonNull
            @Override
            public FirebaseRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.restaurant_list_item, parent, false);
                return new FirebaseRestaurantViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FirebaseRestaurantViewHolder viewHolder,
                                            int position, @NonNull Restaurant model){
                viewHolder.bindRestaurant(model);
            }


        };

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mFirebaseAdapter.stopListening();
    }
}
