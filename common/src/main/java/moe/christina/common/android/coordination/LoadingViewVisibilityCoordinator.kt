package moe.christina.common.android.coordination

import android.view.View

open class LoadingViewVisibilityCoordinator : ViewVisibilityCoordinatorBase() {
    companion object {
        @JvmStatic
        val CONTENT_VIEW_ID = newReservedViewId()
        @JvmStatic
        val NO_CONTENT_VIEW_ID = newReservedViewId()
        @JvmStatic
        val LOADING_VIEW_ID = newReservedViewId()
        @JvmStatic
        val ERROR_VIEW_ID = newReservedViewId()
    }

    var contentVisibilityChanger
        get() = getVisibilityChanger(CONTENT_VIEW_ID)
        set(value) = setVisibilityChanger(CONTENT_VIEW_ID, value)
    var noContentVisibilityChanger
        get() = getVisibilityChanger(NO_CONTENT_VIEW_ID)
        set(value) = setVisibilityChanger(NO_CONTENT_VIEW_ID, value)
    var loadingVisibilityChanger
        get() = getVisibilityChanger(LOADING_VIEW_ID)
        set(value) = setVisibilityChanger(LOADING_VIEW_ID, value)
    var errorVisibilityChanger
        get() = getVisibilityChanger(ERROR_VIEW_ID)
        set(value) = setVisibilityChanger(ERROR_VIEW_ID, value)

    var contentView: View?
        get() = getView(CONTENT_VIEW_ID)
        set(value) = setView(CONTENT_VIEW_ID, value)
    var noContentView: View?
        get() = getView(NO_CONTENT_VIEW_ID)
        set(value) = setView(NO_CONTENT_VIEW_ID, value)
    var loadingView: View?
        get() = getView(LOADING_VIEW_ID)
        set(value) = setView(LOADING_VIEW_ID, value)
    var errorView: View?
        get() = getView(ERROR_VIEW_ID)
        set(value) = setView(ERROR_VIEW_ID, value)

    var isContentViewVisible
        get() = isViewVisible(CONTENT_VIEW_ID)
        set(value) = setViewVisibility(CONTENT_VIEW_ID, value)
    var isNoContentViewVisible
        get() = isViewVisible(NO_CONTENT_VIEW_ID)
        set(value) = setViewVisibility(NO_CONTENT_VIEW_ID, value)
    var isLoadingViewVisible
        get() = isViewVisible(LOADING_VIEW_ID)
        set(value) = setViewVisibility(LOADING_VIEW_ID, value)
    var isErrorViewVisible
        get() = isViewVisible(ERROR_VIEW_ID)
        set(value) = setViewVisibility(ERROR_VIEW_ID, value)

    fun setContentViewVisibility(visible: Boolean) = setViewVisibility(CONTENT_VIEW_ID, visible)
    fun setNoContentViewVisibility(visible: Boolean) = setViewVisibility(NO_CONTENT_VIEW_ID, visible)
    fun setLoadingViewVisibility(visible: Boolean) = setViewVisibility(LOADING_VIEW_ID, visible)
    fun setErrorViewVisibility(visible: Boolean) = setViewVisibility(ERROR_VIEW_ID, visible)

    fun invalidateContentView() = invalidateView(CONTENT_VIEW_ID)
    fun invalidateNoContentView() = invalidateView(NO_CONTENT_VIEW_ID)
    fun invalidateLoadingView() = invalidateView(LOADING_VIEW_ID)
    fun invalidateErrorView() = invalidateView(ERROR_VIEW_ID)

    fun showContentView(hasContent: Boolean = true) {
        if (hasContent) {
            isContentViewVisible = true
            isNoContentViewVisible = false
        } else {
            isContentViewVisible = false
            isNoContentViewVisible = true
        }

        isLoadingViewVisible = false
        isErrorViewVisible = false
    }

    fun showErrorView() {
        isContentViewVisible = false
        isNoContentViewVisible = false
        isLoadingViewVisible = false
        isErrorViewVisible = true
    }

    fun showLoadingView() {
        isContentViewVisible = false
        isNoContentViewVisible = false
        isLoadingViewVisible = true
        isErrorViewVisible = false
    }
}
