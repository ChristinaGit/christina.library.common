package moe.christina.common.core.adapter.position.group.context

import moe.christina.common.core.adapter.position.generic.context.PositionContext

interface GroupPositionContext : PositionContext {
    fun getGroupCount(): Int
    fun getGroupItemCount(group: Int): Int
    override fun getItemCount(): Int = (0 until getGroupCount()).sumBy { getGroupItemCount(it) }
}