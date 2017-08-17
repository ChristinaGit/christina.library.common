package moe.christina.common.android.view.recycler.adapter

import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import moe.christina.common.core.adapter.position.HeaderPositionAdapter
import moe.christina.common.core.adapter.position.HeaderPositionAdapter.BindViewHolderDelegate
import moe.christina.common.core.adapter.position.HeaderPositionAdapter.ItemCountDelegate
import moe.christina.common.core.adapter.position.HeaderPositionAdapter.ViewTypeDelegate
import moe.christina.common.android.view.recycler.adapter.position.notifyFooterItemsChanged
import moe.christina.common.android.view.recycler.adapter.position.notifyHeaderItemsChanged
import moe.christina.common.android.view.recycler.adapter.position.notifyInnerItemsChanged

abstract class HeaderRecyclerViewAdapter<
    THVH : ViewHolder,
    TIVH : ViewHolder,
    TFVH : ViewHolder>
    : RecyclerViewAdapter<ViewHolder>(),
    BindViewHolderDelegate<THVH, TIVH, TFVH>,
    ViewTypeDelegate,
    ItemCountDelegate {
    companion object {
        @JvmStatic
        val VIEW_TYPE_HEADER = newViewType()
        @JvmStatic
        val VIEW_TYPE_INNER = newViewType()
        @JvmStatic
        val VIEW_TYPE_FOOTER = newViewType()
    }

    protected val headerPositionAdapter = HeaderPositionAdapter<THVH, TIVH, TFVH>().apply {
        itemCountDelegate = this@HeaderRecyclerViewAdapter
        viewTypeDelegate = this@HeaderRecyclerViewAdapter
        bindViewHolderDelegate = this@HeaderRecyclerViewAdapter
    }

    override fun getHeaderItemViewType(position: Int) = VIEW_TYPE_HEADER
    override fun getInnerItemViewType(position: Int) = VIEW_TYPE_INNER
    override fun getFooterItemViewType(position: Int) = VIEW_TYPE_FOOTER

    override fun isHeaderItemViewType(viewType: Int) = viewType == VIEW_TYPE_HEADER
    override fun isInnerItemViewType(viewType: Int) = viewType == VIEW_TYPE_INNER
    override fun isFooterItemViewType(viewType: Int) = viewType == VIEW_TYPE_FOOTER

    @CallSuper
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
        headerPositionAdapter.performCreateViewHolder(parent!!, viewType)

    @CallSuper
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) =
        headerPositionAdapter.performBindViewHolder(holder!!, position)

    final override fun getItemCount(): Int = headerPositionAdapter.itemCount
    final override fun getItemViewType(position: Int) = headerPositionAdapter.getItemViewType(position)

    fun notifyHeaderItemsChanged() = notifyHeaderItemsChanged(headerPositionAdapter)
    fun notifyInnerItemsChanged() = notifyInnerItemsChanged(headerPositionAdapter)
    fun notifyFooterItemsChanged() = notifyFooterItemsChanged(headerPositionAdapter)
}
