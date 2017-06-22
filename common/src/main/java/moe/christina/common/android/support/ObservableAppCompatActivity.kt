package moe.christina.common.android.support

import android.os.Bundle
import android.support.annotation.CallSuper
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import moe.christina.common.android.ActivityResultProvider
import moe.christina.common.android.RequestPermissionsResultProvider
import moe.christina.common.android.event.ActivityResultEvent
import moe.christina.common.android.event.RequestPermissionsResultEvent

abstract class ObservableAppCompatActivity : RxAppCompatActivity(),
        ActivityResultProvider,
        RequestPermissionsResultProvider {
    override val onRequestPermissionsResult: io.reactivex.Observable<RequestPermissionsResultEvent>
        get() = requestPermissionsResultSubject.hide()

    override val onActivityResult: io.reactivex.Observable<ActivityResultEvent>
        get() = activityResultSubject.hide()

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent) {
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