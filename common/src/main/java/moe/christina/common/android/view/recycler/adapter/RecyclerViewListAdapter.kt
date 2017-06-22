package moe.christina.common.android.view.recycler.adapter

import android.support.v7.widget.RecyclerView

abstract class RecyclerViewListAdapter<TItem, TViewHolder : RecyclerView.ViewHolder>
    : WrappedRecyclerViewAdapter<TViewHolder>() {
    fun getItem(position: Int): TItem {
        return getItemByRelativePosition(getInnerItemRelativePosition(position))
    }

    fun getItemByRelativePosition(position: Int): TItem {
        val items = items ?: throw IllegalStateException("No item by position: $position")
        return items[position]
    }

    override val innerItemCount: Int
        get() {
            val itemCount: Int

            val items = items
            if (items != null) {
                itemCount = items.size
            } else {
                itemCount = 0
            }

            return itemCount
        }

    abstract var items: List<TItem>?
}
