package moe.christina.common.android

import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.Arrays
import java.util.Objects

data class ActivityResultEvent(val requestCode: Int, val resultCode: Int, var data: Intent)

interface ActivityResultProvider {
    val onActivityResult: Observable<ActivityResultEvent>
}

data class RequestPermissionsResultEvent(val requestCode: Int, val permissions: Array<String>, var grantResults: IntArray) {
    override fun equals(other: Any?): Boolean {
        if (other !is RequestPermissionsResultEvent)
            return false

        return requestCode == other.requestCode
                && Arrays.equals(permissions, other.permissions)
                && Arrays.equals(grantResults, other.grantResults)
    }

    override fun hashCode(): Int {
        return Objects.hash(
                requestCode,
                Arrays.hashCode(permissions),
                Arrays.hashCode(grantResults))
    }
}

interface RequestPermissionsResultProvider {
    val onRequestPermissionsResult: Observable<RequestPermissionsResultEvent>
}

abstract class ObservableActivity : RxAppCompatActivity(),
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

    private val activityResultSubject: Subject<ActivityResultEvent>
            = PublishSubject.create()
    private val requestPermissionsResultSubject: Subject<RequestPermissionsResultEvent>
            = PublishSubject.create()
}