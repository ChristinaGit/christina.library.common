package christina.common.data.presistence.storage.fake

import christina.common.data.presistence.storage.core.StorageTransaction

object FakeStorageTransaction : StorageTransaction {
    override fun commit() {
    }

    override fun rollback() {
    }

    override fun close() {
    }
}