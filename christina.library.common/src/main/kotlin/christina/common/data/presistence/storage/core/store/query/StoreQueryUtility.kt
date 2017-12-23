package christina.common.data.presistence.storage.core.store.query

fun <Entity> storeQuery(entities: Iterable<Entity>): StoreQuery<Entity> =
    object : StoreQuery<Entity> {
        override fun asIterable(): Iterable<Entity> = entities
    }