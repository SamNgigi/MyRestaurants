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
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Objects;

public class FirebaseRestaurantViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
    public static final int MAX_WIDTH = 200;
    public static final int MAXHEIGHT = 200;

    public ImageView restaurantImageView;

    View mView;
    Context mContext;

    // Our FirebaseRestaurantViewHolder constructor
    public FirebaseRestaurantViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        mView.setOnClickListener(this);
    }

    public void bindRestaurant(Restaurant restaurant){
        restaurantImageView = (ImageView) mView.findViewById(R.id.restaurantImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.restaurantNameTextView);
        TextView tagTextView = (TextView) mView.findViewById(R.id.tagTxtView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTxtView);

        Picasso.get().load(restaurant
                .getImageUrl())
                .resize(MAX_WIDTH, MAXHEIGHT)
                .centerCrop()
                .into(restaurantImageView);

        nameTextView.setText(restaurant.getName());
        tagTextView.setText(restaurant.getCategories().get(0));
        ratingTextView.setText(String.format("Rating: %s/5", restaurant.getRating()));
    }

    @Override
    public void onClick(View view){
        final ArrayList<Restaurant> restaurants = new ArrayList<>();

        // We have to access the users uid inorder to retrieve details of their saved restaurants
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = Objects.requireNonNull(user).getUid();

        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                .child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    restaurants.add(snapshot.getValue(Restaurant.class));
                }
                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("restaurants", Parcels.wrap(restaurants));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Do something
            }
        });


    }
}