package com.hai.jedi.myrestaurants.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;

import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.UI.RestaurantDetailActivity;
import com.hai.jedi.myrestaurants.Utils.ItemTouchHelperAdapter;
import com.hai.jedi.myrestaurants.Utils.OnStartDragListener;

import org.parceler.Parcels;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.MotionEventCompat;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseRestaurantListAdapter
        extends FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder>
        implements ItemTouchHelperAdapter {

    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    private ChildEventListener mChildEventListener;
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();

    /**
     * @param options - What i get by this, is that it will hold our Database reference of the
     *                  restaurant object.
     */



    public FirebaseRestaurantListAdapter(@NonNull FirebaseRecyclerOptions<Restaurant> options,
                                         DatabaseReference reference,
                                         OnStartDragListener onStartDragListener,
                                         Context context) {
        super(options);
        mRef = reference;
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener(){

            /*
             * Each time the adapter is constructed, the onChildAdded() will be triggered for each
             * item in the given reference
             * */
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String str){
                /*
                 * We use the add() method to add each returned item to the mRestaurants ArrayList so
                 * that we can access the list of restaurants throughout our adapter.
                 * */
                mRestaurants.add(dataSnapshot.getValue(Restaurant.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s){

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot){

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s){

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }

        });

    }

    /**
     *  Below we bind {@param restaurant} object to our custom {@link FirebaseRestaurantViewHolder}
     */

    @SuppressLint( "ClickableViewAccessibility" )
    @Override
    protected void onBindViewHolder(@NonNull FirebaseRestaurantViewHolder frViewHolder,
                                    int position, @NonNull Restaurant restaurant) {

        frViewHolder.bindRestaurant(restaurant);
        frViewHolder.restaurantImageView.setOnTouchListener(
                new View.OnTouchListener(){

                    /**
                     * Called when a touch event is dispatched to a view. This allows listeners to
                     * get a chance to respond before the target view.
                     *
                     * @param v     The view the touch event has been dispatched to.
                     * @param event The MotionEvent object containing full information about
                     *              the event.
                     * @return True if the listener has consumed the event, false otherwise.
                     *
                     * Has some lint warnings informing us that it is proper to deal with the
                     * perform click actions too. So that there is no conflict.
                     */
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });

        frViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Defining intent to go from mContext to RestaurantDetailActivity
                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                // Getting the current position of the click item
                intent.putExtra("position", frViewHolder.getAdapterPosition());
                // Parsing the data within the specific restaurant.
                intent.putExtra("restaurants", Parcels.wrap(mRestaurants));

                mContext.startActivity(intent);
            }
        });
    }

    /**
     * Called when RecyclerView needs a new {@link FirebaseRestaurantViewHolder} of the given type
     * to represent an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     * Here we provide the restaurant_list_item_drag layout xml file
     *
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(FirebaseRestaurantViewHolder, int, Restaurant)}. Since it will be
     * re-used to display different items in the data set, it is a good idea to cache references to
     * sub views of the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(FirebaseRestaurantViewHolder, int, Restaurant)
     */
    @NonNull
    @Override
    public FirebaseRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_item_drag, parent, false);
        return new FirebaseRestaurantViewHolder(view);
    }

    /**
     * This interface is responsible for custom, touchscreen specific user interactions
     * also known as gestures.
     * <p>
     * onItemMove
     * will be called each time the user moves an item by dragging it across the
     * touch screen.
     * It takes two arguments that represent the original position that on item was on resulting
     * position as a result of the drag.
     *
     * @param fromPosition
     * @param toPosition
     */
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        DatabaseReference reference1 = getRef(fromPosition);
        DatabaseReference reference2 = getRef(toPosition);
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mRestaurants, i, i + 1);
                Restaurant restaurant_1 = mRestaurants.get(i);
                restaurant_1.setIndex(Integer.toString(i));
                reference1.setValue(restaurant_1);
                Restaurant restaurant_2 = mRestaurants.get(i+1);
                restaurant_2.setIndex(Integer.toString(i+1));
                reference2.setValue(restaurant_2);

            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mRestaurants, i, i - 1);
                Restaurant restaurant_1 = mRestaurants.get(i);
                restaurant_1.setIndex(Integer.toString(i));
                reference1.setValue(restaurant_1);
                Restaurant restaurant_2 = mRestaurants.get(i-1);
                restaurant_2.setIndex(Integer.toString(i-1));
                reference2.setValue(restaurant_2);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
        return false;

    }

    /**
     * Below method is called when an item has been dismissed with a swipe motion. The parameter
     * position represents the location the user moved the item to.
     *
     * @param position
     */
    @Override
    public void onItemDismiss(int position) {
        /*
         * To delete the dismissed item from Firebase, we can call the getRef method
         * passing in an item's position and the FirebaseRecyclerAdapter will return
         * the DatabaseReference for the given object.
         *
         * We then call the removeValue method to delete that object from Firebase.
         *
         * Once deleted the FirebaseRecyclerAdapter will automatically update the view.
         *
         * Note the getRef() method is a firebase ui's FirebaseRecyclerAdapter method.
         * */
        mRestaurants.remove(position);
        getRef(position).removeValue();
    }


    @Override
    public void stopListening(){
        super.stopListening();
        mRef.removeEventListener(mChildEventListener);
    }

}