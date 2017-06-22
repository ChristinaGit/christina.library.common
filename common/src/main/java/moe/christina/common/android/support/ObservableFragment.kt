package moe.christina.common.android.support

import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import moe.christina.common.android.ActivityResultProvider
import moe.christina.common.android.RequestPermissionsResultProvider
import moe.christina.common.android.event.ActivityResultEvent
import moe.christina.common.android.event.RequestPermissionsResultEvent

abstract class ObservableFragment : RxFragment(),
        ActivityResultProvider,
        RequestPermissionsResultProvider {
    override val onRequestPermissionsResult: Observable<RequestPermissionsResultEvent>
        get() = requestPermissionsResultSubject.hide()

    override val onActivityResult: Observable<ActivityResultEvent>
        get() = activityResultSubject.hide()

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        activityResultSubject.onNext(ActivityResultEvent(requestCode, resultCode, data))
    }

    @CallSuper
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        requestPermissionsResultSubject.onNext(RequestPermissionsResultEvent(requestCode, permissions, grantResults))
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onInjectMembers()
    }

    @CallSuper
    override fun onDestroy() {
        onReleaseInjectedMembers()

        super.onDestroy()
    }

    @CallSuper
    protected open fun onInjectMembers() {
    }

    @CallSuper
    protected open fun onReleaseInjectedMembers() {
    }

    private val activityResultSubject: Subject<ActivityResultEvent> = PublishSubject.create()
    private val requestPermissionsResultSubject: Subject<RequestPermissionsResultEvent> = PublishSubject.create()
}