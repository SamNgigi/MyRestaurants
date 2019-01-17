package com.hai.jedi.myrestaurants.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private Context mContent;

    public RestaurantListAdapter(Context context, ArrayList<Restaurant> restaurants) {
        mContent = context;
        mRestaurants = restaurants;
    }

    @Override
    public RestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.restaurant_list_item, parent, false);
        RestaurantViewHolder viewHolder = new RestaurantViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantListAdapter.RestaurantViewHolder holder, int position){

    }

    @Override
    public int getItemCount(){
        return mRestaurants.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{
        @BindView( R.id.restaurantImageView) ImageView mRestaurantImageView;
        @BindView(R.id.restaurantNameTextView) TextView mNameTextView;
        @BindView(R.id.tagTxtView) TextView mTagTextView;
        @BindView(R.id.ratingTxtView) TextView mRatingTextView;

        private Context mContext;

        public RestaurantViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindRestaurant(Restaurant restaurant){
            mNameTextView.setText(restaurant.getmName());
            mTagTextView.setText(android.text.TextUtils.join(", ", restaurant.getmCategories()));
            mRatingTextView.setText(String.format("Rating: %s/5", restaurant.getmRating()));
        }
    }

}
