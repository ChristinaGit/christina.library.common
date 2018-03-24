package christina.common.rx

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer

fun Observer<Unit>.onNext() = onNext(Unit)

fun <T> Observable<T>.onErrorComplete() = onErrorResumeNext(Observable.empty())

fun <T> Flowable<T>.onErrorComplete() = onErrorResumeNext(Flowable.empty())

fun <T> ObservableSource<out ObservableSource<out T>>.switchOnNext() = Observable.switchOnNext(this)