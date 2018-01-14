package christina.common.data.presistence.storage.observable_wrapper.store

import christina.common.data.presistence.storage.core.store.Store
import io.reactivex.Observable

open class ObservableWrapperStore<
    out Entity,
    in EntityData,
    in Selector,
    Query>
constructor(
    override val internalStore: Store<Entity, EntityData, Selector, Query>
) : ObservableWrapperAbstractStore<EntityData, Selector, Query>(internalStore),
    Store<Entity, EntityData, Selector, Observable<Query>> {

    override fun create(data: EntityData): Entity =
        internalStore.create(data).also {
            onEntriesChanged()
        }
}