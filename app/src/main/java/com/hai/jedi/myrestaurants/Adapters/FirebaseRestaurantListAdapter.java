package com.hai.jedi.myrestaurants.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Utils.ItemTouchHelperAdapter;
import com.hai.jedi.myrestaurants.Utils.OnStartDragListener;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.MotionEventCompat;

public class FirebaseRestaurantListAdapter
        extends FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder>
        implements ItemTouchHelperAdapter {

    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    /**
     * @param options - What i get by this, is that it will hold our Database reference of the
     *                  restaurant object.
     */



    public FirebaseRestaurantListAdapter(@NonNull FirebaseRecyclerOptions<Restaurant> options,
                                         OnStartDragListener onStartDragListener,
                                         Context context) {
        super(options);
        mOnStartDragListener = onStartDragListener;
        mContext = context;
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
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     * Here we provide the restaurant_list_item_drag layout xml file
     *
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
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
        // We call the notigyItemMoved method to notify our adapter that the underlying data has
        // changed.
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
        getRef(position).removeValue();
    }

}
