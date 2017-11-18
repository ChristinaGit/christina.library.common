package christina.common.indexer

class Indexer(
    val startIndex: Int = 0,
    private val nextIndex: (Int) -> Int = Int::inc
) {
    var currentIndex = startIndex

    fun newIndex(): Int {
        currentIndex = nextIndex(currentIndex)

        return currentIndex
    }
}