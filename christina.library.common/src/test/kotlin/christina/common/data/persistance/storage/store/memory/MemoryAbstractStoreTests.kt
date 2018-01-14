package christina.common.data.persistance.storage.store.memory

import christina.common.data.persistance.storage.store.AbstractStoreTests
import christina.common.data.persistance.storage.store.AbstractStoreTests.Companion.Entity
import christina.common.data.persistance.storage.store.AbstractStoreTests.Companion.EntityData
import christina.common.data.presistence.storage.memory.store.MemoryAbstractStore
import java.util.function.Predicate

class MemoryAbstractStoreTests :
    AbstractStoreTests<MemoryAbstractStore<Entity, EntityData, Predicate<Entity>, Iterable<Entity>>>({
        object : MemoryAbstractStore<Entity, EntityData, Predicate<Entity>, Iterable<Entity>>(it.toMutableList()) {
            override fun applySelector(
                entities: List<Entity>,
                selector: Predicate<Entity>
            ): List<Entity> = entities.filter(selector::test)

            override fun updateEntity(entity: Entity, data: EntityData) {
                entity.apply {
                    name = data.name
                }
            }

            override fun transformToQuery(entities: Iterable<Entity>): Iterable<Entity> = entities

            override fun extractEntity(entity: Entity): Entity =
                Entity(entity.id).apply {
                    name = entity.name
                }
        }
    })