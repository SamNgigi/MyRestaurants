package com.hai.jedi.myrestaurants.Adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.Utils.ItemTouchHelperAdapter;
import com.hai.jedi.myrestaurants.Utils.OnStartDragListener;

import androidx.annotation.NonNull;

public class FirebaseRestaurantListAdapter
        extends FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder>
        implements ItemTouchHelperAdapter {

    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public FirebaseRestaurantListAdapter(@NonNull FirebaseRecyclerOptions<Restaurant> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull FirebaseRestaurantViewHolder frViewHolder, int position, @NonNull Restaurant restaurant) {

    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
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
        return null;
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

    }
}
