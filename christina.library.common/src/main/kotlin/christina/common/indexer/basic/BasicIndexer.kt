package christina.common.indexer.basic

import christina.common.indexer.core.Indexer

class BasicIndexer<Index>(
    override val first: Index,
    private val nextIndex: (Index) -> Index
) : Indexer<Index> {
    override var last = first

    override fun newIndex(): Index {
        last = nextIndex(last)

        return last
    }
}