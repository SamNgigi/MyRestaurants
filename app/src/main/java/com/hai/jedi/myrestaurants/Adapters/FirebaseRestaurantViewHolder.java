package com.hai.jedi.myrestaurants.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.UI.RestaurantDetailActivity;
import com.hai.jedi.myrestaurants.Utils.ItemTouchHelperViewHolderInterface;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Objects;

import android.util.Log;

public class FirebaseRestaurantViewHolder
        extends RecyclerView.ViewHolder
         implements ItemTouchHelperViewHolderInterface {

    public final String TAG = FirebaseRestaurantViewHolder.class.getSimpleName();

    public static final int MAX_WIDTH = 200;
    public static final int MAX_HEIGHT = 200;

    public ImageView restaurantImageView;

    View mView;
    Context mContext;

    // Our FirebaseRestaurantViewHolder constructor
    public FirebaseRestaurantViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        //mView.setOnClickListener(this);
    }

    public void bindRestaurant(Restaurant restaurant){
        restaurantImageView = (ImageView) mView.findViewById(R.id.restaurantImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.restaurantNameTextView);
        TextView tagTextView = (TextView) mView.findViewById(R.id.tagTxtView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTxtView);

        Picasso.get().load(restaurant
                .getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(restaurantImageView);

        nameTextView.setText(restaurant.getName());
        tagTextView.setText(restaurant.getCategories().get(0));
        ratingTextView.setText(String.format("Rating: %s/5", restaurant.getRating()));
    }

    @Override
    public void onItemSelected(){
        Log.d("ANIMATION", "onItemSelected");
        // We will add animations here
    }

    @Override
    public void onItemClear(){
        Log.d("ANIMATION", "onItemClear");
    }

}