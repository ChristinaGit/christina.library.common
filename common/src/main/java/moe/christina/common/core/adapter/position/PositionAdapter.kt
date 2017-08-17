package moe.christina.common.core.adapter.position

import moe.christina.common.core.exception.IllegalOperationException
import moe.christina.common.core.latebox.LateInt
import moe.christina.common.core.latebox.late

abstract class PositionAdapter {
    companion object {
        @JvmStatic
        protected fun errorPositionAdapterIsFrozen(): Nothing =
            throw IllegalOperationException("Position adapter is frozen")

        @JvmStatic
        protected fun errorNoItemByPosition(position: Int): Nothing =
            throw IllegalArgumentException("No item by position: $position")

        @JvmStatic
        protected fun errorNoItemByIndex(index: Int): Nothing =
            throw IllegalArgumentException("No item by index: $index")
    }

    var shift: LateInt = 0.late()

    val actualShift: Int
        get() = shift()

    var isFrozen: Boolean = false
        private set

    protected fun freeze() {
        isFrozen = true
    }

    protected fun unfreeze() {
        isFrozen = false
    }

    abstract val itemCount: LateInt

    val actualItemCount: Int
        get() = itemCount()

    val actualItemPositionRange: IntRange
        get() = actualShift.let {
            it until it + actualItemCount
        }

    val actualItemIndexRange: IntRange
        get() = 0 until itemCount()

    fun getActualItemPosition(index: Int): Int {
        checkActualItemIndex(index)

        return actualShift + index
    }

    fun getActualItemIndex(position: Int): Int {
        checkActualItemPosition(position)

        return position - actualShift
    }

    fun checkActualItemPosition(position: Int) {
        if (position !in actualItemIndexRange) {
            errorNoItemByPosition(position)
        }
    }

    fun checkActualItemIndex(index: Int) {
        if (index !in actualItemIndexRange) {
            errorNoItemByIndex(index)
        }
    }
}