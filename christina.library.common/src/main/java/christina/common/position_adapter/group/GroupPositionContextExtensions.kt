package christina.common.position_adapter.group

fun GroupPositionContext.getGroupLength(group: Int) = getGroupPositionEnd(group) - getGroupPositionStart(group)

fun GroupPositionContext.getGroupPositionRange(group: Int) = getGroupPositionStart(group)..getGroupPositionEnd(group)

fun GroupPositionContext.getGroupIndexRange(group: Int) = 0..getGroupLength(group)

fun GroupPositionContext.getGroupRange() = 0 until getGroupCount()