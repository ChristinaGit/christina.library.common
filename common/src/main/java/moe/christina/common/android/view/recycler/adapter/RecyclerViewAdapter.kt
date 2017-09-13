package moe.christina.common.android.view.recycler.adapter

import android.content.Context
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class RecyclerViewAdapter<VH : ViewHolder> : Adapter<VH>() {
    companion object {
        @JvmStatic
        val UNKNOWN_VIEW_TYPE = 0
        @JvmStatic
        protected var viewTypeIndexer = UNKNOWN_VIEW_TYPE

        @JvmStatic
        protected fun newViewType() = ++viewTypeIndexer
    }

    @CheckResult
    protected fun inflateView(@LayoutRes layoutId: Int, parent: ViewGroup): View {
        return layoutInflaterFrom(parent.context).inflate(layoutId, parent, false)
    }

    @CallSuper
    protected open fun onPrepareLayoutInflater(layoutInflater: LayoutInflater) {
    }

    @CheckResult
    private fun layoutInflaterFrom(context: Context): LayoutInflater {
        if (layoutInflater === null) {
            layoutInflater = LayoutInflater.from(context).apply {
                onPrepareLayoutInflater(this)
            }
        }

        return layoutInflater!!
    }

    private var layoutInflater: LayoutInflater? = null
}
