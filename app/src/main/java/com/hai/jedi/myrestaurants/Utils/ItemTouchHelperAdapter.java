package com.hai.jedi.myrestaurants.Utils;

import com.hai.jedi.myrestaurants.Adapters.FirebaseRestaurantViewHolder;

public interface ItemTouchHelperAdapter {

    /**
     * This interface is responsible for custom, touchscreen specific user interactions
     * also known as gestures.
     *
     * onItemMove
     * will be called each time the user moves an item by dragging it across the
     * touch screen.
     * It takes two arguments that represent the original position that on item was on resulting
     * position as a result of the drag.
     * */
    boolean onItemMove(int fromPosition, int toPosition);
    /**
     * Below method is called when an item has been dismissed with a swipe motion. The parameter
     * position represents the location the user moved the item to.
     * */

    void onItemDismiss(int position);

}
