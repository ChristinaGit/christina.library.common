package moe.christina.common.position_adapter.group.context

import moe.christina.common.position_adapter.generic.context.PositionContext

interface GroupPositionContext : PositionContext {
    fun getGroupCount(): Int
    fun getGroupItemCount(group: Int): Int
    override fun getItemCount(): Int = (0 until getGroupCount()).sumBy { getGroupItemCount(it) }
}