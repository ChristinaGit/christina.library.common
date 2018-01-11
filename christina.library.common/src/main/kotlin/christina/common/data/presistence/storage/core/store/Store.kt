package christina.common.data.presistence.storage.core.store

interface Store<
    out Entity,
    in EntityData,
    in Selector,
    out Query> : AbstractStore<EntityData, Selector, Query> {

    fun create(data: EntityData): Entity
}