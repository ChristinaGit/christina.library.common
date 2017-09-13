package moe.christina.common.android.view.recycler.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class RecyclerViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {
    val context: Context
        get() = itemView.context
}
