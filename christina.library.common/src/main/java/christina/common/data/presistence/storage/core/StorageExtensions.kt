package christina.common.data.presistence.storage.core

inline fun Storage.performTransaction(transactionBody: () -> Unit) {
    val transaction = beginTransaction()
    try {
        transactionBody()
        transaction.commit()
    } catch (e: Throwable) {
        transaction.rollback()
        throw e
    } finally {
        transaction.close()
    }
}