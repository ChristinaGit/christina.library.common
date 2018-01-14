package christina.common.data.presistence.storage.memory.store

import android.support.annotation.CallSuper
import christina.common.data.presistence.storage.core.store.AbstractStore
import christina.common.data.presistence.storage.core.store.RelationStore

abstract class MemoryRelationStore<
    BindEntity,
    LeftKey,
    RightKey,
    in EntityData,
    in Selector,
    LeftSelector,
    RightSelector,
    out BindQuery,
    out LeftQuery,
    out RightQuery>
@JvmOverloads
constructor(
    protected val leftEntities: AbstractStore<*, LeftSelector, LeftQuery>,
    protected val rightEntities: AbstractStore<*, RightSelector, RightQuery>,
    entities: MutableList<BindEntity> = mutableListOf()
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
    override fun queryLeft(rightKey: RightKey): LeftQuery = synchronized(entities) {
        val keys = entities
            .filter { getRightKey(it) == rightKey }
            .map(::getLeftKey)

        return leftEntities.query(getLeftSelector(keys))
    }

    @CallSuper
    override fun queryRight(leftKey: LeftKey): RightQuery = synchronized(entities) {
        val keys = entities
            .filter { getLeftKey(it) == leftKey }
            .map(::getRightKey)

        return rightEntities.query(getRightSelector(keys))
    }

    @CallSuper
    override fun bind(leftKey: LeftKey, rightKey: RightKey, data: EntityData) {
        synchronized(entities) {
            entities.add(create(leftKey, rightKey, data))
        }
    }

    @CallSuper
    override fun unbind(leftKey: LeftKey, rightKey: RightKey) {
        synchronized(entities) {
            entities.removeAll { getLeftKey(it) == leftKey && getRightKey(it) == rightKey }
        }
    }

    @CallSuper
    override fun unbind(selector: Selector) {
        delete(selector)
    }

    @CallSuper
    override fun create(
        leftKey: LeftKey,
        rightKey: RightKey,
        data: EntityData
    ): BindEntity = synchronized(entities) {
        createEntity(leftKey, rightKey).also {
            updateEntity(it, data)
            entities += it
        }
    }

    protected abstract fun getLeftKey(entity: BindEntity): LeftKey
    protected abstract fun getRightKey(entity: BindEntity): RightKey

    protected abstract fun getLeftSelector(keys: Iterable<LeftKey>): LeftSelector
    protected abstract fun getRightSelector(keys: Iterable<RightKey>): RightSelector

    protected abstract fun createEntity(leftKey: LeftKey, rightKey: RightKey): BindEntity
}