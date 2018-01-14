package christina.common.rx

import io.reactivex.Observer

fun Observer<Unit>.onNext() = onNext(Unit)