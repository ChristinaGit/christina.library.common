package moe.christina.common.android.view.recycler.adapter.position

import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import moe.christina.common.android.view.recycler.adapter.position.PositionAdapter.ShiftProvider

open class HeaderPositionAdapter<
    THVH : ViewHolder,
    TIVH : ViewHolder,
    TFVH : ViewHolder> : BasePositionAdapter<ViewHolder>() {
    final override val itemCount: Int
        get() = headerItemCount + innerItemCount + footerItemCount

    var bindViewHolderDelegate: BindViewHolderDelegate<THVH, TIVH, TFVH>
        = object : BindViewHolderDelegate<THVH, TIVH, TFVH> {}

    var viewTypeDelegate: ViewTypeDelegate = object : ViewTypeDelegate {}

    fun setViewTypes(
        headerItemViewTypes: IntArray,
        innerItemViewTypes: IntArray,
        footerItemViewTypes: IntArray,
        headerItemViewTypeSelector: (Int) -> Int,
        innerItemViewTypeSelector: (Int) -> Int,
        footerItemViewTypeSelector: (Int) -> Int) {
        viewTypeDelegate = object : ViewTypeDelegate {
            override fun getHeaderItemViewType(position: Int) = headerItemViewTypeSelector(position)
            override fun getInnerItemViewType(position: Int) = innerItemViewTypeSelector(position)
            override fun getFooterItemViewType(position: Int) = footerItemViewTypeSelector(position)

            override fun isHeaderItemViewType(viewType: Int) = viewType in headerItemViewTypes
            override fun isInnerItemViewType(viewType: Int) = viewType in innerItemViewTypes
            override fun isFooterItemViewType(viewType: Int) = viewType in footerItemViewTypes
        }
    }

    fun setViewTypes(headerItemViewType: Int,
                     innerItemViewType: Int,
                     footerItemViewType: Int) {
        viewTypeDelegate = object : ViewTypeDelegate {
            override fun getHeaderItemViewType(position: Int) = headerItemViewType
            override fun getInnerItemViewType(position: Int) = innerItemViewType
            override fun getFooterItemViewType(position: Int) = footerItemViewType

            override fun isHeaderItemViewType(viewType: Int) = viewType == headerItemViewType
            override fun isInnerItemViewType(viewType: Int) = viewType == innerItemViewType
            override fun isFooterItemViewType(viewType: Int) = viewType == footerItemViewType
        }
    }

    var itemCountDelegate: ItemCountDelegate = object : ItemCountDelegate {}

    fun setItemCount(
        headerItemCount: () -> Int,
        innerItemCount: () -> Int,
        footerItemCount: () -> Int) {
        itemCountDelegate = object : ItemCountDelegate {
            override val headerItemCount: Int
                get() = headerItemCount()
            override val innerItemCount: Int
                get() = innerItemCount()
            override val footerItemCount: Int
                get() = footerItemCount()
        }
    }

    fun setStaticItemCount(
        headerItemCount: Int,
        innerItemCount: Int,
        footerItemCount: Int) {
        itemCountDelegate = object : ItemCountDelegate {
            override val headerItemCount: Int
                get() = headerItemCount
            override val innerItemCount: Int
                get() = innerItemCount
            override val footerItemCount: Int
                get() = footerItemCount
        }
    }

    protected val headerItemCount: Int
        get() = itemCountDelegate.headerItemCount

    protected val innerItemCount: Int
        get() = itemCountDelegate.innerItemCount

    protected val footerItemCount: Int
        get() = itemCountDelegate.footerItemCount

    private var headerItemPositionAdapter: PositionAdapter<THVH>? = null
    private var innerItemPositionAdapter: PositionAdapter<TIVH>? = null
    private var footerItemPositionAdapter: PositionAdapter<TFVH>? = null

    fun attachHeaderItemPositionAdapter(adapter: PositionAdapter<THVH>, shift: Int = 0) {
        headerItemPositionAdapter = adapter.apply {
            shiftProvider = object : ShiftProvider {
                override val shift: Int
                    get() = shift + this@HeaderPositionAdapter.shift
            }
        }
    }

    fun detachHeaderItemPositionAdapter() {
        headerItemPositionAdapter?.shiftProvider = object : ShiftProvider {}
        headerItemPositionAdapter = null
    }

    fun attachInnerItemPositionAdapter(adapter: PositionAdapter<TIVH>, shift: Int = 0) {
        innerItemPositionAdapter = adapter.apply {
            shiftProvider = object : ShiftProvider {
                override val shift: Int
                    get() = shift + this@HeaderPositionAdapter.shift + headerItemCount
            }
        }
    }

    fun detachInnerItemPositionAdapter() {
        innerItemPositionAdapter?.shiftProvider = object : ShiftProvider {}
        innerItemPositionAdapter = null
    }

    fun attachFooterItemPositionAdapter(adapter: PositionAdapter<TFVH>, shift: Int = 0) {
        footerItemPositionAdapter = adapter.apply {
            shiftProvider = object : ShiftProvider {
                override val shift: Int
                    get() = shift + this@HeaderPositionAdapter.shift + headerItemCount + innerItemCount
            }
        }
    }

    fun detachFooterItemPositionAdapter() {
        footerItemPositionAdapter?.shiftProvider = object : ShiftProvider {}
        footerItemPositionAdapter = null
    }

    @CallSuper
    final override fun performCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when {
            isHeaderItemViewType(viewType) -> {
                val nestedPositionAdapter = headerItemPositionAdapter
                if (nestedPositionAdapter?.isKnownViewType(viewType) == true) {
                    nestedPositionAdapter.performCreateViewHolder(parent, viewType)
                } else {
                    bindViewHolderDelegate.onCreateHeaderItemViewHolder(parent, viewType)
                }
            }
            isInnerItemViewType(viewType) -> {
                val nestedPositionAdapter = innerItemPositionAdapter
                if (nestedPositionAdapter?.isKnownViewType(viewType) == true) {
                    nestedPositionAdapter.performCreateViewHolder(parent, viewType)
                } else {
                    bindViewHolderDelegate.onCreateInnerItemViewHolder(parent, viewType)
                }
            }
            isFooterItemViewType(viewType) -> {
                val nestedPositionAdapter = footerItemPositionAdapter
                if (nestedPositionAdapter?.isKnownViewType(viewType) == true) {
                    nestedPositionAdapter.performCreateViewHolder(parent, viewType)
                } else {
                    bindViewHolderDelegate.onCreateFooterItemViewHolder(parent, viewType)
                }
            }
            else -> errorUnknownViewType(viewType)
        }

    @CallSuper
    final override fun performBindViewHolder(holder: ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when {
            isHeaderItemViewType(viewType) -> {
                val nestedPositionAdapter = headerItemPositionAdapter
                if (nestedPositionAdapter?.getItemPositionRange()?.contains(position) == true) {
                    nestedPositionAdapter.performBindViewHolder(holder as THVH, position)
                } else {
                    bindViewHolderDelegate.onBindHeaderItemViewHolder(holder as THVH, position)
                }
            }
            isInnerItemViewType(viewType) -> {
                val nestedPositionAdapter = innerItemPositionAdapter
                if (nestedPositionAdapter?.getItemPositionRange()?.contains(position) == true) {
                    nestedPositionAdapter.performBindViewHolder(holder as TIVH, position)
                } else {
                    bindViewHolderDelegate.onBindInnerItemViewHolder(holder as TIVH, position)
                }
            }
            isFooterItemViewType(viewType) -> {
                val nestedPositionAdapter = footerItemPositionAdapter
                if (nestedPositionAdapter?.getItemPositionRange()?.contains(position) == true) {
                    nestedPositionAdapter.performBindViewHolder(holder as TFVH, position)
                } else {
                    bindViewHolderDelegate.onBindFooterItemViewHolder(holder as TFVH, position)
                }
            }
            else -> errorUnknownViewType(viewType, position)
        }
    }

    final override fun isKnownViewType(viewType: Int): Boolean =
        isHeaderItemViewType(viewType)
            || isInnerItemViewType(viewType)
            || isFooterItemViewType(viewType)

    @CallSuper
    final override fun getItemViewType(position: Int) =
        when (position) {
            in getHeaderItemPositionRange() -> getHeaderItemViewType(position)
            in getInnerItemPositionRange() -> getInnerItemViewType(position)
            in getFooterItemPositionRange() -> getFooterItemViewType(position)
            else -> errorNoItemByPosition(position)
        }

    fun getHeaderItemViewType(position: Int): Int {
        checkHeaderItemPosition(position)

        val viewType: Int

        val nestedPositionAdapter = headerItemPositionAdapter
        if (nestedPositionAdapter?.getItemPositionRange()?.contains(position) == true) {
            viewType = nestedPositionAdapter.getItemViewType(position)
        } else {
            viewType = viewTypeDelegate.getHeaderItemViewType(position)
        }

        return viewType
    }

    fun getInnerItemViewType(position: Int): Int {
        checkInnerItemPosition(position)

        val viewType: Int

        val nestedPositionAdapter = innerItemPositionAdapter
        if (nestedPositionAdapter?.getItemPositionRange()?.contains(position) == true) {
            viewType = nestedPositionAdapter.getItemViewType(position)
        } else {
            viewType = viewTypeDelegate.getInnerItemViewType(position)
        }

        return viewType
    }

    fun getFooterItemViewType(position: Int): Int {
        checkFooterItemPosition(position)

        val viewType: Int

        val nestedPositionAdapter = footerItemPositionAdapter
        if (nestedPositionAdapter?.getItemPositionRange()?.contains(position) == true) {
            viewType = nestedPositionAdapter.getItemViewType(position)
        } else {
            viewType = viewTypeDelegate.getFooterItemViewType(position)
        }

        return viewType
    }

    fun isHeaderItemViewType(viewType: Int): Boolean =
        viewTypeDelegate.isHeaderItemViewType(viewType) ||
            headerItemPositionAdapter?.isKnownViewType(viewType) == true

    fun isInnerItemViewType(viewType: Int): Boolean =
        viewTypeDelegate.isInnerItemViewType(viewType) ||
            innerItemPositionAdapter?.isKnownViewType(viewType) == true

    fun isFooterItemViewType(viewType: Int): Boolean =
        viewTypeDelegate.isFooterItemViewType(viewType) ||
            footerItemPositionAdapter?.isKnownViewType(viewType) == true

    fun notifyHeaderItemsChanged(adapter: Adapter<*>) {
        val itemCount = headerItemCount
        if (itemCount > 0) {
            adapter.notifyItemRangeChanged(getHeaderItemPosition(0), itemCount)
        }
    }

    fun notifyInnerItemsChanged(adapter: Adapter<*>) {
        val itemCount = innerItemCount
        if (itemCount > 0) {
            adapter.notifyItemRangeChanged(getInnerItemPosition(0), itemCount)
        }
    }

    fun notifyFooterItemsChanged(adapter: Adapter<*>) {
        val itemCount = footerItemCount
        if (itemCount > 0) {
            adapter.notifyItemRangeChanged(getFooterItemPosition(0), itemCount)
        }
    }

    fun getHeaderItemIndex(position: Int): Int {
        checkHeaderItemPosition(position)

        return position - shift
    }

    fun getInnerItemIndex(position: Int): Int {
        checkInnerItemPosition(position)

        return position - shift - headerItemCount
    }

    fun getFooterItemIndex(position: Int): Int {
        checkFooterItemPosition(position)

        return position - shift - headerItemCount - innerItemCount
    }

    fun getHeaderItemPosition(index: Int): Int {
        checkHeaderItemIndex(index)

        return shift + index
    }

    fun getInnerItemPosition(index: Int): Int {
        checkInnerItemIndex(index)

        return shift + index + headerItemCount
    }

    fun getFooterItemPosition(index: Int): Int {
        checkFooterItemIndex(index)

        return shift + index + headerItemCount + innerItemCount
    }

    fun getHeaderItemPositionRange() =
        shift.let {
            it..(it + headerItemCount - 1)
        }

    fun getInnerItemPositionRange() =
        shift.let {
            val headerItemCount = headerItemCount

            (it + headerItemCount)..(it + headerItemCount + innerItemCount - 1)
        }

    fun getFooterItemPositionRange() =
        shift.let {
            val headerItemCount = headerItemCount
            val innerItemCount = innerItemCount
            (it + headerItemCount + innerItemCount)..(it + headerItemCount + innerItemCount + footerItemCount - 1)
        }

    fun getHeaderItemIndexRange() = 0..(headerItemCount - 1)

    fun getInnerItemIndexRange() = 0..(innerItemCount - 1)

    fun getFooterItemIndexRange() = 0..(footerItemCount - 1)

    private fun checkHeaderItemPosition(position: Int) {
        if (position !in getHeaderItemPositionRange()) {
            errorNoItemByPosition(position)
        }
    }

    private fun checkInnerItemPosition(position: Int) {
        if (position !in getInnerItemPositionRange()) {
            errorNoItemByPosition(position)
        }
    }

    private fun checkFooterItemPosition(position: Int) {
        if (position !in getFooterItemPositionRange()) {
            errorNoItemByPosition(position)
        }
    }

    private fun checkHeaderItemIndex(index: Int) {
        if (index !in getHeaderItemIndexRange()) {
            errorNoItemByIndex(index)
        }
    }

    private fun checkInnerItemIndex(index: Int) {
        if (index !in getInnerItemIndexRange()) {
            errorNoItemByIndex(index)
        }
    }

    private fun checkFooterItemIndex(index: Int) {
        if (index !in getFooterItemIndexRange()) {
            errorNoItemByIndex(index)
        }
    }

    interface ItemCountDelegate {
        val headerItemCount: Int
            get() = 0
        val innerItemCount: Int
            get() = 0
        val footerItemCount: Int
            get() = 0
    }

    interface ViewTypeDelegate {
        fun getHeaderItemViewType(position: Int): Int = 0
        fun getInnerItemViewType(position: Int): Int = 0
        fun getFooterItemViewType(position: Int): Int = 0

        fun isHeaderItemViewType(viewType: Int): Boolean = false
        fun isInnerItemViewType(viewType: Int): Boolean = false
        fun isFooterItemViewType(viewType: Int): Boolean = false
    }

    interface BindViewHolderDelegate<THVH, TIVH, TFVH> {
        fun onCreateHeaderItemViewHolder(parent: ViewGroup, viewType: Int): THVH =
            throw UnsupportedOperationException()

        fun onCreateInnerItemViewHolder(parent: ViewGroup, viewType: Int): TIVH =
            throw UnsupportedOperationException()

        fun onCreateFooterItemViewHolder(parent: ViewGroup, viewType: Int): TFVH =
            throw UnsupportedOperationException()

        fun onBindHeaderItemViewHolder(holder: THVH, position: Int) {}
        fun onBindInnerItemViewHolder(holder: TIVH, position: Int) {}
        fun onBindFooterItemViewHolder(holder: TFVH, position: Int) {}
    }
}