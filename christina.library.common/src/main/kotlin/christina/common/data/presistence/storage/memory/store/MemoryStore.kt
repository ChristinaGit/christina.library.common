package christina.common.data.presistence.storage.memory.store

import android.support.annotation.CallSuper
import christina.common.data.presistence.storage.core.store.Store

abstract class MemoryStore<
    Entity,
    in EntityData,
    in Selector>(entities: MutableCollection<Entity> = mutableListOf()) :
    MemoryAbstractStore<Entity, EntityData, Selector>(entities),
    Store<Entity, EntityData, Selector> {

    @CallSuper
    override fun create(data: EntityData): Entity =
        createEntity().also {
            updateEntity(it, data)
            entities.add(it)
        }

    protected abstract fun createEntity(): Entity
}