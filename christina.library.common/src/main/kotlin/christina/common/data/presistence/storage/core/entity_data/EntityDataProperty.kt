package christina.common.data.presistence.storage.core.entity_data

import christina.common.core.equality_comparator.EqualityComparator
import christina.common.core.equality_comparator.EqualityComparators
import christina.common.data.presistence.storage.core.entity_data.EntityDataPropertyState.Assigned
import christina.common.data.presistence.storage.core.entity_data.EntityDataPropertyState.Changed
import christina.common.data.presistence.storage.core.entity_data.EntityDataPropertyState.Unchanged

class EntityDataProperty<Value>
@JvmOverloads
constructor(
    private var value: Value,
    private val comparator: EqualityComparator<Value> = EqualityComparators.default()
) {
    var state: EntityDataPropertyState = Unchanged
        private set

    private fun equals(lhs: Value, rhs: Value): Boolean = comparator.equals(rhs, lhs)

    fun set(value: Value) {
        state = if (equals(this.value, value)) {
            Assigned
        } else {
            Changed
        }
        this.value = value
    }

    fun get(): Value = value
}