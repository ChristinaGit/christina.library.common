package christina.common.data.presistence.storage.core.store

interface RelationStore<
    out BindEntity,
    in LeftKey,
    in RightKey,
    in EntityData,
    in Selector,
    out BindQuery,
    out LeftQuery,
    out RightQuery> : AbstractStore<EntityData, Selector, BindQuery> {

    fun create(leftKey: LeftKey, rightKey: RightKey, data: EntityData): BindEntity

    fun queryLeft(rightKey: RightKey): LeftQuery
    fun queryRight(leftKey: LeftKey): RightQuery

    fun bind(leftKey: LeftKey, rightKey: RightKey, data: EntityData)

    fun unbind(leftKey: LeftKey, rightKey: RightKey)
    fun unbind(selector: Selector)
}