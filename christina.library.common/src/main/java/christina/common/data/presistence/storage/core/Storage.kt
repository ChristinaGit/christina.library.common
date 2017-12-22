package christina.common.data.presistence.storage.core

interface Storage {
    fun beginTransaction(): StorageTransaction
}