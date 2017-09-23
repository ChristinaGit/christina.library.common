package moe.christina.common.android.support

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.support.v4.app.Fragment
import android.view.View
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import moe.christina.common.android.ActivityResultProvider
import moe.christina.common.android.RequestPermissionsResultProvider
import moe.christina.common.android.event.ActivityResultEventData
import moe.christina.common.android.event.RequestPermissionsResultEventData
import moe.christina.common.event.Event
import moe.christina.common.event.Events
import moe.christina.common.event.invoke

abstract class ObservableFragment : Fragment(),
    ActivityResultProvider,
    RequestPermissionsResultProvider,
    LifecycleProvider<FragmentEvent> {
    final override val onRequestPermissionsResult: Event<RequestPermissionsResultEventData>
        get() = onRequestPermissionsResultEvent

    final override val onActivityResult: Event<ActivityResultEventData>
        get() = onActivityResultEvent

    @CheckResult
    final override fun lifecycle(): Observable<FragmentEvent> = lifecycleSubject.hide()

    @CheckResult
    final override fun <T> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> =
        RxLifecycle.bindUntilEvent<T, FragmentEvent>(lifecycleSubject, event)

    @CheckResult
    final override fun <T> bindToLifecycle(): LifecycleTransformer<T> =
        RxLifecycleAndroid.bindFragment<T>(lifecycleSubject)

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        onActivityResultEvent(ActivityResultEventData(requestCode, resultCode, data))
    }

    @CallSuper
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val event = RequestPermissionsResultEventData(requestCode, permissions.toList(), grantResults.toList())
        onRequestPermissionsResultEvent(event)
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

    private fun riseLifecycleEvent(event: FragmentEvent) = lifecycleSubject.onNext(event)

    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()
    private val onRequestPermissionsResultEvent = Events.basic<RequestPermissionsResultEventData>()
    private val onActivityResultEvent = Events.basic<ActivityResultEventData>()
}