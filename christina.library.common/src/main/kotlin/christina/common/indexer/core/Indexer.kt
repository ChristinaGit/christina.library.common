package christina.common.indexer.core

interface Indexer<out Index> {
    val first: Index

    val last: Index

    fun newIndex(): Index
}