package moe.christina.common.core.adapter.position.generic.context

interface PositionContext {
    companion object {
        const val SHIFT_NO_SHIFT: Int = 0
    }

    fun getItemCount(): Int

    fun getShift(): Int = SHIFT_NO_SHIFT
}