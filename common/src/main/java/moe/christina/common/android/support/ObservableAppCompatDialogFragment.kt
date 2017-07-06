package moe.christina.common.android.support

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.support.v7.app.AppCompatDialogFragment
import android.view.View
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid.bindFragment
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import moe.christina.common.android.ActivityResultProvider
import moe.christina.common.android.RequestPermissionsResultProvider
import moe.christina.common.android.event.ActivityResultEvent
import moe.christina.common.android.event.RequestPermissionsResultEvent

abstract class ObservableAppCompatDialogFragment : AppCompatDialogFragment(),
    ActivityResultProvider,
    RequestPermissionsResultProvider,
    LifecycleProvider<FragmentEvent> {
    final override  val onRequestPermissionsResult: Observable<RequestPermissionsResultEvent>
        get() = onRequestPermissionsResultSubject.hide()

    final override  val onActivityResult: Observable<ActivityResultEvent>
        get() = onActivityResultSubject.hide()

    @CheckResult
    final override  fun lifecycle(): Observable<FragmentEvent> = lifecycleSubject.hide()

    @CheckResult
    final override  fun <T> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> =
        RxLifecycle.bindUntilEvent<T, FragmentEvent>(lifecycleSubject, event)

    @CheckResult
    final override  fun <T> bindToLifecycle(): LifecycleTransformer<T> = bindFragment<T>(lifecycleSubject)

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
    override fun onAttach(context: Context?) {
        onInjectMembers()

        super.onAttach(context)

        riseLifecycleEvent(FragmentEvent.ATTACH)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        riseLifecycleEvent(FragmentEvent.CREATE)
    }

    @CallSuper
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        riseLifecycleEvent(FragmentEvent.CREATE_VIEW)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()

        riseLifecycleEvent(FragmentEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()

        riseLifecycleEvent(FragmentEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        riseLifecycleEvent(FragmentEvent.PAUSE)

        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        riseLifecycleEvent(FragmentEvent.STOP)

        super.onStop()
    }

    @CallSuper
    override fun onDestroyView() {
        riseLifecycleEvent(FragmentEvent.DESTROY_VIEW)

        super.onDestroyView()
    }

    @CallSuper
    override fun onDestroy() {
        riseLifecycleEvent(FragmentEvent.DESTROY)

        super.onDestroy()
    }

    override fun onDetach() {
        riseLifecycleEvent(FragmentEvent.DETACH)

        super.onDetach()

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

    private fun riseLifecycleEvent(event: FragmentEvent) =
        lifecycleSubject.onNext(event)

    private val onActivityResultSubject: Subject<ActivityResultEvent> = PublishSubject.create()
    private val onRequestPermissionsResultSubject: Subject<RequestPermissionsResultEvent> = PublishSubject.create()
    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()
}