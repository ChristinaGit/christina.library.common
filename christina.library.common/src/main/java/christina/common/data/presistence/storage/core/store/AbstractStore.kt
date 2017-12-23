package christina.common.data.presistence.storage.core.store

import christina.common.data.presistence.storage.core.store.query.StoreQuery

interface AbstractStore<
    out Entity,
    in EntityData,
    in Selector> {
    fun query(selector: Selector): StoreQuery<Entity>
    fun queryAll(): StoreQuery<Entity>

    fun update(selector: Selector, data: EntityData)
    fun updateAll(data: EntityData)

    fun delete(selector: Selector)
    fun deleteAll()
}