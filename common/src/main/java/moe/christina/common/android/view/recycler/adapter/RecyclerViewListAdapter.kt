package moe.christina.common.android.view.recycler.adapter

import android.support.v7.widget.RecyclerView

abstract class RecyclerViewListAdapter<TItem, TViewHolder : RecyclerView.ViewHolder>
    : WrappedRecyclerViewAdapter<TViewHolder>() {

    fun getInnerItem(position: Int): TItem =
            getInnerItemByRelativePosition(getInnerItemRelativePosition(position))

    fun getItem(position: Int): TItem = getInnerItem(position)

    fun getInnerItemByRelativePosition(relativePosition: Int): TItem {
        checkInnerItemRelativePosition(relativePosition)

        return items!![relativePosition]
    }

    fun getItemByRelativePosition(relativePosition: Int): TItem
            = getInnerItemByRelativePosition(relativePosition)

    override val innerItemCount: Int
        get() = items?.size ?: 0

    abstract var items: List<TItem>?
}
