package moe.christina.common.android.view.recycler.adapter

import android.support.v7.widget.RecyclerView

abstract class HeaderListRecyclerViewAdapter<
    TItem,
    THVH : RecyclerView.ViewHolder,
    TIVH : RecyclerView.ViewHolder,
    TFVH : RecyclerView.ViewHolder>
    : HeaderRecyclerViewAdapter<THVH, TIVH, TFVH>() {

    fun getItem(position: Int): TItem =
        items!![headerPositionAdapter.getActualInnerItemIndex(position)]

    override val innerItemCount: Int
        get() = items?.size ?: 0

    var items: List<TItem>? = null
}
