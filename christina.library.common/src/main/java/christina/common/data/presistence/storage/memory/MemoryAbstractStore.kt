package christina.common.data.presistence.storage.memory

import android.support.annotation.CallSuper
import christina.common.data.presistence.storage.core.store.AbstractStore
import christina.common.data.presistence.storage.core.store.StoreQuery

abstract class MemoryAbstractStore<
    TEntity,
    in TEntityData,
    in TSelector>(
    protected val entities: MutableCollection<TEntity> = mutableListOf()
) : AbstractStore<TEntity, TEntityData, TSelector> {

    override fun query(selector: TSelector): StoreQuery<TEntity> =
        entities.filter { applySelector(it, selector) }.let(this::transformToQuery)

    override fun queryAll(): StoreQuery<TEntity> =
        entities.let(this::transformToQuery)

    override fun update(selector: TSelector, data: TEntityData) {
        entities.filter { applySelector(it, selector) }.forEach { updateEntity(it, data) }
    }

    override fun updateAll(data: TEntityData) {
        entities.forEach { updateEntity(it, data) }
    }

    @CallSuper
    override fun delete(selector: TSelector) {
        entities.removeAll { applySelector(it, selector) }
    }

    @CallSuper
    override fun deleteAll() {
        entities.clear()
    }

    protected abstract fun applySelector(entity: TEntity, selector: TSelector): Boolean

    protected abstract fun updateEntity(entity: TEntity, data: TEntityData)

    protected abstract fun transformToQuery(entities: Iterable<TEntity>): StoreQuery<TEntity>
}