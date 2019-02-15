package com.hai.jedi.myrestaurants.Adapters;

import android.content.Context;
import android.content.Intent;

import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.UI.RestaurantDetailActivity;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.UI.RestaurantDetailFragment;
import com.hai.jedi.myrestaurants.Utils.OnRestaurantSelectedInterface;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Remember that an Adapter basically links a list/array of data with the View(ListView)
 *
 * Here we are defining a custom Adapter "RestaurantListAdapter", that extends from the RecyclerView.
 * Adapter in order to work with more complex data such as an ArrayList of Objects.
 *
 * The RecyclerView.Adapter, much like the built-in Android ArrayAdapter, will populate the data into
 * the RecyclerView.
 *
 * It also converts a Java object into an individual list item View to be inserted and displayed to
 * the use.
 *
 * The RecyclerView.Adapter requires a ViewHolder. A ViewHolder is an object that stores multiple Views
 * inside a Layout so they can be loaded immediately and we don't need to find them by ids repeatedly.
 * This improves the performance
 *
 * The RecyclerView.Adapter requires 3 primary methods
 * 1. onCreateViewHolder - inflating an xml layout and returns a ViewHolder
 * 2. onBindViewHolder - sets the various information in the list item View through the ViewHolder.
 *                        This is the moment when the data from our models get associated, aka "bound"
 *                        to our view
 * 3. getItemCount() - simply returns the number of items the RecyclerView will be responsible for
 *                     listing, so that it know how many items views it will need to recycle*/
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {
    // To calculate the item count which will inform the RecyclerView how many individual item Views
    // it will need to recycle.
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    // For our ViewHolder
    private Context mContent;
    // Our Selected restaurant listener
    private OnRestaurantSelectedInterface mOnRestaurantSelectedListener;

    public RestaurantListAdapter(Context context,
                                 ArrayList<Restaurant> restaurants,
                                 OnRestaurantSelectedInterface restaurantSelectedInterface) {
        mContent = context;
        mRestaurants = restaurants;
        mOnRestaurantSelectedListener = restaurantSelectedInterface;

    }

    // Below method inflates an xml layout and returns a ViewHolder. I think it populates/inflates
    // each view it with data from the restaurant object
    @NonNull
    @Override
    public RestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                         int ViewType){
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.restaurant_list_item, parent, false);
        RestaurantViewHolder viewHolder = new RestaurantViewHolder(view, mRestaurants, mOnRestaurantSelectedListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantListAdapter.RestaurantViewHolder holder, int position){
        holder.bindRestaurant(mRestaurants.get(position));
    }

    @Override
    public int getItemCount(){
        return mRestaurants.size();
    }

    // We define our ViewHolderClass that our RecycleView will need. This is where we define the data
    // to be displayed
    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements
    View.OnClickListener{
        // We use ButterKnife to bind all views in the layout
        @BindView( R.id.restaurantImageView) ImageView mRestaurantImageView;
        @BindView(R.id.restaurantNameTextView) TextView nameTextView;
        @BindView(R.id.tagTxtView) TextView mTagTextView;
        @BindView(R.id.ratingTxtView) TextView mRatingTextView;

        private Context mContext;

        private int mOrientation;

        // We added new Parameters to the the view holder  class so that we
        // can access the position and the restaurant object
        private ArrayList<Restaurant> mRestaurants;
        private OnRestaurantSelectedInterface mRestaurantSelectedListener;

        // I would say connecting our different Views into a single ViewHolder
        public RestaurantViewHolder(View itemView,
                                    ArrayList<Restaurant> restaurants,
                                    OnRestaurantSelectedInterface restaurantSelectedInterface){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

            // Determining the current orientation of device
            mOrientation = itemView.getResources().getConfiguration().orientation;

            mRestaurants = restaurants;
            mRestaurantSelectedListener = restaurantSelectedInterface;

            /*
            * Checking if the recorded orientation matches Android's landscape
            * configuration. If so we create a new DetailFragment to display our
            * special landscape layout.
            * */
            if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(0);
            }

            // We set our listener to our RestaurantView Holder constructor
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            // Getting the specific item in the layout that has been clicked
            int itemPosition = getLayoutPosition();
            // Now in the onclick we pass the exact position that was clicked and
            // the Restaurant at that position
            mRestaurantSelectedListener.onRestaurantSelected(
                    itemPosition,
                    mRestaurants,
                    Constants.KEY_SOURCE);

            /*
            * Allowing user to change the detail container in landscape mode
            * depending on which restaurant item they click
            * */
            if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(itemPosition);
            } else {
                /*NORMAL PORTRAIT MODE*/
                // Intent to navigate to our RestaurantDetailActivity
                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                // Pass the item position and restaurant as data to be passes to RestaurantDetailActivity
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));
                intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);
                // Start/launch RestaurantDetailActivity
                mContext.startActivity(intent);
            }

        }

        // We define the method below that will set the contents of the layouts TextView to the
        // attributes specific to a restaurant.
        public void bindRestaurant(Restaurant restaurant){
            // It would seem that we do not need to pass mContext to the get()
            Picasso.get().load(restaurant.getImageUrl()).into(mRestaurantImageView);
            nameTextView.setText(restaurant.getName());
            mTagTextView.setText(restaurant.getCategories().get(0));
            // mTagTextView.setText(android.text.TextUtils.join(", ", restaurant.getCategories()));
            mRatingTextView.setText(String.format("Rating: %s/5", restaurant.getRating()));
        }

        // Method takes in int position of restaurant in list as parameter
        private void createDetailFragment(int position) {
            // We create a new RestaurantDetailFragment with the given position
            RestaurantDetailFragment detailFragment = RestaurantDetailFragment
                                                    .newInstance(mRestaurants,
                                                                position,
                                                                Constants.SOURCE_FIND);
            /*
            * Gathering necessary components to replace the FrameLayout in the layout
            * with the RestaurantDetailFragment
            *
            * The getSupportManager method is part of Android's build in interface of the
            * FragmentManager which is responsible for interacting with Fragment objects
            * */
            FragmentTransaction fragmentTransaction = ((FragmentActivity) mContext)
                                    .getSupportFragmentManager()
                                    .beginTransaction();

            // Replacing the FrameLayout with the RestaurantDetailFragment
            fragmentTransaction.replace(R.id.restaurantDetailContainer, detailFragment);
            // Committing the changes
            fragmentTransaction.commit();
        }


    }

}
