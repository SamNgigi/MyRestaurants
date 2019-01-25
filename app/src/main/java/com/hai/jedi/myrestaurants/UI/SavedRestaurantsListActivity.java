package com.hai.jedi.myrestaurants.UI;

import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.Adapters.FirebaseRestaurantViewHolder;

import com.google.firebase.database.FirebaseDatabase;
import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.ViewGroup;

public class SavedRestaurantsListActivity extends AppCompatActivity {

    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_restaurants_list);

        ButterKnife.bind(this);

        mRestaurantReference = FirebaseDatabase.getInstance()
                                               .getReference(Constants.FIREBASE_CHILD_RESTAURANTS);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter(){
        mFirebaseAdapter =
                new FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder>(
                        Restaurant.class, R.layout.restaurant_list_item,
                        FirebaseRestaurantViewHolder.class, mRestaurantReference
                ) {
            @Override
            protected void populateViewHolder(FirebaseRestaurantViewHolder viewHolder
                                              Restaurant model, int position){
                viewHolder.bindRestaurant(model);
            }
        };
    }
}
