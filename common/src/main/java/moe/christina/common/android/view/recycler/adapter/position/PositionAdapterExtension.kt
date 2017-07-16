package moe.christina.common.android.view.recycler.adapter.position

import android.support.v7.widget.RecyclerView.Adapter

fun Adapter<*>.notifyItemsChanged(positionAdapter: PositionAdapter<*>) =
    positionAdapter.notifyItemsChanged(this)