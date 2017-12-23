package christina.common.data.presistence.storage.core.store

interface Store<
    out Entity,
    in EntityData,
    in Selector> : AbstractStore<Entity, EntityData, Selector> {

    fun create(data: EntityData): Entity
}