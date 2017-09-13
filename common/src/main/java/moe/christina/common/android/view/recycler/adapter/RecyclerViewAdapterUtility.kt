package moe.christina.common.android.view.recycler.adapter

import android.support.v7.widget.RecyclerView.Adapter
import moe.christina.common.core.adapter.position.generic.PositionAdapter
import moe.christina.common.core.adapter.position.generic.context.PositionContext

interface WithPositionAdapterUtility<out T> {
    val positionAdapter: T
}

fun <T> T.notifyItemsChanged()
    where
    T : Adapter<*>,
    T : WithPositionAdapterUtility<PositionAdapter>,
    T : PositionContext {
    notifyItemRangeChanged(positionAdapter.getItemPosition(this, 0), itemCount)
}