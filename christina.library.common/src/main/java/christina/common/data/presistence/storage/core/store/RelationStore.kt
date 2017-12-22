package christina.common.data.presistence.storage.core.store

interface RelationStore<
    out TBindEntity,
    out TLeftEntity,
    out TRightEntity,
    in TLeftKey,
    in TRightKey,
    in TEntityData,
    in TSelector> : AbstractStore<TBindEntity, TEntityData, TSelector> {

    fun queryLeft(rightKey: TRightKey): StoreQuery<TLeftEntity>
    fun queryRight(leftKey: TLeftKey): StoreQuery<TRightEntity>

    fun bind(leftKey: TLeftKey, rightKey: TRightKey, data: TEntityData)

    fun unbind(leftKey: TLeftKey, rightKey: TRightKey)
    fun unbind(selector: TSelector)
}