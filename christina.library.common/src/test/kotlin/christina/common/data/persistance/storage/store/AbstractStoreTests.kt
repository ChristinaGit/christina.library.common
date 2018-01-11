package christina.common.data.persistance.storage.store

import christina.common.data.presistence.storage.core.store.AbstractStore
import christina.common.divisibleBy
import org.junit.Before
import org.junit.Test
import java.util.function.Predicate
import kotlin.test.assertTrue

abstract class AbstractStoreTests(
    private val abstractStoreFactory: () -> AbstractStore<EntityData, Predicate<Entity>, Iterable<Entity>>
) {
    companion object {
        fun generateEntities() = (1L..10L).map { Entity(it).apply { name = "Name $it" } }

        class Entity(
            val id: Long
        ) {
            var name: String? = null
        }

        class EntityData(
            val name: String?
        )
    }

    fun generateAbstractStore() = abstractStoreFactory()

    lateinit var abstractStore: AbstractStore<EntityData, Predicate<Entity>, Iterable<Entity>>

    @Before
    fun setup() {
        abstractStore = generateAbstractStore()
    }

    @Test
    fun `query`() {
        //region Arrange
        val selector: Predicate<Entity> = Predicate { it.id divisibleBy 2L }
        //endregion

        //region Act
        val entities = abstractStore.query(selector).asIterable()
        val entitiesNegate = abstractStore.query(selector.negate()).asIterable()
        //endregion

        //region Assert
        assertTrue(entities.any())
        assertTrue(entitiesNegate.any())
        assertTrue(entities.all(selector::test))
        assertTrue(entitiesNegate.all(selector.negate()::test))
        //endregion
    }

    @Test
    fun `queryAll`() {
        //region Arrange
        val originalEntities = generateEntities()
        //endregion

        //region Act
        val entities = abstractStore.queryAll().asIterable()
        //endregion

        //region Assert
        assertTrue(entities.map { it.id }.toLongArray() contentEquals originalEntities.map { it.id }.toLongArray())
        //endregion
    }

    @Test
    fun `update`() {
        //region Arrange
        val newName = "new entry name"
        val selector: Predicate<Entity> = Predicate { it.id divisibleBy 3L }
        //endregion

        //region Act
        abstractStore.update(selector, EntityData(newName))
        val entities = abstractStore.query(selector).asIterable()
        val entitiesNegate = abstractStore.query(selector.negate()).asIterable()
        //endregion

        //region Assert
        assertTrue(entities.any())
        assertTrue(entitiesNegate.any())
        assertTrue(entities.all { it.name == newName })
        assertTrue(entitiesNegate.none { it.name == newName })
        //endregion
    }

    @Test
    fun `updateAll`() {
        //region Arrange
        val newName = "new entry name"
        //endregion

        //region Act
        abstractStore.updateAll(EntityData(newName))
        val entities = abstractStore.queryAll().asIterable()
        //endregion

        //region Assert
        assertTrue(entities.all { it.name == newName })
        //endregion
    }

    @Test
    fun `delete`() {
        //region Arrange
        val selector: Predicate<Entity> = Predicate { it.id divisibleBy 3L }
        //endregion

        //region Act
        val originalEntries = abstractStore.queryAll().asIterable()
        abstractStore.delete(selector)
        val entries = abstractStore.queryAll().asIterable()
        //endregion

        //region Assert
        assertTrue(originalEntries.any(selector::test))
        assertTrue(entries.none(selector::test))
        //endregion
    }

    @Test
    fun `deleteAll`() {
        //region Arrange
        //endregion

        //region Act
        val originalEntries = abstractStore.queryAll().asIterable()
        abstractStore.deleteAll()
        val entries = abstractStore.queryAll().asIterable()
        //endregion

        //region Assert
        assertTrue(originalEntries.any())
        assertTrue(entries.none())
        //endregion
    }
}