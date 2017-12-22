package christina.common.indexer.core

interface Indexer<out TIndex> {
    val first: TIndex

    val last: TIndex

    fun newIndex(): TIndex
}