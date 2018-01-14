package christina.common.data.presistence.storage.memory.store

import android.support.annotation.CallSuper
import christina.common.data.presistence.storage.core.store.RelationStore

abstract class MemoryRelationStore<
    BindEntity,
    LeftEntity,
    RightEntity,
    LeftKey,
    RightKey,
    in EntityData,
    in Selector,
    out BindQuery,
    out LeftQuery,
    out RightQuery>
@JvmOverloads
constructor(
    entities: MutableList<BindEntity> = mutableListOf(),
    protected val leftEntities: MutableList<LeftEntity> = mutableListOf(),
    protected val rightEntities: MutableList<RightEntity> = mutableListOf()
) : MemoryAbstractStore<
    BindEntity,
    EntityData,
    Selector,
    BindQuery>(entities),
    RelationStore<
        BindEntity,
        LeftKey,
        RightKey,
        EntityData,
        Selector,
        BindQuery,
        LeftQuery,
        RightQuery> {
    @CallSuper
    override fun queryLeft(rightKey: RightKey): LeftQuery {
        val keys = entities
            .filter { getBindRightKey(it) == rightKey }
            .map(this::getBindLeftKey)

        return leftEntities
            .filter { keys.contains(getLeftKey(it)) }
            .map(this::extractLeftEntry)
            .let(this::transformLeftToQuery)
    }

    @CallSuper
    override fun queryRight(leftKey: LeftKey): RightQuery {
        val keys = entities
            .filter { getBindLeftKey(it) == leftKey }
            .map(this::getBindRightKey)

        return rightEntities
            .filter { keys.contains(getRightKey(it)) }
            .map(this::extractRightEntry)
            .let(this::transformRightToQuery)
    }

    @CallSuper
    override fun bind(leftKey: LeftKey, rightKey: RightKey, data: EntityData) {
        entities.add(create(leftKey, rightKey, data))
    }

    @CallSuper
    override fun unbind(leftKey: LeftKey, rightKey: RightKey) {
        entities.removeAll { getBindLeftKey(it) == leftKey && getBindRightKey(it) == rightKey }
    }

    @CallSuper
    override fun unbind(selector: Selector) {
        delete(selector)
    }

    @CallSuper
    override fun create(leftKey: LeftKey, rightKey: RightKey, data: EntityData): BindEntity =
        createEntity(leftKey, rightKey).also {
            entities.add(updateEntity(it, data))
        }

    protected abstract fun getLeftKey(entity: LeftEntity): LeftKey

    protected abstract fun getRightKey(entity: RightEntity): RightKey
    protected abstract fun getBindLeftKey(entity: BindEntity): LeftKey

    protected abstract fun getBindRightKey(entity: BindEntity): RightKey

    protected abstract fun createEntity(leftKey: LeftKey, rightKey: RightKey): BindEntity

    protected abstract fun transformRightToQuery(entities: Iterable<RightEntity>): RightQuery
    protected abstract fun transformLeftToQuery(entities: Iterable<LeftEntity>): LeftQuery

    protected abstract fun extractLeftEntry(entity: LeftEntity): LeftEntity
    protected abstract fun extractRightEntry(entity: RightEntity): RightEntity
}