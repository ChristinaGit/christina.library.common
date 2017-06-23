package moe.christina.common.android.view.recycler.adapter

import android.support.v7.widget.RecyclerView

abstract class RecyclerViewListAdapter<TItem, TViewHolder : RecyclerView.ViewHolder>
    : WrappedRecyclerViewAdapter<TViewHolder>() {
    companion object {
        private fun throwNoItemByRelativePositionError(relativePosition: Int): Nothing =
                throw IllegalArgumentException("No item by relative position: $relativePosition")
    }

    fun getItem(position: Int): TItem {
        return getItemByRelativePosition(getInnerItemRelativePosition(position))
    }

    fun getItemByRelativePosition(relativePosition: Int): TItem {
        val items = items ?: throwNoItemByRelativePositionError(relativePosition)
        return when (relativePosition) {
            in 0..items.size -> items[relativePosition]
            else -> throwNoItemByRelativePositionError(relativePosition)
        }
    }

    override val innerItemCount: Int
        get() = items?.size ?: 0

    abstract var items: List<TItem>?
}
