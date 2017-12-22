package christina.common.data.presistence.storage.core.entity_data

class EntityDataProperty<T>(
    private var value: T
) {
    fun set(value: T) {
        this.value = value
    }

    fun get(): T = value

    var isChanged: Boolean = false
        private set
}