package com.hai.jedi.myrestaurants.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.hai.jedi.myrestaurants.Adapters.FirebaseRestaurantViewHolder;
import com.hai.jedi.myrestaurants.Utils.ItemTouchHelperAdapter;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter itemTouchHelperAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter){
        /*
         * This constructor takes an ItemTouchHelper parameter.
         *
         * When implemented in FirebaseRestaurantListAdapter, the ItemTouchHelperAdapter
         * instance will pass the gesture event back to the Firebase adapter where we will
         * define what occurs when an item is moved or dismissed
         * */
        itemTouchHelperAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled(){
        /*
        * This method informs the ItemTouchHelper that drag gestures are enabled. We could also
        * disable drag gestures by return false
        *
        * In order to use a specific handle view (ImageView)to drag and drop we need to set
        * this to false
        * */
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled(){
        /*
        * This method informs the ItemTouchHelperAdapter that swipe gestures are enabled.
        *
        * We could also disable them by returning false.
        * */
        return true;
    }

   @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull  RecyclerView.ViewHolder source,
                          @NonNull RecyclerView.ViewHolder target){
        /*
        * This method notifies the adapter that an item has moved.
        * This triggers the onItemMove override in our Firebase adapter, which will eventually
        * handle updating the restaurants ArrayList to reflect the item's new position
        * */
        if(source.getItemViewType() != target.getItemViewType()){
            // Signaling no movement
            return false;
        }
        itemTouchHelperAdapter.onItemMove(source.getAdapterPosition(),
                                          target.getAdapterPosition());
        return true;
   }

   @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i){
        /*
        * This method notifies the adapter that an item was dismissed. This triggers the
        * onItemDismiss override in our Firebase adapter which will eventually handle deleting
        * this item from the user's "Saved Restaurants" in Firebase
        * */

        itemTouchHelperAdapter.onItemDismiss(viewHolder.getAdapterPosition());
   }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder){

        /*
         * This method informs the ItemTouchHelper which movement directions are supported.
         * For example, when a user drags a list item, they press "Down" to begin the drag an lift
         * their finger, "Up", to end the drag.
         *
         * */

        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }


}
