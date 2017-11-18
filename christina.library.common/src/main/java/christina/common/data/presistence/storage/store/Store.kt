package christina.common.data.presistence.storage.store

interface Store<
    TEntity,
    in TEntityData,
    in TSelector
    > : AbstractStore<TEntity, TEntityData, TSelector> {

    fun create(data: TEntityData): TEntity
}