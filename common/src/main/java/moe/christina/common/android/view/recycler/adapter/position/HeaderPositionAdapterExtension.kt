package moe.christina.common.android.view.recycler.adapter.position

import android.support.v7.widget.RecyclerView.Adapter

fun Adapter<*>.notifyHeaderItemsChanged(positionAdapter: HeaderPositionAdapter<*, *, *>) =
    positionAdapter.notifyHeaderItemsChanged(this)

fun Adapter<*>.notifyInnerItemsChanged(positionAdapter: HeaderPositionAdapter<*, *, *>) =
    positionAdapter.notifyInnerItemsChanged(this)

fun Adapter<*>.notifyFooterItemsChanged(positionAdapter: HeaderPositionAdapter<*, *, *>) =
    positionAdapter.notifyFooterItemsChanged(this)