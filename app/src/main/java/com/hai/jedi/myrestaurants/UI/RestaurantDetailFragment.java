package com.hai.jedi.myrestaurants.UI;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Models.Restaurant;

import org.parceler.Parcels;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailFragment extends Fragment implements View.OnClickListener{
    // Predefining the size of our images.
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    //Attaching the layout views to our logic
    @BindView(R.id.restaurantImageView) ImageView mImageLabel;
    @BindView(R.id.restaurantNameTextView) TextView nameLabel;
    @BindView(R.id.tagTextView) TextView mTagLabel;
    @BindView(R.id.ratingTextView) TextView mRatingLabel;
    @BindView(R.id.urlTextView) TextView mUrlLabel;
    @BindView(R.id.phoneTextView) TextView phoneLabel;
    @BindView(R.id.addressTextView) TextView addressLabel;
    @BindView(R.id.saveRestaurantButton) TextView mSaveRestaurantButton;

    private Restaurant mRestaurant;

    /**
     * This method below is used instead of a constructor and returns a new instance of our
     * RestaurantDetailFragment.
     *
     * We use the Parceler library add our restaurant object to our bundle and set the bundle as the
     * argument for our new RestaurantDetailFragment*/
    public static RestaurantDetailFragment newInstance(Restaurant restaurant) {
        // Required empty public constructor. Instance of our Restaurant Fragment
        RestaurantDetailFragment restaurant_detail_fragment = new RestaurantDetailFragment();
        // I think what we will use to store and transfer  our serialized data to Fragment
        Bundle arguments = new Bundle();
        // Serializing our restaurant object using Parcels.wrap then "attaching it" to pir arguments,
        // in a hash-map  way/ dictionary using key value pairs.
        arguments.putParcelable("restaurant", Parcels.wrap(restaurant));
        // Passing the serialized data to our fragment.
        restaurant_detail_fragment.setArguments(arguments);
        // returning the restaurant_fragment
        return restaurant_detail_fragment;
    }

    // Below method will be called when the fragment is created and then we unwrap our restaurant
    // object
    @Override
    public void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        // When our fragment instances is created/initialized we want to deserialize the serialized
        // restaurant and store it to mRestaurant.
        assert getArguments() != null;
        mRestaurant = Parcels.unwrap(getArguments().getParcelable("restaurant"));
    }

    // Here we set the various restaurant attributes to the ImageViews and TextViews
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        ButterKnife.bind(this, view);
        // Attaching the various Restaurant object properties to the layout/view
        // Loading images with picasso with predefined size and some style edit.
        Picasso.get().load(mRestaurant.getImageUrl())
                     .resize(MAX_WIDTH, MAX_HEIGHT)
                     .centerCrop()
                     .into(mImageLabel);

        nameLabel.setText(mRestaurant.getName());
        mTagLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getCategories()));
        mRatingLabel.setText(String.format("%s/5",Double.toString(mRestaurant.getRating())));
        phoneLabel.setText(mRestaurant.getPhone());
        addressLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getAddresses()));
        // mUrlLabel.setText(mRestaurant.getName());

        // We set OnClickListeners to website url phone and address
        mUrlLabel.setOnClickListener(this);
        phoneLabel.setOnClickListener(this);
        addressLabel.setOnClickListener(this);
        mSaveRestaurantButton.setOnClickListener(this);
        // We haven't the website url yet.
        return view;
    }

    @Override
    public void onClick(View view){
        if(view == mUrlLabel){
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(mRestaurant.getWebsite()));
            startActivity(webIntent);
        }
        if(view == phoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel: " + mRestaurant.getPhone()));
            startActivity(phoneIntent);
        }
        if(view == addressLabel){
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("geo: " + mRestaurant.getLatitude()
                                            + "," + mRestaurant.getLongitude()
                                            +"?q=(" + mRestaurant.getName() + ")"));
            startActivity(mapIntent);
        }
        if(view == mSaveRestaurantButton) {

            DatabaseReference restaurantRef = FirebaseDatabase
                                             .getInstance()
                                             .getReference(Constants.FIREBASE_CHILD_RESTAURANTS);

            restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                    final ArrayList<String> restaurants = new ArrayList<>();
                    // This loop might be working to slow.
                    // When you save before the api call can check, the same restaurant is persisted
                    // twice.
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        restaurants.add(Objects.requireNonNull(snapshot.getValue(Restaurant.class))
                                   .getName());

                    }

                    Log.d("CAN YOU SEE ME", restaurants.toString());
                    Log.d("CAN YOU SEE ME",
                            Boolean.toString(restaurants.contains(mRestaurant.getName())));
                    /**
                     * We have to use the string property of the restaurant object because
                     * the object itself is not unique enough to check if object exists
                     * */
                    if(restaurants.contains(mRestaurant.getName())){
                        Toast.makeText(getContext(),
                                "Restaurant is already saved",
                                Toast.LENGTH_LONG).show();
                    } else {
                        restaurantRef.push().setValue(mRestaurant);
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError dbError){
                    // do something
                }
            });

        }
    }

}
