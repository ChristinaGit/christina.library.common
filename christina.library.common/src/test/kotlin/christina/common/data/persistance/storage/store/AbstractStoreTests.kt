package christina.common.data.persistance.storage.store

import android.support.annotation.CallSuper
import christina.common.data.persistance.storage.store.AbstractStoreTests.Companion.Entity
import christina.common.data.persistance.storage.store.AbstractStoreTests.Companion.EntityData
import christina.common.data.presistence.storage.core.store.AbstractStore
import christina.common.divisibleBy
import org.junit.Before
import org.junit.Test
import java.util.function.Predicate
import kotlin.test.assertTrue

abstract class AbstractStoreTests<
    Target : AbstractStore<EntityData, Predicate<Entity>, Iterable<Entity>>>(
    private val factory: (Iterable<Entity>) -> Target
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

    protected fun generateTarget() = factory(generateEntities())

    protected lateinit var target: Target

    @Before
    @CallSuper
    open fun setup() {
        target = generateTarget()
    }

    @Test
    fun `query`() {
        //region Arrange
        val selector: Predicate<Entity> = Predicate { it.id divisibleBy 2L }
        //endregion

        //region Act
        val entities = target.query(selector)
        val entitiesNegate = target.query(selector.negate())
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
        val entities = target.queryAll()
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
        target.update(selector, EntityData(newName))
        val entities = target.query(selector)
        val entitiesNegate = target.query(selector.negate())
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
        target.updateAll(EntityData(newName))
        val entities = target.queryAll()
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
        val originalEntries = target.queryAll()
        target.delete(selector)
        val entries = target.queryAll()
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
        val originalEntries = target.queryAll()
        target.deleteAll()
        val entries = target.queryAll()
        //endregion

        //region Assert
        assertTrue(originalEntries.any())
        assertTrue(entries.none())
        //endregion
    }
}