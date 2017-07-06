package moe.christina.common.android.view.recycler.adapter.position

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup

interface PositionAdapter<TVH : ViewHolder> {
    interface ShiftProvider {
        val shift: Int
            get() = 0
    }

    var shiftProvider: ShiftProvider
    val itemCount: Int

    fun isKnownViewType(viewType: Int): Boolean
    fun getItemViewType(position: Int): Int

    fun performCreateViewHolder(parent: ViewGroup, viewType: Int): TVH
    fun performBindViewHolder(holder: TVH, position: Int)
}