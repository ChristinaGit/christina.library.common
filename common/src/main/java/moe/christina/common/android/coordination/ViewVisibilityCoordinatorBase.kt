package moe.christina.common.android.coordination

import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.View
import collections.forEach
import moe.christina.common.android.coordination.visibility.SimpleVisibilityChanger
import moe.christina.common.android.coordination.visibility.VisibilityChanger

open class ViewVisibilityCoordinatorBase : ViewVisibilityCoordinator {
    companion object {
        @JvmStatic
        protected var reservedViewIdIndexer = 0

        @JvmStatic
        protected fun newReservedViewId() = --reservedViewIdIndexer

        @JvmStatic
        val defaultVisibilityChanger: VisibilityChanger by lazy(LazyThreadSafetyMode.NONE) {
            SimpleVisibilityChanger()
        }
    }

    final override fun getView(id: Int): View? = views[id, null]
    final override fun setView(id: Int, view: View?) {
        views.append(id, view)

        invalidateView(id)
    }

    final override var visibilityChanger: VisibilityChanger = defaultVisibilityChanger

    final override fun getVisibilityChanger(id: Int): VisibilityChanger = visibilityChangers[id, null] ?: visibilityChanger
    final override fun setVisibilityChanger(id: Int, changer: VisibilityChanger?) = visibilityChangers.append(id, changer)

    final override fun setViewVisibility(id: Int, visible: Boolean) {
        val currentVisibility = isViewVisible(id)
        if (visible != currentVisibility) {
            viewsVisibility.append(id, visible)

            invalidateView(id)
        }
    }

    final override fun isViewVisible(id: Int): Boolean = viewsVisibility[id, false]

    final override fun invalidateView(id: Int) {
        performChangeVisibility(id, isViewVisible(id))
    }

    protected fun invalidateView(id: Int, view: View) {
        performChangeVisibility(id, view, isViewVisible(id))
    }

    final override fun showAllViews() {
        viewsVisibility.forEach { id, visible -> if (!visible) setViewVisibility(id, true) }
    }

    final override fun hideAllViews() {
        viewsVisibility.forEach { id, visible -> if (visible) setViewVisibility(id, false) }
    }

    final override fun invalidateAllViews() {
        views.forEach { id, view -> invalidateView(id, view) }
    }

    final override val isVisible: Boolean
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

    private val views = SparseArray<View>()
    private val viewsVisibility = SparseBooleanArray()
    private val visibilityChangers = SparseArray<VisibilityChanger>()
}