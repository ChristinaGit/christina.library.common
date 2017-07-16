package moe.christina.common.android.support

import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.support.v7.app.AppCompatActivity
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle.bindUntilEvent
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid.bindActivity
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import moe.christina.common.android.ActivityResultProvider
import moe.christina.common.android.RequestPermissionsResultProvider
import moe.christina.common.android.event.ActivityResultEvent
import moe.christina.common.android.event.RequestPermissionsResultEvent

abstract class ObservableAppCompatActivity : AppCompatActivity(),
    ActivityResultProvider,
    RequestPermissionsResultProvider,
    LifecycleProvider<ActivityEvent> {
    final override val onRequestPermissionsResult: Observable<RequestPermissionsResultEvent>
        get() = onRequestPermissionsResultSubject.hide()

    final override val onActivityResult: Observable<ActivityResultEvent>
        get() = onActivityResultSubject.hide()

    @CheckResult
    final override fun lifecycle(): Observable<ActivityEvent> = lifecycleSubject.hide()

    @CheckResult
    final override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> =
        bindUntilEvent<T, ActivityEvent>(lifecycleSubject, event)

    @CheckResult
    final override fun <T> bindToLifecycle(): LifecycleTransformer<T> = bindActivity<T>(lifecycleSubject)

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
        onInjectMembers()

        super.onCreate(savedInstanceState)

        riseLifecycleEvent(ActivityEvent.CREATE)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()

        riseLifecycleEvent(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()

        riseLifecycleEvent(ActivityEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        riseLifecycleEvent(ActivityEvent.PAUSE)

        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        riseLifecycleEvent(ActivityEvent.STOP)

        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        riseLifecycleEvent(ActivityEvent.DESTROY)

        super.onDestroy()

        onReleaseInjectedMembers()
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

    private fun riseLifecycleEvent(event: ActivityEvent) =
        lifecycleSubject.onNext(event)

    private val onActivityResultSubject: Subject<ActivityResultEvent> = PublishSubject.create()
    private val onRequestPermissionsResultSubject: Subject<RequestPermissionsResultEvent> = PublishSubject.create()
    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()
}