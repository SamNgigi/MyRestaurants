package com.hai.jedi.myrestaurants.Adapters;

import com.google.firebase.database.DatabaseReference;
import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.UI.RestaurantDetailActivity;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseRestaurantViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseRestaurantViewHolder (View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindRestaurant (Restaurant restaurant){
        ImageView restaurantImageView = (ImageView) mView.findViewById(R.id.restaurantImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.restaurantNameTextView);
        TextView tagTextView = (TextView) mView.findViewById(R.id.tagTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);

        Picasso.get().load(restaurant.getImageUrl())
                     .resize(MAX_WIDTH, MAX_HEIGHT)
                     .centerCrop()
                     .into(restaurantImageView);

        nameTextView.setText(restaurant.getName());
        tagTextView.setText(restaurant.getCategories().get(0));
        ratingTextView.setText(String.format("Rating: %s/5", restaurant.getRating()));
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Restaurant> restaurants = new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                                                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener(){
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot){

               for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                   // add snapshot Restaurant Json objects to the restaurant array list.
                   restaurants.add(snapshot.getValue(Restaurant.class));
               }

               int itemPosition = getLayoutPosition();

               Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
               intent.putExtra("position", itemPosition + "");
               intent.putExtra("restaurants", Parcels.wrap(restaurants));

               mContext.startActivity(intent);
           }

           @Override
            public void onCancelled(DatabaseError databaseError){
               // Do something
           }
        });
    }
}
