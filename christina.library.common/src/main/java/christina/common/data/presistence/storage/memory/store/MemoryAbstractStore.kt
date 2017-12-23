package christina.common.data.presistence.storage.memory.store

import android.support.annotation.CallSuper
import christina.common.data.presistence.storage.core.store.AbstractStore
import christina.common.data.presistence.storage.core.store.query.StoreQuery
import christina.common.data.presistence.storage.core.store.query.storeQuery

abstract class MemoryAbstractStore<
    Entity,
    in EntityData,
    in Selector>
@JvmOverloads
constructor(
    protected val entities: MutableCollection<Entity> = mutableListOf()
) : AbstractStore<Entity, EntityData, Selector> {

    final override fun query(selector: Selector): StoreQuery<Entity> =
        entities
            .filter { applySelector(it, selector) }
            .map(this::copyEntry)
            .let(this::transformToQuery)

    final override fun queryAll(): StoreQuery<Entity> =
        entities
            .map(this::copyEntry)
            .let(this::transformToQuery)

    final override fun update(selector: Selector, data: EntityData) {
        entities
            .filter { applySelector(it, selector) }
            .forEach { updateEntity(it, data) }
    }

    final override fun updateAll(data: EntityData) {
        entities.forEach { updateEntity(it, data) }
    }

    final override fun delete(selector: Selector) {
        entities.removeAll { applySelector(it, selector) }
    }

    @CallSuper
    final override fun deleteAll() {
        entities.clear()
    }

    protected abstract fun applySelector(entity: Entity, selector: Selector): Boolean

    protected abstract fun updateEntity(entity: Entity, data: EntityData)

    protected open fun transformToQuery(entities: Iterable<Entity>): StoreQuery<Entity> = storeQuery(entities)

    protected abstract fun copyEntry(entity: Entity): Entity
}