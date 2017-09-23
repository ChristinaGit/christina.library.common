package moe.christina.common.data.presistence.dao

import io.reactivex.Flowable

interface Dao<TEntity, in TIdentifier, in TSelector> {
    fun loadAll(): Flowable<List<TEntity>>
    fun loadAll(selector: TSelector): Flowable<List<TEntity>>
    fun load(id: TIdentifier): Flowable<TEntity>

    fun update(entity: TEntity)

    fun deleteAll()
    fun deleteAll(selector: TSelector)
    fun delete(id: TIdentifier)
}