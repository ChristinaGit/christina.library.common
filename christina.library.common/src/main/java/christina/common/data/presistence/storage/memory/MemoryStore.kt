package christina.common.data.presistence.storage.memory

import christina.common.data.presistence.storage.core.store.Store

abstract class MemoryStore<
    TEntity,
    in TEntityData,
    in TSelector>(entities: MutableCollection<TEntity> = mutableListOf()) :
    MemoryAbstractStore<TEntity, TEntityData, TSelector>(entities),
    Store<TEntity, TEntityData, TSelector> {

    override fun create(data: TEntityData): TEntity = createEntity().also { updateEntity(it, data) }

    protected abstract fun createEntity(): TEntity
}