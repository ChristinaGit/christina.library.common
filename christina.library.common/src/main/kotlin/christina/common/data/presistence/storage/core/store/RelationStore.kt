package christina.common.data.presistence.storage.core.store

import christina.common.data.presistence.storage.core.store.query.StoreQuery

interface RelationStore<
    out BindEntity,
    out LeftEntity,
    out RightEntity,
    in LeftKey,
    in RightKey,
    in EntityData,
    in Selector> : AbstractStore<BindEntity, EntityData, Selector> {

    fun queryLeft(rightKey: RightKey): StoreQuery<LeftEntity>
    fun queryRight(leftKey: LeftKey): StoreQuery<RightEntity>

    fun bind(leftKey: LeftKey, rightKey: RightKey, data: EntityData)

    fun unbind(leftKey: LeftKey, rightKey: RightKey)
    fun unbind(selector: Selector)
}