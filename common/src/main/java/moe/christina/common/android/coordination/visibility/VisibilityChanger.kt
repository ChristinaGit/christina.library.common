package moe.christina.common.android.coordination.visibility

import android.support.annotation.CallSuper
import android.view.View
import moe.christina.common.core.coordination.state.StateChanger

open class VisibilityChanger
@JvmOverloads
constructor(
    val invisibleVisibility: Int = VisibilityChanger.DEFAULT_INVISIBLE_VISIBILITY
) : StateChanger<View, Boolean> {
    companion object {
        @JvmStatic
        val DEFAULT_INVISIBLE_VISIBILITY: Int = View.GONE
    }

    @CallSuper
    override fun changeState(target: View, state: Boolean) {
        target.visibility = if (state) View.VISIBLE else invisibleVisibility
    }
}
