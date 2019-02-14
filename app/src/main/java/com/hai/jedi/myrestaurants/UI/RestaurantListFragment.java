package com.hai.jedi.myrestaurants.UI;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hai.jedi.myrestaurants.Adapters.RestaurantListAdapter;
import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Services.YelpService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.internal.annotations.EverythingIsNonNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment {

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private RestaurantListAdapter mAdapter;
    public ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAdressess;


    public RestaurantListFragment() {
        // Required empty public constructor
    }

    // We override the onCreate method
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        /*
        * This will allow us to eventually display search menu options
        * within the RestaurantListFragment.
        * */
        mSharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

        // Instructs fragment to include menu options
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        ButterKnife.bind(this, view);

        mRecentAdressess = mSharedPreferences.getString(Constants.PREFERENCE_LOCATION_KEY, null);

        if(mRecentAdressess != null ){
            getRestaurant(mRecentAdressess);
        }

        return view;
    }

    // Menu options
    /* Method is now void, menu inflater is now passed in as an argument*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        /*Call super to inherit methods from parent*/
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void getRestaurant(String location){

        final YelpService yelpService = new YelpService();

        yelpService.findRestaurants(location, new Callback(){

            @Override
            public void onFailure(Call call, IOException e){
                e.printStackTrace();
            }

            @EverythingIsNonNull
            @Override
            public void onResponse( Call call, Response response){
                mRestaurants = yelpService.processResults(response);

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable(){
                    /*
                    * Line above states 'getActivity()' instead of RestaurantListActivity
                    * because fragments do not have their own context, and must inherit from
                    * parent activity.
                    * */

                    @Override
                    public void run(){
                        /*
                        * Below we use getActivity() instead of "getApplicationContext()"
                        * because fragments do not have their own context and have to inherit
                        * from parent
                        * */
                        mAdapter = new RestaurantListAdapter(
                                getActivity(),mRestaurants);
                        mRecyclerView.setAdapter(mAdapter);
                        // We continue with parsing our getActivity()
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                                                                    getActivity());

                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }

        });

    }

    public void addToSharedPreferences(String location){
        mEditor.putString(Constants.PREFERENCE_LOCATION_KEY, location).apply();
    }

}
