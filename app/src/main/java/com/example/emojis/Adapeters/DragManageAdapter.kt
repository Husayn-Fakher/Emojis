package com.example.emojis.Adapeters

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created By Fakher_Husayn on 23-Aug-20
 **/
class DragManageAdapter(adapter: EmojiAdapter, context: Context, dragDirs: Int, swipeDirs: Int) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    var nameAdapter = adapter
    var myContext = context


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        nameAdapter.swapItems(viewHolder.adapterPosition, target.adapterPosition)
        Toast.makeText(myContext, " Moved " + viewHolder.adapterPosition, Toast.LENGTH_LONG).show()

        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        nameAdapter.deleteItems(viewHolder.adapterPosition)

    }


}