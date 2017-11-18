package christina.common.position_adapter

import android.support.annotation.CheckResult

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

    fun requirePosition(position: Int) =
        require(
            checkPosition(position),
            { "Illegal position. position: $position" })

    fun requireIndex(index: Int) =
        require(
            checkIndex(index),
            { "Illegal index. index: $index" })
}