package com.hai.jedi.myrestaurants.Utils;

public interface ItemTouchHelperViewHolderInterface {
    /*
    * Will handle updating the appearance of the selected item while the user
    * is dragging-and-dropping it
    * */
    void onItemSelected();
    /*
    * Will remove the "selected" state (and therefore the corresponding changes
    * in appearance) from an item
    * */
    void onItemClear();
}
