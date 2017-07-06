package moe.christina.common.android.view.recycler.adapter.position

import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import moe.christina.common.android.view.recycler.adapter.position.PositionAdapter.ShiftProvider

abstract class BasePositionAdapter<TVH : ViewHolder> : PositionAdapter<TVH> {
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

    override var shiftProvider: ShiftProvider = object : ShiftProvider {}

    protected val shift: Int
        get() = shiftProvider.shift

    fun notifyItemsChanged(adapter: Adapter<*>) {
        val itemCount = itemCount
        if (itemCount > 0) {
            adapter.notifyItemRangeChanged(shift, itemCount)
        }
    }
}