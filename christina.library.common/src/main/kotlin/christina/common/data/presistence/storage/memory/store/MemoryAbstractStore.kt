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
    override fun query(selector: Selector): Query = synchronized(entities) {
        prepareQuery(getEntitiesSnapshot(selector))
    }

    @CallSuper
    override fun queryAll(): Query = synchronized(entities) {
        prepareQuery(getEntitiesSnapshot())
    }

    @CallSuper
    override fun update(selector: Selector, data: EntityData) = synchronized(entities) {
        getEntitiesSnapshot(selector)
            .forEach { updateEntity(it, data) }
    }

    @CallSuper
    override fun updateAll(data: EntityData) = synchronized(entities) {
        getEntitiesSnapshot()
            .forEach { updateEntity(it, data) }
    }

    @CallSuper
    override fun delete(selector: Selector) {
        synchronized(entities) {
            entities.removeAll(getEntitiesSnapshot(selector))
        }
    }

    @CallSuper
    override fun deleteAll() = synchronized(entities) {
        entities.clear()
    }

    protected fun getEntitiesSnapshot(): List<Entity> = synchronized(entities) {
        entities.toList()
    }

    protected fun getEntitiesSnapshot(selector: Selector): List<Entity> = synchronized(entities) {
        applySelector(getEntitiesSnapshot(), selector)
    }

    protected fun prepareQuery(entities: Iterable<Entity>): Query =
        entities
            .map(::extractEntity)
            .let(::transformToQuery)

    protected abstract fun applySelector(
        entities: List<Entity>,
        selector: Selector
    ): List<Entity>

    protected abstract fun updateEntity(entity: Entity, data: EntityData)

    protected abstract fun transformToQuery(entities: Iterable<Entity>): Query

    protected abstract fun extractEntity(entity: Entity): Entity
}