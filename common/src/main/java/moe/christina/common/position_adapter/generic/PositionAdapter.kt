package moe.christina.common.position_adapter.generic

import android.support.annotation.CheckResult
import moe.christina.common.position_adapter.generic.context.PositionContext
import moe.christina.common.exception.IllegalOperationException

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

    fun getItemPosition(context: PositionContext, index: Int): Int {
        requireItemIndex(context, index)

        return context.getShift() + index
    }

    fun getItemIndex(context: PositionContext, position: Int): Int {
        requireItemPosition(context, position)

        return position - context.getShift()
    }

    fun getItemPositionRange(context: PositionContext): IntRange {
        val shift = context.getShift()

        return shift until shift + context.getItemCount()
    }

    fun getItemIndexRange(context: PositionContext): IntRange =
        0 until context.getItemCount()

    @CheckResult
    fun checkItemPosition(context: PositionContext, position: Int): Boolean =
        position in getItemIndexRange(context)

    @CheckResult
    fun checkItemIndex(context: PositionContext, index: Int): Boolean =
        index in getItemIndexRange(context)

    fun requireItemPosition(context: PositionContext, position: Int) {
        if (!checkItemPosition(context, position)) {
            errorNoItemByPosition(position)
        }
    }

    fun requireItemIndex(context: PositionContext, index: Int) {
        if (!checkItemIndex(context, index)) {
            errorNoItemByIndex(index)
        }
    }
}