package christina.common.position_adapter.group

import christina.common.position_adapter.core.context.PositionContext

interface GroupPositionContext : PositionContext {
    fun getGroupCount(): Int
    fun getGroupPositionStart(group: Int): Int
    fun getGroupPositionEnd(group: Int): Int
}