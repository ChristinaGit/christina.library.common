package christina.common.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

open class ImmutableObservableSourceTransformer<Stream>
@JvmOverloads
constructor(
    private val onSubscribe: Consumer<Any>? = null,
    private val onNext: Consumer<in Stream>? = null,
    private val onComplete: Action? = null,
    private val onError: Consumer<in Throwable>? = null
) : TransientObservableSourceTransformer<Stream, Stream>() {

    override fun apply(upstream: Completable): Completable =
        upstream
            .let { if (onSubscribe === null) it else it.doOnSubscribe(onSubscribe) }
            .let { if (onComplete === null) it else it.doOnComplete(onComplete) }
            .let { if (onError === null) it else it.doOnError(onError) }

    override fun apply(upstream: Single<Stream>): Single<Stream> =
        upstream
            .let { if (onSubscribe === null) it else it.doOnSubscribe(onSubscribe) }
            .let { if (onNext === null) it else it.doOnSuccess(onNext) }
            .let { if (onError === null) it else it.doOnError(onError) }

    override fun apply(upstream: Flowable<Stream>): Flowable<Stream> =
        upstream
            .let { if (onSubscribe === null) it else it.doOnSubscribe(onSubscribe) }
            .let { if (onNext === null) it else it.doOnNext(onNext) }
            .let { if (onComplete === null) it else it.doOnComplete(onComplete) }
            .let { if (onError === null) it else it.doOnError(onError) }

    override fun apply(upstream: Maybe<Stream>): Maybe<Stream> =
        upstream
            .let { if (onSubscribe === null) it else it.doOnSubscribe(onSubscribe) }
            .let { if (onNext === null) it else it.doOnSuccess(onNext) }
            .let { if (onComplete === null) it else it.doOnComplete(onComplete) }
            .let { if (onError === null) it else it.doOnError(onError) }

    override fun apply(upstream: Observable<Stream>): Observable<Stream> =
        upstream
            .let { if (onSubscribe === null) it else it.doOnSubscribe(onSubscribe) }
            .let { if (onNext === null) it else it.doOnNext(onNext) }
            .let { if (onComplete === null) it else it.doOnComplete(onComplete) }
            .let { if (onError === null) it else it.doOnError(onError) }
}