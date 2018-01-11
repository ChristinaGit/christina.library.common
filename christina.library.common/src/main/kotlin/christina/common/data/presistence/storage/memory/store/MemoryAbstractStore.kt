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
    protected val entities: MutableCollection<Entity> = mutableListOf()
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
        entities
            .filter { applySelector(it, selector) }
            .forEach { updateEntity(it, data) }
    }

    @CallSuper
    override fun updateAll(data: EntityData) {
        entities.forEach { updateEntity(it, data) }
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

    protected abstract fun updateEntity(entity: Entity, data: EntityData)

    protected abstract fun transformToQuery(entities: Iterable<Entity>): Query

    protected abstract fun extractEntity(entity: Entity): Entity
}