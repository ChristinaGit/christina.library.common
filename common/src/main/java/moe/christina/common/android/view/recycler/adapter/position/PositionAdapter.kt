package moe.christina.common.android.view.recycler.adapter.position

import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup

abstract class PositionAdapter<TVH : ViewHolder> {
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

    interface ShiftProvider {
        val shift: Int
            get() = 0
    }

    var shiftProvider: ShiftProvider = object : ShiftProvider {}

    abstract val itemCount: Int
    abstract fun isKnownViewType(viewType: Int): Boolean

    abstract fun getItemViewType(position: Int): Int
    abstract fun performCreateViewHolder(parent: ViewGroup, viewType: Int): TVH

    abstract fun performBindViewHolder(holder: TVH, position: Int)

    fun getItemPositionRange() =
        shiftProvider.shift.let {
            it until it + itemCount
        }

    fun getItemIndexRange() = 0 until itemCount

    fun notifyItemsChanged(adapter: Adapter<*>) {
        val itemCount = itemCount
        if (itemCount > 0) {
            adapter.notifyItemRangeChanged(shiftProvider.shift, itemCount)
        }
    }

    protected val shift: Int
        get() = shiftProvider.shift
}