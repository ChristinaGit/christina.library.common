package christina.common.position_adapter.core

import android.support.annotation.CheckResult
import christina.common.exception.reasonableException
import christina.common.position_adapter.core.context.PositionContext
import christina.common.position_adapter.core.context.getIndexRange
import christina.common.position_adapter.core.context.getPositionRange
import christina.common.position_adapter.core.exception.PositionAdapterErrorReasons.ILLEGAL_INDEX
import christina.common.position_adapter.core.exception.PositionAdapterErrorReasons.ILLEGAL_POSITION
import christina.common.position_adapter.core.exception.PositionAdapterException

open class PositionAdapter(open val context: PositionContext) {
    fun getPosition(index: Int): Int {
        requireIndex(index)

        return context.getPositionStart() + index
    }

    fun getIndex(position: Int): Int {
        requirePosition(position)

        return position - context.getPositionStart()
    }

    @CheckResult
    fun checkPosition(position: Int): Boolean =
        position in context.getPositionRange()

    @CheckResult
    fun checkIndex(index: Int): Boolean =
        index in context.getIndexRange()

    fun requirePosition(position: Int) {
        if (!checkPosition(position)) {
            throw PositionAdapterException(cause = reasonableException(ILLEGAL_POSITION, "Position $position doesn't belong to ${context.getPositionRange()}."))
        }
    }

    fun requireIndex(index: Int) {
        if (!checkIndex(index)) {
            throw PositionAdapterException(cause = reasonableException(ILLEGAL_INDEX, "Index $index doesn't belong to ${context.getIndexRange()}."))
        }
    }
}