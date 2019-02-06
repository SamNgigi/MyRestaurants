package com.hai.jedi.myrestaurants.Utils;

import androidx.recyclerview.widget.RecyclerView;

public interface OnStartDragListener {
    /**
     * Below method will be called when the user begins a 'drag-and-drop' interaction with
     * the touchscreen.
     *
     * viewHolder represents the RecyclerView holder corresponding to the object being dragged
     * */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
