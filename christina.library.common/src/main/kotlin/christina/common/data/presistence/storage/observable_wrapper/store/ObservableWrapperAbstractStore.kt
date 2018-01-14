package christina.common.data.presistence.storage.observable_wrapper.store

import android.support.annotation.CallSuper
import christina.common.data.presistence.storage.core.store.AbstractStore
import christina.common.rx.onNext
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

open class ObservableWrapperAbstractStore<
    in EntityData,
    in Selector,
    Query>
constructor(
    protected open val internalStore: AbstractStore<EntityData, Selector, Query>
) : AbstractStore<EntityData, Selector, Observable<Query>> {
    protected val entitiesSubject: Subject<Unit> = BehaviorSubject.createDefault(Unit)

    @CallSuper
    override fun query(selector: Selector): Observable<Query> =
        entitiesSubject.map { internalStore.query(selector) }

    @CallSuper
    override fun queryAll(): Observable<Query> = entitiesSubject.map { internalStore.queryAll() }

    @CallSuper
    override fun update(selector: Selector, data: EntityData) {
        internalStore.update(selector, data)

        onEntriesChanged()
    }

    @CallSuper
    override fun updateAll(data: EntityData) {
        internalStore.updateAll(data)

        onEntriesChanged()
    }

    @CallSuper
    override fun delete(selector: Selector) {
        internalStore.delete(selector)

        onEntriesChanged()
    }

    @CallSuper
    override fun deleteAll() {
        internalStore.deleteAll()

        onEntriesChanged()
    }

    @CallSuper
    protected open fun onEntriesChanged() {
        entitiesSubject.onNext()
    }
}