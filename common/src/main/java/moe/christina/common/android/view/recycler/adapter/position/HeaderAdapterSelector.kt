package moe.christina.common.android.view.recycler.adapter.position

import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import moe.christina.common.core.adapter.position.HeaderPositionAdapter

fun Adapter<*>.notifyHeaderItemsChanged(positionAdapter: HeaderAdapterSelector<*, *, *>) =
    positionAdapter.notifyHeaderItemsChanged(this)

fun Adapter<*>.notifyInnerItemsChanged(positionAdapter: HeaderAdapterSelector<*, *, *>) =
    positionAdapter.notifyInnerItemsChanged(this)

fun Adapter<*>.notifyFooterItemsChanged(positionAdapter: HeaderAdapterSelector<*, *, *>) =
    positionAdapter.notifyFooterItemsChanged(this)

class HeaderAdapterSelector<
    HVH : ViewHolder,
    IVH : ViewHolder,
    FVH : ViewHolder>
@JvmOverloads
constructor(
    override val positionAdapter: HeaderPositionAdapter = HeaderPositionAdapter()
) : AdapterHelper<ViewHolder>() {
    var viewHolderBinder: ViewHolderBinder<HVH, IVH, FVH> = ViewHolderBinder.default()

    var viewTypeSelector: ViewTypeSelector = ViewTypeSelector.default()

    fun setViewTypes(
        headerItemViewTypes: IntArray,
        innerItemViewTypes: IntArray,
        footerItemViewTypes: IntArray,
        headerItemViewTypeSelector: (Int) -> Int,
        innerItemViewTypeSelector: (Int) -> Int,
        footerItemViewTypeSelector: (Int) -> Int) {
        viewTypeSelector = object : ViewTypeSelector {
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
        viewTypeSelector = object : ViewTypeSelector {
            override fun getHeaderItemViewType(position: Int) = headerItemViewType
            override fun getInnerItemViewType(position: Int) = innerItemViewType
            override fun getFooterItemViewType(position: Int) = footerItemViewType

            override fun isHeaderItemViewType(viewType: Int) = viewType == headerItemViewType
            override fun isInnerItemViewType(viewType: Int) = viewType == innerItemViewType
            override fun isFooterItemViewType(viewType: Int) = viewType == footerItemViewType
        }
    }

    private var headerItemAdapterHelper: AdapterHelper<HVH>? = null
    private var innerItemAdapterHelper: AdapterHelper<IVH>? = null
    private var footerItemAdapterHelper: AdapterHelper<FVH>? = null

    @JvmOverloads
    fun attachHeaderItemAdapterHelper(adapter: AdapterHelper<HVH>, shift: Int = 0) {
        positionAdapter.attachHeaderItemPositionAdapter(adapter.positionAdapter, shift)
        headerItemAdapterHelper = adapter
    }

    fun detachHeaderItemAdapterHelper() {
        positionAdapter.detachHeaderItemPositionAdapter()
        headerItemAdapterHelper = null
    }

    @JvmOverloads
    fun attachInnerItemAdapterHelper(adapter: AdapterHelper<IVH>, shift: Int = 0) {
        positionAdapter.attachInnerItemPositionAdapter(adapter.positionAdapter, shift)
        innerItemAdapterHelper = adapter
    }

    fun detachInnerItemAdapterHelper() {
        positionAdapter.detachInnerItemPositionAdapter()
        innerItemAdapterHelper = null
    }

    @JvmOverloads
    fun attachFooterItemAdapterHelper(adapter: AdapterHelper<FVH>, shift: Int = 0) {
        positionAdapter.attachFooterItemPositionAdapter(adapter.positionAdapter, shift)
        footerItemAdapterHelper = adapter
    }

    fun detachFooterItemAdapterHelper() {
        positionAdapter.detachFooterItemPositionAdapter()
        footerItemAdapterHelper = null
    }

    @CallSuper
    override fun performCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when {
            isHeaderItemViewType(viewType) -> {
                val nestedAdapterHelper = headerItemAdapterHelper
                if (nestedAdapterHelper?.isKnownViewType(viewType) == true) {
                    nestedAdapterHelper.performCreateViewHolder(parent, viewType)
                } else {
                    viewHolderBinder.onCreateHeaderItemViewHolder(parent, viewType)
                }
            }
            isInnerItemViewType(viewType) -> {
                val nestedAdapterHelper = innerItemAdapterHelper
                if (nestedAdapterHelper?.isKnownViewType(viewType) == true) {
                    nestedAdapterHelper.performCreateViewHolder(parent, viewType)
                } else {
                    viewHolderBinder.onCreateInnerItemViewHolder(parent, viewType)
                }
            }
            isFooterItemViewType(viewType) -> {
                val nestedAdapterHelper = footerItemAdapterHelper
                if (nestedAdapterHelper?.isKnownViewType(viewType) == true) {
                    nestedAdapterHelper.performCreateViewHolder(parent, viewType)
                } else {
                    viewHolderBinder.onCreateFooterItemViewHolder(parent, viewType)
                }
            }
            else -> errorUnknownViewType(viewType)
        }

    @CallSuper
    override fun performBindViewHolder(holder: ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when {
            isHeaderItemViewType(viewType) -> {
                val nestedAdapterHelper = headerItemAdapterHelper
                if (nestedAdapterHelper?.positionAdapter?.actualItemPositionRange?.contains(position) == true) {
                    nestedAdapterHelper.performBindViewHolder(holder as HVH, position)
                } else {
                    viewHolderBinder.onBindHeaderItemViewHolder(holder as HVH, position)
                }
            }
            isInnerItemViewType(viewType) -> {
                val nestedAdapterHelper = innerItemAdapterHelper
                if (nestedAdapterHelper?.positionAdapter?.actualItemPositionRange?.contains(position) == true) {
                    nestedAdapterHelper.performBindViewHolder(holder as IVH, position)
                } else {
                    viewHolderBinder.onBindInnerItemViewHolder(holder as IVH, position)
                }
            }
            isFooterItemViewType(viewType) -> {
                val nestedAdapterHelper = footerItemAdapterHelper
                if (nestedAdapterHelper?.positionAdapter?.actualItemPositionRange?.contains(position) == true) {
                    nestedAdapterHelper.performBindViewHolder(holder as FVH, position)
                } else {
                    viewHolderBinder.onBindFooterItemViewHolder(holder as FVH, position)
                }
            }
            else -> errorUnknownViewType(viewType, position)
        }
    }

    override fun isKnownViewType(viewType: Int): Boolean =
        isHeaderItemViewType(viewType)
            || isInnerItemViewType(viewType)
            || isFooterItemViewType(viewType)

    override fun getItemViewType(position: Int) =
        when (position) {
            in positionAdapter.actualHeaderItemIndexRange -> getHeaderItemViewType(position)
            in positionAdapter.actualInnerItemIndexRange -> getInnerItemViewType(position)
            in positionAdapter.actualFooterItemIndexRange -> getFooterItemViewType(position)
            else -> errorNoItemByPosition(position)
        }

    fun getHeaderItemViewType(position: Int): Int {
        positionAdapter.checkActualHeaderItemPosition(position)

        val viewType: Int

        val nestedAdapterHelper = headerItemAdapterHelper
        if (nestedAdapterHelper?.positionAdapter?.actualItemPositionRange?.contains(position) == true) {
            viewType = nestedAdapterHelper.getItemViewType(position)
        } else {
            viewType = viewTypeSelector.getHeaderItemViewType(position)
        }

        return viewType
    }

    fun getInnerItemViewType(position: Int): Int {
        positionAdapter.checkActualInnerItemPosition(position)

        val viewType: Int

        val nestedAdapterHelper = innerItemAdapterHelper
        if (nestedAdapterHelper?.positionAdapter?.actualItemPositionRange?.contains(position) == true) {
            viewType = nestedAdapterHelper.getItemViewType(position)
        } else {
            viewType = viewTypeSelector.getInnerItemViewType(position)
        }

        return viewType
    }

    fun getFooterItemViewType(position: Int): Int {
        positionAdapter.checkActualFooterItemPosition(position)

        val viewType: Int

        val nestedAdapterHelper = footerItemAdapterHelper
        if (nestedAdapterHelper?.positionAdapter?.actualItemPositionRange?.contains(position) == true) {
            viewType = nestedAdapterHelper.getItemViewType(position)
        } else {
            viewType = viewTypeSelector.getFooterItemViewType(position)
        }

        return viewType
    }

    fun isHeaderItemViewType(viewType: Int): Boolean =
        viewTypeSelector.isHeaderItemViewType(viewType) ||
            headerItemAdapterHelper?.isKnownViewType(viewType) == true

    fun isInnerItemViewType(viewType: Int): Boolean =
        viewTypeSelector.isInnerItemViewType(viewType) ||
            innerItemAdapterHelper?.isKnownViewType(viewType) == true

    fun isFooterItemViewType(viewType: Int): Boolean =
        viewTypeSelector.isFooterItemViewType(viewType) ||
            footerItemAdapterHelper?.isKnownViewType(viewType) == true

    fun notifyHeaderItemsChanged(adapter: Adapter<*>) {
        val itemCount = positionAdapter.actualHeaderItemCount
        if (itemCount > 0) {
            adapter.notifyItemRangeChanged(positionAdapter.getActualHeaderItemPosition(0), itemCount)
        }
    }

    fun notifyInnerItemsChanged(adapter: Adapter<*>) {
        val itemCount = positionAdapter.actualInnerItemCount
        if (itemCount > 0) {
            adapter.notifyItemRangeChanged(positionAdapter.getActualInnerItemPosition(0), itemCount)
        }
    }

    fun notifyFooterItemsChanged(adapter: Adapter<*>) {
        val itemCount = positionAdapter.actualFooterItemCount
        if (itemCount > 0) {
            adapter.notifyItemRangeChanged(positionAdapter.getActualFooterItemPosition(0), itemCount)
        }
    }

    interface ViewTypeSelector {
        companion object {
            fun default(): ViewTypeSelector = object : ViewTypeSelector {
                override fun getHeaderItemViewType(position: Int) = 0
                override fun getInnerItemViewType(position: Int) = 0
                override fun getFooterItemViewType(position: Int) = 0

                override fun isHeaderItemViewType(viewType: Int) = false
                override fun isInnerItemViewType(viewType: Int) = false
                override fun isFooterItemViewType(viewType: Int) = false
            }
        }

        fun getHeaderItemViewType(position: Int): Int
        fun getInnerItemViewType(position: Int): Int
        fun getFooterItemViewType(position: Int): Int

        fun isHeaderItemViewType(viewType: Int): Boolean
        fun isInnerItemViewType(viewType: Int): Boolean
        fun isFooterItemViewType(viewType: Int): Boolean
    }

    interface ViewHolderBinder<HVH, IVH, FVH> {
        companion object {
            fun <HVH, IVH, FVH> default(): ViewHolderBinder<HVH, IVH, FVH> =
                object : ViewHolderBinder<HVH, IVH, FVH> {
                    override fun onCreateHeaderItemViewHolder(parent: ViewGroup, viewType: Int) = throw NotImplementedError()
                    override fun onCreateInnerItemViewHolder(parent: ViewGroup, viewType: Int) = throw NotImplementedError()
                    override fun onCreateFooterItemViewHolder(parent: ViewGroup, viewType: Int) = throw NotImplementedError()

                    override fun onBindHeaderItemViewHolder(holder: HVH, position: Int) {}
                    override fun onBindInnerItemViewHolder(holder: IVH, position: Int) {}
                    override fun onBindFooterItemViewHolder(holder: FVH, position: Int) {}
                }
        }

        fun onCreateHeaderItemViewHolder(parent: ViewGroup, viewType: Int): HVH
        fun onCreateInnerItemViewHolder(parent: ViewGroup, viewType: Int): IVH
        fun onCreateFooterItemViewHolder(parent: ViewGroup, viewType: Int): FVH

        fun onBindHeaderItemViewHolder(holder: HVH, position: Int)
        fun onBindInnerItemViewHolder(holder: IVH, position: Int)
        fun onBindFooterItemViewHolder(holder: FVH, position: Int)
    }
}