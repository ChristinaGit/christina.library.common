package christina.common.rx

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer

fun Observer<Unit>.onNext() = onNext(Unit)

fun <T> ObservableSource<out ObservableSource<out T>>.switchOnNext() = Observable.switchOnNext(this)