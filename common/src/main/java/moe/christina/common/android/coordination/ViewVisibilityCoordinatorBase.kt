package moe.christina.common.android.coordination

import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.View
import collections.forEach
import moe.christina.common.android.coordination.visibility.SimpleVisibilityChanger
import moe.christina.common.android.coordination.visibility.VisibilityChanger

open class ViewVisibilityCoordinatorBase : ViewVisibilityCoordinator {
    companion object {
        val defaultVisibilityChanger: VisibilityChanger by lazy(LazyThreadSafetyMode.NONE) {
            SimpleVisibilityChanger()
        }
    }

    private val views = SparseArray<View>()
    private val viewsVisibility = SparseBooleanArray()
    private val visibilityChangers = SparseArray<VisibilityChanger>()

    override final fun getView(id: Int): View? = views[id, null]
    override final fun setView(id: Int, view: View?) {
        views.append(id, view)

        invalidateView(id)
    }

    override final var visibilityChanger: VisibilityChanger = defaultVisibilityChanger

    override final fun getVisibilityChanger(id: Int): VisibilityChanger = visibilityChangers[id, null] ?: visibilityChanger
    override final fun setVisibilityChanger(id: Int, changer: VisibilityChanger?) = visibilityChangers.append(id, changer)

    override fun setViewVisibility(id: Int, visible: Boolean) {
        val currentVisibility = isViewVisible(id)
        if (visible != currentVisibility) {
            viewsVisibility.append(id, visible)

            invalidateView(id)
        }
    }

    override final fun isViewVisible(id: Int): Boolean = viewsVisibility[id, false]

    override fun invalidateView(id: Int) {
        performChangeVisibility(id, isViewVisible(id))
    }

    protected fun invalidateView(id: Int, view: View) {
        performChangeVisibility(id, view, isViewVisible(id))
    }

    override fun showAllViews() {
        viewsVisibility.forEach { id, visible -> if (!visible) setViewVisibility(id, true) }
    }

    override fun hideAllViews() {
        viewsVisibility.forEach { id, visible -> if (visible) setViewVisibility(id, false) }
    }

    override fun invalidateAllViews() {
        views.forEach { id, view -> invalidateView(id, view) }
    }

    override val isVisible: Boolean
        get() = (0..viewsVisibility.size()).all { viewsVisibility.valueAt(it) }

    protected fun performChangeVisibility(id: Int, visible: Boolean) {
        val view = getView(id)

        if (view != null) {
            performChangeVisibility(id, view, visible)
        }
    }

    protected fun performChangeVisibility(id: Int, view: View, visible: Boolean) {
        getVisibilityChanger(id).changeVisibility(view, visible)
    }
}