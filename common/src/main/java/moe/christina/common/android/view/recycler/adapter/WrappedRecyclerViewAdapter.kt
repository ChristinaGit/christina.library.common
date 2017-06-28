package moe.christina.common.android.view.recycler.adapter

import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class WrappedRecyclerViewAdapter<TViewHolder : RecyclerView.ViewHolder> : RecyclerViewAdapter<TViewHolder>() {
    companion object {
        @JvmStatic
        val VIEW_TYPE_HEADER = newViewType()
        @JvmStatic
        val VIEW_TYPE_INNER = newViewType()
        @JvmStatic
        val VIEW_TYPE_FOOTER = newViewType()
    }

    fun notifyHeaderItemsChanged() {
        val itemCount = headerItemCount
        if (itemCount > 0) {
            notifyItemRangeChanged(getHeaderItemAdapterPosition(0), itemCount)
        }
    }

    fun notifyInnerItemsChanged() {
        val itemCount = innerItemCount
        if (itemCount > 0) {
            notifyItemRangeChanged(getInnerItemAdapterPosition(0), itemCount)
        }
    }

    fun notifyFooterItemsChanged() {
        val itemCount = footerItemCount
        if (itemCount > 0) {
            notifyItemRangeChanged(getFooterItemAdapterPosition(0), itemCount)
        }
    }

    override fun getItemCount(): Int = headerItemCount + innerItemCount + footerItemCount

    open val headerItemCount: Int
        get() = 0

    abstract val innerItemCount: Int

    open val footerItemCount: Int
        get() = 0

    @CallSuper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TViewHolder = when {
        isHeaderItemViewType(viewType) -> onCreateHeaderItemViewHolder(parent, viewType)
        isInnerItemViewType(viewType) -> onCreateInnerItemViewHolder(parent, viewType)
        isFooterItemViewType(viewType) -> onCreateFooterItemViewHolder(parent, viewType)
        else -> errorUnknownViewType(viewType)
    }

    @CallSuper
    override fun onBindViewHolder(holder: TViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when {
            isHeaderItemViewType(viewType) -> onBindHeaderItemViewHolder(holder, position)
            isInnerItemViewType(viewType) -> onBindInnerItemViewHolder(holder, position)
            isFooterItemViewType(viewType) -> onBindFooterItemViewHolder(holder, position)
            else -> errorUnknownViewType(viewType, position)
        }
    }

    @CallSuper
    override fun getItemViewType(position: Int) =
            when (position) {
                in getHeaderItemValidPositionRange() -> getHeaderItemViewType(position)
                in getInnerItemValidPositionRange() -> getInnerItemViewType(position)
                in getFooterItemValidPositionRange() -> getFooterItemViewType(position)
                else -> errorNoItemByPosition(position)
            }

    open fun getHeaderItemViewType(position: Int): Int {
        checkHeaderItemPosition(position)

        return VIEW_TYPE_HEADER
    }

    open fun getInnerItemViewType(position: Int): Int {
        checkInnerItemPosition(position)

        return VIEW_TYPE_INNER
    }

    open fun getFooterItemViewType(position: Int): Int {
        checkFooterItemPosition(position)

        return VIEW_TYPE_FOOTER
    }

    open fun isHeaderItemViewType(viewType: Int): Boolean = viewType == VIEW_TYPE_HEADER

    open fun isInnerItemViewType(viewType: Int): Boolean = viewType == VIEW_TYPE_INNER

    open fun isFooterItemViewType(viewType: Int): Boolean = viewType == VIEW_TYPE_FOOTER

    fun getHeaderItemRelativePosition(position: Int): Int {
        checkHeaderItemPosition(position)

        return position
    }

    fun getInnerItemRelativePosition(position: Int): Int {
        checkInnerItemPosition(position)

        return position - headerItemCount
    }

    fun getFooterItemRelativePosition(position: Int): Int {
        checkFooterItemPosition(position)

        return position - headerItemCount - innerItemCount
    }

    fun getHeaderItemAdapterPosition(relativePosition: Int): Int {
        checkHeaderItemRelativePosition(relativePosition)

        return relativePosition
    }

    fun getInnerItemAdapterPosition(relativePosition: Int): Int {
        checkInnerItemRelativePosition(relativePosition)

        return relativePosition + headerItemCount
    }

    fun getFooterItemAdapterPosition(relativePosition: Int): Int {
        checkFooterItemRelativePosition(relativePosition)

        return relativePosition + headerItemCount + innerItemCount
    }

    open fun onBindHeaderItemViewHolder(holder: TViewHolder, position: Int) {}

    open fun onBindInnerItemViewHolder(holder: TViewHolder, position: Int) {}

    open fun onBindFooterItemViewHolder(holder: TViewHolder, position: Int) {}

    open fun onCreateHeaderItemViewHolder(parent: ViewGroup, viewType: Int): TViewHolder =
            errorUnknownViewType(viewType)

    open fun onCreateInnerItemViewHolder(parent: ViewGroup, viewType: Int): TViewHolder =
            errorUnknownViewType(viewType)

    open fun onCreateFooterItemViewHolder(parent: ViewGroup, viewType: Int): TViewHolder =
            errorUnknownViewType(viewType)

    fun getHeaderItemValidPositionRange() =
            0..(headerItemCount - 1)

    fun getInnerItemValidPositionRange() =
            headerItemCount..(headerItemCount + innerItemCount - 1)

    fun getFooterItemValidPositionRange() =
            (headerItemCount + innerItemCount)..(headerItemCount + innerItemCount + footerItemCount - 1)

    fun getHeaderItemValidRelativePositionRange() = 0..(headerItemCount - 1)

    fun getInnerItemValidRelativePositionRange() = 0..(innerItemCount - 1)

    fun getFooterItemValidRelativePositionRange() = 0..(footerItemCount - 1)

    protected fun checkHeaderItemPosition(position: Int) {
        if (position !in getHeaderItemValidPositionRange()) {
            throw errorNoItemByPosition(position)
        }
    }

    protected fun checkInnerItemPosition(position: Int) {
        if (position !in getInnerItemValidPositionRange()) {
            throw errorNoItemByPosition(position)
        }
    }

    protected fun checkFooterItemPosition(position: Int) {
        if (position !in getFooterItemValidPositionRange()) {
            throw errorNoItemByPosition(position)
        }
    }

    protected fun checkHeaderItemRelativePosition(position: Int) {
        if (position !in getHeaderItemValidPositionRange()) {
            throw errorNoItemByRelativePosition(position)
        }
    }

    protected fun checkInnerItemRelativePosition(position: Int) {
        if (position !in getInnerItemValidRelativePositionRange()) {
            throw errorNoItemByRelativePosition(position)
        }
    }

    protected fun checkFooterItemRelativePosition(position: Int) {
        if (position !in getFooterItemValidRelativePositionRange()) {
            throw errorNoItemByRelativePosition(position)
        }
    }
}
