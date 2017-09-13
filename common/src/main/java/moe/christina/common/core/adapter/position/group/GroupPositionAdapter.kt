package moe.christina.common.core.adapter.position.group

import android.support.annotation.CheckResult
import moe.christina.common.core.adapter.position.generic.PositionAdapter
import moe.christina.common.core.adapter.position.group.context.GroupPositionContext

open class GroupPositionAdapter : PositionAdapter() {
    companion object {
        @JvmStatic
        protected fun errorNoGroupByIndex(index: Int): Nothing =
            throw IllegalArgumentException("No group by index: $index")

        @JvmStatic
        protected fun errorNoGroupByPosition(position: Int): Nothing =
            throw IllegalArgumentException("No group by position: $position")

        @JvmStatic
        protected fun errorNoItemByIndex(groupIndex: Int, index: Int): Nothing =
            throw IllegalArgumentException("No item in group $groupIndex by index: $index")

        @JvmStatic
        protected fun errorNoItemByPosition(groupIndex: Int, position: Int): Nothing =
            throw IllegalArgumentException("No item in group $groupIndex by position: $position")
    }

    fun getGroupItemPosition(context: GroupPositionContext, groupIndex: Int, index: Int): Int {
        requireGroupIndex(context, groupIndex)
        requireGroupItemIndex(context, groupIndex, index)

        return context.getShift() +
            (0 until groupIndex).sumBy { context.getGroupItemCount(it) } +
            index
    }

    fun getGroupItemIndex(context: GroupPositionContext, groupIndex: Int, position: Int): Int {
        requireGroupIndex(context, groupIndex)
        requireGroupItemPosition(context, groupIndex, position)

        return position - context.getShift() - (0 until groupIndex).sumBy { context.getGroupItemCount(it) }
    }

    fun getGroupIndex(context: GroupPositionContext, position: Int): Int {
        requireItemPosition(context, position)

        var skipped = 0
        for (groupIndex in getGroupIndexRange(context)) {
            skipped += context.getGroupItemCount(groupIndex)

            if (position < skipped) {
                return groupIndex
            }
        }

        errorNoGroupByPosition(position)
    }

    fun getGroupIndexRange(context: GroupPositionContext): IntRange =
        0 until context.getGroupCount()

    fun getGroupItemPositionRange(context: GroupPositionContext, groupIndex: Int): IntRange {
        requireGroupIndex(context, groupIndex)

        val shift = context.getShift()
        val groupItemStartIndex = (0 until groupIndex).sumBy { context.getGroupItemCount(it) }
        val groupItemStartPosition = shift + groupItemStartIndex

        return groupItemStartPosition until groupItemStartPosition + context.getGroupItemCount(groupIndex)
    }

    fun getGroupItemIndexRange(context: GroupPositionContext, groupIndex: Int): IntRange {
        requireGroupIndex(context, groupIndex)

        return 0 until context.getGroupItemCount(groupIndex)
    }

    @CheckResult
    fun checkGroupIndex(context: GroupPositionContext, groupIndex: Int): Boolean =
        groupIndex in getGroupIndexRange(context)

    @CheckResult
    fun checkGroupItemPosition(context: GroupPositionContext, groupIndex: Int, position: Int): Boolean =
        position in getGroupItemPositionRange(context, groupIndex)

    @CheckResult
    fun checkGroupItemIndexRange(context: GroupPositionContext, groupIndex: Int, index: Int): Boolean =
        index in getGroupItemIndexRange(context, groupIndex)

    fun requireGroupIndex(context: GroupPositionContext, groupIndex: Int) {
        if (!checkGroupIndex(context, groupIndex)) {
            errorNoGroupByIndex(groupIndex)
        }
    }

    fun requireGroupItemPosition(context: GroupPositionContext, groupIndex: Int, position: Int) {
        if (!checkGroupItemPosition(context, groupIndex, position)) {
            errorNoItemByPosition(groupIndex, position)
        }
    }

    fun requireGroupItemIndex(context: GroupPositionContext, groupIndex: Int, index: Int) {
        if (!checkGroupItemIndexRange(context, groupIndex, index)) {
            errorNoItemByIndex(groupIndex, index)
        }
    }
}