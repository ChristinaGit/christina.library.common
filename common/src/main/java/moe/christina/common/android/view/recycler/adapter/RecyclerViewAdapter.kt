package moe.christina.common.android.view.recycler.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class RecyclerViewAdapter<TVH : RecyclerView.ViewHolder> : RecyclerView.Adapter<TVH>() {
    companion object {
        @JvmStatic
        val UNKNOWN_VIEW_TYPE = 0;
        @JvmStatic
        protected var viewTypeIndexer = UNKNOWN_VIEW_TYPE

        @JvmStatic
        protected fun newViewType() = ++viewTypeIndexer
    }

    protected fun inflateView(@LayoutRes layoutId: Int, parent: ViewGroup): View {
        if (_layoutInflater === null) {
            _layoutInflater = LayoutInflater.from(parent.context)
        }

        return _layoutInflater!!.inflate(layoutId, parent, false)
    }

    private var _layoutInflater: LayoutInflater? = null
}
