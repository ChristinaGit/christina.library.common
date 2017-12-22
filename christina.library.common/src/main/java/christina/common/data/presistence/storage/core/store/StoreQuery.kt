package christina.common.data.presistence.storage.core.store

interface StoreQuery<out TEntity> {
    fun asIterable(): Iterable<TEntity>
}