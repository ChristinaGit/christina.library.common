package christina.common.indexer.basic

import christina.common.indexer.core.Indexer

class BasicIndexer<Index>(
    override val firstIndex: Index,
    private val nextIndex: (Index) -> Index
) : Indexer<Index> {
    override var lastIndex = firstIndex

    override fun newIndex(): Index {
        lastIndex = nextIndex(lastIndex)

        return lastIndex
    }
}