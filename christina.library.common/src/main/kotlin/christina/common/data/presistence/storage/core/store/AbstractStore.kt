package christina.common.data.presistence.storage.core.store

interface AbstractStore<
    in EntityData,
    in Selector,
    out Query> {
    fun query(selector: Selector): Query
    fun queryAll(): Query

    fun update(selector: Selector, data: EntityData)
    fun updateAll(data: EntityData)

    fun delete(selector: Selector)
    fun deleteAll()
}