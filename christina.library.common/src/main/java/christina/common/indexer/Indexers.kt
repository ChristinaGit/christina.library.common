package christina.common.indexer

import christina.common.Default
import christina.common.indexer.basic.BasicIndexer
import christina.common.indexer.core.Indexer

object Indexers {
    @JvmStatic
    fun byte(first: Byte = Default.BYTE): Indexer<Byte> = BasicIndexer(first, Byte::inc)

    @JvmStatic
    fun short(first: Short = Default.SHORT): Indexer<Short> = BasicIndexer(first, Short::inc)

    @JvmStatic
    fun int(first: Int = Default.INT): Indexer<Int> = BasicIndexer(first, Int::inc)

    @JvmStatic
    fun long(first: Long = Default.LONG): Indexer<Long> = BasicIndexer(first, Long::inc)
}