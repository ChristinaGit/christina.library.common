package moe.christina.common.android.view.recycler.adapter

import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class WrappedRecyclerViewAdapter<TViewHolder : RecyclerView.ViewHolder> : RecyclerViewAdapter<TViewHolder>() {
    companion object {
        @JvmStatic
        val VIEW_TYPE_HEADER = newViewType()
        @JvmStatic
        val VIEW_TYPE_FOOTER = newViewType()
        @JvmStatic
        val VIEW_TYPE_INNER = newViewType()
    }

    override fun getItemCount(): Int = headerItemCount + innerItemCount + footerItemCount

    val headerItemCount: Int
        get() = 0

    abstract val innerItemCount: Int

    val footerItemCount: Int
        get() = 0

    @CallSuper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TViewHolder {
        val holder: TViewHolder

        holder = when {
            isHeaderItemViewType(viewType) -> onCreateHeaderItemViewHolder(parent, viewType)
            isInnerItemViewType(viewType) -> onCreateInnerItemViewHolder(parent, viewType)
            isFooterItemViewType(viewType) -> onCreateFooterItemViewHolder(parent, viewType)
            else -> throw IllegalArgumentException("Unknown view type: " + viewType)
        }

        return holder
    }

    @CallSuper
    override fun onBindViewHolder(holder: TViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when {
            isHeaderItemViewType(viewType) -> onBindHeaderItemViewHolder(holder, position)
            isInnerItemViewType(viewType) -> onBindInnerItemViewHolder(holder, position)
            isFooterItemViewType(viewType) -> onBindFooterItemViewHolder(holder, position)
            else -> throw IllegalArgumentException("Unknown view type: $viewType at position $position")
        }
    }

    @CallSuper
    override fun getItemViewType(position: Int): Int {
        val viewType: Int

        val headerItemCount = headerItemCount
        val innerItemCount = innerItemCount
        val footerItemCount = footerItemCount

        val headerItemIndex = headerItemCount
        val innerItemIndex = headerItemIndex + innerItemCount
        val footerItemIndex = innerItemIndex + footerItemCount

        viewType = when (position) {
            in 0..(headerItemIndex - 1) -> getHeaderItemViewType(position)
            in headerItemIndex..(innerItemIndex - 1) -> getInnerItemViewType(position)
            in innerItemIndex..(footerItemIndex - 1) -> getFooterItemViewType(position)
            else -> throw IllegalArgumentException("Illegal position: " + position)
        }

        return viewType
    }

    protected open fun getHeaderItemViewType(position: Int): Int = VIEW_TYPE_HEADER

    protected open fun getInnerItemViewType(position: Int): Int = VIEW_TYPE_INNER

    protected open fun getFooterItemViewType(position: Int): Int = VIEW_TYPE_FOOTER

    protected open fun isHeaderItemViewType(viewType: Int): Boolean = viewType == VIEW_TYPE_HEADER

    protected open fun isInnerItemViewType(viewType: Int): Boolean = viewType == VIEW_TYPE_INNER

    protected open fun isFooterItemViewType(viewType: Int): Boolean = viewType == VIEW_TYPE_FOOTER

    protected fun getHeaderItemRelativePosition(position: Int): Int = position

    protected fun getInnerItemRelativePosition(position: Int): Int = position - headerItemCount

    protected fun getFooterItemRelativePosition(position: Int): Int = position - headerItemCount - innerItemCount

    protected fun getHeaderItemAdapterPosition(relativePosition: Int): Int = relativePosition

    protected fun getInnerItemAdapterPosition(relativePosition: Int): Int = relativePosition + headerItemCount

    protected fun getFooterItemAdapterPosition(relativePosition: Int): Int = relativePosition + headerItemCount + innerItemCount

    protected open fun onBindHeaderItemViewHolder(holder: TViewHolder, position: Int) {
    }

    protected open fun onBindInnerItemViewHolder(holder: TViewHolder, position: Int) {
    }

    protected open fun onBindFooterItemViewHolder(holder: TViewHolder, position: Int) {
    }

    protected open fun onCreateHeaderItemViewHolder(parent: ViewGroup, viewType: Int): TViewHolder =
            throw IllegalArgumentException("Unknown view type: " + viewType)

    protected open fun onCreateInnerItemViewHolder(parent: ViewGroup, viewType: Int): TViewHolder =
            throw IllegalArgumentException("Unknown view type: " + viewType)

    protected open fun onCreateFooterItemViewHolder(parent: ViewGroup, viewType: Int): TViewHolder =
            throw IllegalArgumentException("Unknown view type: " + viewType)

    fun notifyInnerItemsChanged() {
        val innerItemCount = innerItemCount
        if (innerItemCount > 0) {
            notifyItemRangeChanged(getInnerItemAdapterPosition(0), innerItemCount)
        }
    }
}
