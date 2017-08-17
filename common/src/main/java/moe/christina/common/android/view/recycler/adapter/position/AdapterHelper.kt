package moe.christina.common.android.view.recycler.adapter.position

import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import moe.christina.common.core.adapter.position.PositionAdapter

fun Adapter<*>.notifyItemsChanged(positionAdapter: AdapterHelper<*>) =
    positionAdapter.notifyItemsChanged(this)

abstract class AdapterHelper<VH : ViewHolder> {
    companion object {
        @JvmStatic
        protected fun errorNoItemByPosition(position: Int): Nothing =
            throw IllegalArgumentException("No item by position: $position")

        @JvmStatic
        protected fun errorNoItemByIndex(index: Int): Nothing =
            throw IllegalArgumentException("No item by index: $index")

        @JvmStatic
        protected fun errorUnknownViewType(viewType: Int): Nothing =
            throw IllegalArgumentException("Unknown view type: " + viewType)

        @JvmStatic
        protected fun errorUnknownViewType(viewType: Int, position: Int): Nothing =
            throw IllegalArgumentException("Unknown view type: $viewType at position $position")
    }

    abstract val positionAdapter: PositionAdapter

    abstract fun isKnownViewType(viewType: Int): Boolean
    abstract fun getItemViewType(position: Int): Int
    abstract fun performCreateViewHolder(parent: ViewGroup, viewType: Int): VH
    abstract fun performBindViewHolder(holder: VH, position: Int)

    fun notifyItemsChanged(adapter: Adapter<*>) {
        val itemCount = positionAdapter.actualItemCount
        if (itemCount > 0) {
            adapter.notifyItemRangeChanged(positionAdapter.actualShift, itemCount)
        }
    }
}