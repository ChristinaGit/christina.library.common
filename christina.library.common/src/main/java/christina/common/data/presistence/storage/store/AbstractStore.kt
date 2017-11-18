package christina.common.data.presistence.storage.store

interface AbstractStore<
    TEntity,
    in TEntityData,
    in TSelector
    > {
    fun query(selector: TSelector): StoreQuery<TEntity>
    fun queryAll(): StoreQuery<TEntity>

    fun update(selector: TSelector, data: TEntityData)
    fun updateAll(data: TEntityData)

    fun delete(selector: TSelector)
    fun deleteAll()
}