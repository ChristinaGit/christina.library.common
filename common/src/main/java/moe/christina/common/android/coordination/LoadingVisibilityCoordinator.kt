package moe.christina.common.android.coordination

import android.view.View
import moe.christina.common.android.coordination.visibility.LoadingVisibilityChanger
import moe.christina.common.core.coordination.state.StateChanger
import moe.christina.common.core.coordination.state.StateCoordinator

open class LoadingVisibilityCoordinator
@JvmOverloads
constructor(
    defaultStateChanger: StateChanger<View, Boolean>? = LoadingVisibilityChanger()
) : StateCoordinator<View, Boolean>(defaultStateChanger) {
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

    fun addContent(target: View) = add(CONTENT_VIEW_ID, target)
    fun addNoContent(target: View) = add(NO_CONTENT_VIEW_ID, target)
    fun addLoading(target: View) = add(LOADING_VIEW_ID, target)
    fun addError(target: View) = add(ERROR_VIEW_ID, target)

    fun removeContent() = remove(CONTENT_VIEW_ID)
    fun removeNoContent() = remove(NO_CONTENT_VIEW_ID)
    fun removeLoading() = remove(LOADING_VIEW_ID)
    fun removeError() = remove(ERROR_VIEW_ID)

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

    override fun isActualState(target: View, state: Boolean): Boolean =
        (state && target.visibility == View.VISIBLE) || super.isActualState(target, state)
}
