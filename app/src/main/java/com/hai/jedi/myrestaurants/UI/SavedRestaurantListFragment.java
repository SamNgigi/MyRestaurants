package com.hai.jedi.myrestaurants.UI;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.hai.jedi.myrestaurants.Adapters.FirebaseRestaurantListAdapter;
import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Utils.OnRestaurantSelectedInterface;
import com.hai.jedi.myrestaurants.Utils.OnStartDragListener;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.Utils.SimpleItemTouchHelperCallback;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedRestaurantListFragment
        extends Fragment
        implements OnStartDragListener {

    private DatabaseReference mRestaurantReference;
    private FirebaseRestaurantListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    private Query query;

    private OnRestaurantSelectedInterface mOnRestaurantSelectedListener;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;


    public SavedRestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_restaurant_list,
                                     container, false);
        ButterKnife.bind(this, view);
        setUpFirebaseAdapter();
        return view;
    }

    private void setUpFirebaseAdapter(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = Objects.requireNonNull(user).getUid();

        mRestaurantReference =  FirebaseDatabase
                                .getInstance()
                                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                                .child(uid);


        query = FirebaseDatabase.getInstance()
                                  .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                                  .child(uid)
                                  .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        FirebaseRecyclerOptions<Restaurant> options = new FirebaseRecyclerOptions
                                                        .Builder<Restaurant>()
                                                        .setQuery(query, Restaurant.class)
                                                        .build();
        /*
        * We change the context from this to getActivity() because fragment do not have their own
        * context
        * */
        mFirebaseAdapter = new FirebaseRestaurantListAdapter(options,
                mRestaurantReference,
                this,
                getActivity());


        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                mFirebaseAdapter.notifyDataSetChanged();
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onStart(){
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        mFirebaseAdapter.stopListening();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    @OnLifecycleEvent( Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        super.onDestroy();
//        mFirebaseAdapter.cleanup(this);
    }

}
