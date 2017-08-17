package moe.christina.common.core.adapter.position

import moe.christina.common.core.latebox.LateInt
import moe.christina.common.core.latebox.late
import moe.christina.common.core.latebox.plus

class HeaderPositionAdapter : PositionAdapter() {
    override val itemCount: LateInt
        get() = headerItemCount + innerItemCount + footerItemCount

    val headerItemCount: LateInt = 0.late()
    val innerItemCount: LateInt = 0.late()
    val footerItemCount: LateInt = 0.late()

    val actualHeaderItemCount: Int get() = headerItemCount()
    val actualInnerItemCount: Int get() = innerItemCount()
    val actualFooterItemCount: Int get() = footerItemCount()

    private var headerItemPositionAdapter: PositionAdapter? = null
    private var innerItemPositionAdapter: PositionAdapter? = null
    private var footerItemPositionAdapter: PositionAdapter? = null

    @JvmOverloads
    fun attachHeaderItemPositionAdapter(adapter: PositionAdapter, nestedShift: Int = 0) {
        headerItemPositionAdapter = adapter.apply {
            shift += nestedShift

            freeze()
        }
    }

    fun detachHeaderItemPositionAdapter() {
        headerItemPositionAdapter?.apply {
            unfreeze()

            shift = 0.late()
        }
        headerItemPositionAdapter = null
    }

    @JvmOverloads
    fun attachInnerItemPositionAdapter(adapter: PositionAdapter, nestedShift: Int = 0) {
        innerItemPositionAdapter = adapter.apply {
            shift += nestedShift + headerItemCount

            freeze()
        }
    }

    fun detachInnerItemPositionAdapter() {
        innerItemPositionAdapter?.apply {
            unfreeze()

            shift = 0.late()
        }
        innerItemPositionAdapter = null
    }

    @JvmOverloads
    fun attachFooterItemPositionAdapter(adapter: PositionAdapter, nestedShift: Int = 0) {
        footerItemPositionAdapter = adapter.apply {
            shift = nestedShift + headerItemCount + innerItemCount

            freeze()
        }
    }

    fun detachFooterItemPositionAdapter() {
        footerItemPositionAdapter?.apply {
            unfreeze()

            shift = 0.late()
        }
        footerItemPositionAdapter = null
    }

    fun getActualHeaderItemIndex(position: Int): Int {
        checkActualHeaderItemPosition(position)

        return position - actualShift
    }

    fun getActualInnerItemIndex(position: Int): Int {
        checkActualInnerItemPosition(position)

        return position - actualShift - actualHeaderItemCount
    }

    fun getActualFooterItemIndex(position: Int): Int {
        checkActualFooterItemPosition(position)

        return position - actualShift - actualHeaderItemCount - actualInnerItemCount
    }

    fun getActualHeaderItemPosition(index: Int): Int {
        checkActualHeaderItemIndex(index)

        return actualShift + index
    }

    fun getActualInnerItemPosition(index: Int): Int {
        checkActualInnerItemIndex(index)

        return actualShift + index + actualHeaderItemCount
    }

    fun getActualFooterItemPosition(index: Int): Int {
        checkActualFooterItemIndex(index)

        return actualShift + index + actualHeaderItemCount + actualInnerItemCount
    }

    val actualHeaderItemPositionRange: IntRange
        get() = actualShift.let {
            it until it + actualHeaderItemCount
        }

    val actualInnerItemPositionRange: IntRange
        get() = actualShift.let {
            val headerItemCount = actualHeaderItemCount

            (it + headerItemCount) until (it + headerItemCount + actualInnerItemCount)
        }

    val actualFooterItemPositionRange: IntRange
        get() = actualShift.let {
            val headerItemCount = actualHeaderItemCount
            val innerItemCount = actualInnerItemCount
            (it + headerItemCount + innerItemCount) until (it + headerItemCount + innerItemCount + actualFooterItemCount)
        }

    val actualHeaderItemIndexRange: IntRange
        get() = 0 until actualHeaderItemCount

    val actualInnerItemIndexRange: IntRange
        get() = 0 until actualInnerItemCount

    val actualFooterItemIndexRange: IntRange
        get() = 0 until actualFooterItemCount

    fun checkActualHeaderItemPosition(position: Int) {
        if (position !in actualHeaderItemPositionRange) {
            errorNoItemByPosition(position)
        }
    }

    fun checkActualInnerItemPosition(position: Int) {
        if (position !in actualInnerItemPositionRange) {
            errorNoItemByPosition(position)
        }
    }

    fun checkActualFooterItemPosition(position: Int) {
        if (position !in actualFooterItemPositionRange) {
            errorNoItemByPosition(position)
        }
    }

    fun checkActualHeaderItemIndex(index: Int) {
        if (index !in actualHeaderItemIndexRange) {
            errorNoItemByIndex(index)
        }
    }

    fun checkActualInnerItemIndex(index: Int) {
        if (index !in actualInnerItemIndexRange) {
            errorNoItemByIndex(index)
        }
    }

    fun checkActualFooterItemIndex(index: Int) {
        if (index !in actualFooterItemIndexRange) {
            errorNoItemByIndex(index)
        }
    }
}