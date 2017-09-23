package moe.christina.common.data.presistence.repository

interface Repository<TEntity, in TSelector> {
    fun getAll(): List<TEntity>
    fun getAll(selector: TSelector): List<TEntity>
    fun getFirst(selector: TSelector): TEntity?

    fun add(entity: TEntity)

    fun removeAll()
    fun removeAll(selector: TSelector)
    fun removeFirst(selector: TSelector)
}