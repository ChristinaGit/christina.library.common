package moe.christina.common.android.coordination

import android.view.View
import moe.christina.common.core.coordination.state.StateCoordinator

open class LoadingVisibilityCoordinator : StateCoordinator<View, Boolean>() {
    companion object {
        @JvmStatic
        private var idIndexer = 0

        @JvmStatic
        protected fun newId() = idIndexer++

        @JvmStatic
        val CONTENT_VIEW_ID = newId()
        @JvmStatic
        val NO_CONTENT_VIEW_ID = newId()
        @JvmStatic
        val LOADING_VIEW_ID = newId()
        @JvmStatic
        val ERROR_VIEW_ID = newId()
    }

    var contentVisibilityChanger
        get() = getStateChanger(CONTENT_VIEW_ID)
        set(value) = setStateChanger(CONTENT_VIEW_ID, value)
    var noContentVisibilityChanger
        get() = getStateChanger(NO_CONTENT_VIEW_ID)
        set(value) = setStateChanger(NO_CONTENT_VIEW_ID, value)
    var loadingVisibilityChanger
        get() = getStateChanger(LOADING_VIEW_ID)
        set(value) = setStateChanger(LOADING_VIEW_ID, value)
    var errorVisibilityChanger
        get() = getStateChanger(ERROR_VIEW_ID)
        set(value) = setStateChanger(ERROR_VIEW_ID, value)

    var isContentVisible: Boolean?
        get() = getState(CONTENT_VIEW_ID)
        set(value) = setState(CONTENT_VIEW_ID, value ?: false)
    var isNoContentVisible: Boolean?
        get() = getState(NO_CONTENT_VIEW_ID)
        set(value) = setState(NO_CONTENT_VIEW_ID, value ?: false)
    var isLoadingVisible: Boolean?
        get() = getState(LOADING_VIEW_ID)
        set(value) = setState(LOADING_VIEW_ID, value ?: false)
    var isErrorVisible: Boolean?
        get() = getState(ERROR_VIEW_ID)
        set(value) = setState(ERROR_VIEW_ID, value ?: false)

    fun invalidateContent() = invalidate(CONTENT_VIEW_ID)
    fun invalidateNoContent() = invalidate(NO_CONTENT_VIEW_ID)
    fun invalidateLoading() = invalidate(LOADING_VIEW_ID)
    fun invalidateError() = invalidate(ERROR_VIEW_ID)

    fun showContent(hasContent: Boolean = true) {
        if (hasContent) {
            isContentVisible = true
            isNoContentVisible = false
        } else {
            isContentVisible = false
            isNoContentVisible = true
        }

        isLoadingVisible = false
        isErrorVisible = false
    }

    fun showError() {
        isContentVisible = false
        isNoContentVisible = false
        isLoadingVisible = false
        isErrorVisible = true
    }

    fun showLoading() {
        isContentVisible = false
        isNoContentVisible = false
        isLoadingVisible = true
        isErrorVisible = false
    }
}
