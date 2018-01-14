package christina.common.data.persistance.storage.store

import christina.common.data.persistance.storage.store.AbstractStoreTests.Companion.Entity
import christina.common.data.persistance.storage.store.AbstractStoreTests.Companion.EntityData
import christina.common.data.presistence.storage.core.store.Store
import org.junit.Test
import java.util.function.Predicate
import kotlin.test.assertTrue

abstract class StoreTests<Target : Store<Entity, EntityData, Predicate<Entity>, Iterable<Entity>>>(
    storeFactory: (Iterable<Entity>) -> Target
) : AbstractStoreTests<Target>(storeFactory) {
    @Test
    fun `create`() {
        //region Arrange
        val newEntitiesData = (0..10).map { EntityData("New entry name. id: $it") }
        //endregion

        //region Act
        val originalEntities = target.queryAll()
        val createdEntities = newEntitiesData.map(target::create)
        val resultEntities = target.queryAll()
        //endregion

        //region Assert
        assertTrue(newEntitiesData.any())
        assertTrue(originalEntities.none { originalEntry -> newEntitiesData.any { it.name == originalEntry.name } })
        assertTrue(newEntitiesData.all { newEntityData -> createdEntities.any { it.name == newEntityData.name } })
        assertTrue(newEntitiesData.all { newEntityData -> resultEntities.any { it.name == newEntityData.name } })
        //endregion
    }
}