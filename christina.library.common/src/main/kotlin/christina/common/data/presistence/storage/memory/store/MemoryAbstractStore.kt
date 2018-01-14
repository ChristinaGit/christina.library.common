package christina.common.data.presistence.storage.memory.store

import android.support.annotation.CallSuper
import christina.common.data.presistence.storage.core.store.AbstractStore

abstract class MemoryAbstractStore<
    Entity,
    in EntityData,
    in Selector,
    out Query>
@JvmOverloads
constructor(
    protected val entities: MutableList<Entity> = mutableListOf()
) : AbstractStore<EntityData, Selector, Query> {

    @CallSuper
    override fun query(selector: Selector): Query =
        entities
            .filter { applySelector(it, selector) }
            .map(this::extractEntity)
            .let(this::transformToQuery)

    @CallSuper
    override fun queryAll(): Query =
        entities
            .map(this::extractEntity)
            .let(this::transformToQuery)

    @CallSuper
    override fun update(selector: Selector, data: EntityData) {
        val entitiesIterator = entities.listIterator()
        while (entitiesIterator.hasNext()) {
            val entity = entitiesIterator.next()
            if (applySelector(entity, selector)) {
                entitiesIterator.set(updateEntity(entity, data))
            }
        }
    }

    @CallSuper
    override fun updateAll(data: EntityData) {
        val entitiesIterator = entities.listIterator()
        while (entitiesIterator.hasNext()) {
            val entity = entitiesIterator.next()
            entitiesIterator.set(updateEntity(entity, data))
        }
    }

    @CallSuper
    override fun delete(selector: Selector) {
        entities.removeAll { applySelector(it, selector) }
    }

    @CallSuper
    override fun deleteAll() {
        entities.clear()
    }

    protected abstract fun applySelector(entity: Entity, selector: Selector): Boolean

    protected abstract fun updateEntity(entity: Entity, data: EntityData): Entity

    protected abstract fun transformToQuery(entities: Iterable<Entity>): Query

    protected abstract fun extractEntity(entity: Entity): Entity
}