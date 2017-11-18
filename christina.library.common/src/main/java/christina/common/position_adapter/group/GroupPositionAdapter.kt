package christina.common.position_adapter.group

import android.support.annotation.CheckResult
import christina.common.position_adapter.PositionAdapter

open class GroupPositionAdapter(override val context: GroupPositionContext) : PositionAdapter(context) {
    fun getGroupPosition(group: Int, index: Int): Int {
        requireGroup(group)
        requireGroupIndex(group, index)

        return context.getGroupPositionStart(group) + index
    }

    fun getGroupIndex(group: Int, position: Int): Int {
        requireGroup(group)
        requireGroupPosition(group, position)

        return position - context.getGroupPositionStart(group)
    }

    fun findGroupByPosition(position: Int): Int? {
        requirePosition(position)

        return context.getGroupRange().firstOrNull { position in context.getGroupPositionRange(it) }
    }

    fun findGroupByIndex(index: Int): Int? {
        requireIndex(index)

        return context.getGroupRange().firstOrNull { index in context.getGroupIndexRange(it) }
    }

    @CheckResult
    fun checkGroup(group: Int): Boolean =
        group in context.getGroupRange()

    @CheckResult
    fun checkGroupPosition(group: Int, position: Int): Boolean =
        position in context.getGroupPositionRange(group)

    @CheckResult
    fun checkGroupIndex(group: Int, index: Int): Boolean =
        index in context.getGroupIndexRange(group)

    fun requireGroup(group: Int) =
        require(
            checkGroup(group),
            { "Illegal group. group: $group" })

    fun requireGroupPosition(group: Int, position: Int) =
        require(
            checkGroupPosition(group, position),
            { "Illegal group position. group: $group, position = $position" })

    fun requireGroupIndex(group: Int, index: Int) =
        require(
            checkGroupIndex(group, index),
            { "Illegal group index. group: $group, index = $index" })
}