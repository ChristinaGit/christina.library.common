package christina.common.data.presistence.storage.fake

import christina.common.data.presistence.storage.core.Storage
import christina.common.data.presistence.storage.core.StorageTransaction

open class FakeStorage : Storage {
    final override fun beginTransaction(): StorageTransaction = FakeStorageTransaction()
}