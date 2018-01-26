package christina.common.rx.event

import io.reactivex.subjects.Subject

operator fun <T : Event> Subject<T>.invoke(event: T) = onNext(event)

operator fun <T> Subject<ValueEvent<T>>.invoke(event: T) = onNext(ValueEvent(event))

operator fun Subject<UnitEvent>.invoke() = invoke(UnitEvent)