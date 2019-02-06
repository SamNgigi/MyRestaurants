package com.hai.jedi.myrestaurants.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.hai.jedi.myrestaurants.Utils.ItemTouchHelperAdapter;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter itemTouchHelperAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter){
        itemTouchHelperAdapter = adapter;
    }

   @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder){
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
   }

   @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull  RecyclerView.ViewHolder source,
                          @NonNull RecyclerView.ViewHolder target){
        return true;
   }

   @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i){
        itemTouchHelperAdapter.onItemDismiss(viewHolder.getAdapterPosition());
   }
}
