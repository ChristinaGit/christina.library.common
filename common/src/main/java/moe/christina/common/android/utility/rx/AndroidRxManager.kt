package moe.christina.common.android.utility.rx

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Flowable
import io.reactivex.Observable

class AndroidRxManager<TEvent>(private val lifecycleProvider: LifecycleProvider<TEvent>) : RxManager {
    override fun <T> autoManage(observable: Observable<T>): Observable<T> {
        return observable.bindToLifecycle(lifecycleProvider)
    }

    override fun <T> autoManage(observable: Flowable<T>): Flowable<T> {
        return observable.bindToLifecycle(lifecycleProvider)
    }
}