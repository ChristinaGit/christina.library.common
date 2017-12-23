package christina.common.data.presistence.storage.memory.store

import christina.common.data.presistence.storage.core.store.RelationStore
import christina.common.data.presistence.storage.core.store.query.StoreQuery
import christina.common.data.presistence.storage.core.store.query.storeQuery

abstract class MemoryRelationStore<
    BindEntity,
    LeftEntity,
    RightEntity,
    LeftKey,
    RightKey,
    in EntityData,
    in Selector>
@JvmOverloads
constructor(
    entities: MutableCollection<BindEntity> = mutableListOf(),
    protected val leftEntities: MutableCollection<LeftEntity> = mutableListOf(),
    protected val rightEntities: MutableCollection<RightEntity> = mutableListOf()
) : MemoryAbstractStore<
    BindEntity,
    EntityData,
    Selector>(entities),
    RelationStore<
        BindEntity,
        LeftEntity,
        RightEntity,
        LeftKey,
        RightKey,
        EntityData,
        Selector> {
    final override fun queryLeft(rightKey: RightKey): StoreQuery<LeftEntity> {
        val keys = entities
            .filter { getBindRightKey(it) == rightKey }
            .map(this::getBindLeftKey)

        return leftEntities
            .filter { keys.contains(getLeftKey(it)) }
            .map(this::copyLeftEntry)
            .let(this::transformLeftToQuery)
    }

    final override fun queryRight(leftKey: LeftKey): StoreQuery<RightEntity> {
        val keys = entities
            .filter { getBindLeftKey(it) == leftKey }
            .map(this::getBindRightKey)

        return rightEntities
            .filter { keys.contains(getRightKey(it)) }
            .map(this::copyRightEntry)
            .let(this::transformRightToQuery)
    }

    final override fun bind(leftKey: LeftKey, rightKey: RightKey, data: EntityData) {
        entities.add(create(leftKey, rightKey, data))
    }

    final override fun unbind(leftKey: LeftKey, rightKey: RightKey) {
        entities.removeAll { getBindLeftKey(it) == leftKey && getBindRightKey(it) == rightKey }
    }

    final override fun unbind(selector: Selector) {
        delete(selector)
    }

    protected abstract fun getLeftKey(entity: LeftEntity): LeftKey
    protected abstract fun getRightKey(entity: RightEntity): RightKey

    protected abstract fun getBindLeftKey(entity: BindEntity): LeftKey
    protected abstract fun getBindRightKey(entity: BindEntity): RightKey

    protected fun create(leftKey: LeftKey, rightKey: RightKey, data: EntityData): BindEntity =
        createEntity(leftKey, rightKey).also {
            updateEntity(it, data)
            entities.add(it)
        }

    protected abstract fun createEntity(leftKey: LeftKey, rightKey: RightKey): BindEntity

    protected open fun transformRightToQuery(entities: Iterable<RightEntity>): StoreQuery<RightEntity> = storeQuery(entities)

    protected open fun transformLeftToQuery(entities: Iterable<LeftEntity>): StoreQuery<LeftEntity> = storeQuery(entities)

    protected abstract fun copyLeftEntry(entity: LeftEntity): LeftEntity
    protected abstract fun copyRightEntry(entity: RightEntity): RightEntity
}