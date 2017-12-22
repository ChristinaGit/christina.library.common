package christina.common.indexer.basic

import christina.common.indexer.core.Indexer

class BasicIndexer<TIndex>(
    override val first: TIndex,
    private val nextIndex: (TIndex) -> TIndex
) : Indexer<TIndex> {
    override var last = first

    override fun newIndex(): TIndex {
        last = nextIndex(last)

        return last
    }
}