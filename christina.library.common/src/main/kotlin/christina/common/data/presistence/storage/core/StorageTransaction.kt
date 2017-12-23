package christina.common.data.presistence.storage.core

interface StorageTransaction : AutoCloseable {
    fun commit()

    fun rollback()
}