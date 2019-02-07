package com.hai.jedi.myrestaurants.UI;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.hai.jedi.myrestaurants.Adapters.FirebaseRestaurantListAdapter;
import com.hai.jedi.myrestaurants.Adapters.FirebaseRestaurantViewHolder;
import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import com.google.firebase.database.DatabaseReference;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.hai.jedi.myrestaurants.Utils.OnStartDragListener;
import com.hai.jedi.myrestaurants.Utils.SimpleItemTouchHelperCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public class SavedRestaurantsListActivity extends AppCompatActivity implements OnStartDragListener{
    private DatabaseReference mRestaurantReference;
    private FirebaseRestaurantListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = Objects.requireNonNull(user).getUid();
        mRestaurantReference = FirebaseDatabase
                                .getInstance()
                                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                                .child(uid);


        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter(){


        FirebaseRecyclerOptions<Restaurant> options = new FirebaseRecyclerOptions
                .Builder<Restaurant>()
                .setQuery(mRestaurantReference, Restaurant.class)
                .build();

        mFirebaseAdapter = new FirebaseRestaurantListAdapter(options, (OnStartDragListener) this, this);

        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
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

    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }

}