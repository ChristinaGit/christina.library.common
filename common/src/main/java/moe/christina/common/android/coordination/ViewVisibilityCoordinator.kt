package moe.christina.common.android.coordination

import android.view.View
import moe.christina.common.android.coordination.visibility.VisibilityChanger

interface ViewVisibilityCoordinator {
    fun getView(id: Int): View?
    fun setView(id: Int, view: View?)

    fun setViewVisibility(id: Int, visible: Boolean)
    fun showAllViews()
    fun hideAllViews()

    fun isViewVisible(id: Int): Boolean
    val isVisible: Boolean

    fun invalidateView(id: Int)
    fun invalidateAllViews()

    fun getVisibilityChanger(id: Int): VisibilityChanger
    fun setVisibilityChanger(id: Int, changer: VisibilityChanger?)
    var visibilityChanger: VisibilityChanger

    fun showView(id: Int) {
        setViewVisibility(id, true)
    }

    fun hideView(id: Int) {
        setViewVisibility(id, false)
    }
}