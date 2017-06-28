package moe.christina.common.android.support

import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import moe.christina.common.android.ActivityResultProvider
import moe.christina.common.android.RequestPermissionsResultProvider
import moe.christina.common.android.event.ActivityResultEvent
import moe.christina.common.android.event.RequestPermissionsResultEvent

abstract class ObservableAppCompatActivity : RxAppCompatActivity(),
        ActivityResultProvider,
        RequestPermissionsResultProvider {
    override final val onRequestPermissionsResult: Observable<RequestPermissionsResultEvent>
        get() = onRequestPermissionsResultSubject.hide()

    override final val onActivityResult: Observable<ActivityResultEvent>
        get() = onActivityResultSubject.hide()

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        riseOnActivityResultEvent(ActivityResultEvent(requestCode, resultCode, data))
    }

    @CallSuper
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val event = RequestPermissionsResultEvent(requestCode, permissions.toList(), grantResults.toList())
        riseOnRequestPermissionsResultEvent(event)
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

    private fun riseOnActivityResultEvent(event: ActivityResultEvent) =
            onActivityResultSubject.onNext(event)

    private fun riseOnRequestPermissionsResultEvent(event: RequestPermissionsResultEvent) =
            onRequestPermissionsResultSubject.onNext(event)

    private val onActivityResultSubject: Subject<ActivityResultEvent> = PublishSubject.create()
    private val onRequestPermissionsResultSubject: Subject<RequestPermissionsResultEvent> = PublishSubject.create()
}