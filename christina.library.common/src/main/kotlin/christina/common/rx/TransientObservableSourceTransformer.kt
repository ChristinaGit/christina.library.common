package christina.common.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

abstract class TransientObservableSourceTransformer<Upstream, Downstream>
    : ObservableSourceTransformer<Upstream, Downstream> {
    abstract override fun apply(upstream: Completable): Completable
    abstract override fun apply(upstream: Single<Upstream>): Single<Downstream>
    abstract override fun apply(upstream: Flowable<Upstream>): Flowable<Downstream>
    abstract override fun apply(upstream: Maybe<Upstream>): Maybe<Downstream>
    abstract override fun apply(upstream: Observable<Upstream>): Observable<Downstream>
}
