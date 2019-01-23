package com.hai.jedi.myrestaurants.UI;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Models.Restaurant;

import org.parceler.Parcels;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailFragment extends Fragment {

    //Attaching the layout views to our logic
    @BindView(R.id.restaurantImageView) ImageView mImageLabel;
    @BindView(R.id.restaurantNameTextView) TextView mNameLabel;
    @BindView(R.id.tagTextView) TextView mTagLabel;
    @BindView(R.id.ratingTextView) TextView mRatingLabel;
    @BindView(R.id.urlTextView) TextView mUrlLabel;
    @BindView(R.id.phoneTextView) TextView mPhoneLabel;
    @BindView(R.id.addressTextView) TextView mAddressLabel;
    @BindView(R.id.saveRestaurantButton) TextView msSaveRestaurantButton;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        ButterKnife.bind(this, view);
        // Attaching the various Restaurant object properties to the layout/view
        Picasso.get().load(mRestaurant.getmImageUrl()).into(mImageLabel);
        mNameLabel.setText(mRestaurant.getmName());
        mTagLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getmCategories()));
        mRatingLabel.setText(String.format("%s/5",Double.toString(mRestaurant.getmRating())));
        mPhoneLabel.setText(mRestaurant.getmPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getmAddresses()));

        // We haven't the website url yet.
        return view;
    }

}
