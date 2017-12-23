package christina.common.data.presistence.storage.core.store.query

interface StoreQuery<out Entity> {
    fun asIterable(): Iterable<Entity>
}