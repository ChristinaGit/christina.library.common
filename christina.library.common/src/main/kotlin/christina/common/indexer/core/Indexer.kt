package christina.common.indexer.core

interface Indexer<out Index> {
    val firstIndex: Index

    val lastIndex: Index

    fun newIndex(): Index
}