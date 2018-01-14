package christina.common.data.presistence.storage.memory.store

import android.support.annotation.CallSuper
import christina.common.data.presistence.storage.core.store.Store

abstract class MemoryStore<
    Entity,
    in EntityData,
    in Selector,
    out Query>(entities: MutableList<Entity> = mutableListOf()) :
    MemoryAbstractStore<Entity, EntityData, Selector, Query>(entities),
    Store<Entity, EntityData, Selector, Query> {

    @CallSuper
    override fun create(data: EntityData): Entity = synchronized(entities) {
        createEntity().also {
            updateEntity(it, data)
            entities += it
        }
    }

    protected abstract fun createEntity(): Entity
}