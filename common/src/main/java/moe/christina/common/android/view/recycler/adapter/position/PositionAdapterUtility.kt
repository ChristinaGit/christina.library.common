package moe.christina.common.android.view.recycler.adapter.position

fun PositionAdapter<*>.getItemPositionRange() =
    shiftProvider.shift.let {
        it..(it + itemCount - 1)
    }

fun PositionAdapter<*>.getItemIndexRange() = 0..(itemCount - 1)
