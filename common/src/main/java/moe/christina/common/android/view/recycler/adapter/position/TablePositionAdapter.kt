package moe.christina.common.android.view.recycler.adapter.position

//internal open class TablePositionAdapter<
//    TVH : ViewHolder,
//    TCVH : ViewHolder,
//    TVHVH : ViewHolder,
//    THHVH : ViewHolder
//    > : PositionAdapter<ViewHolder>() {
//    init {
//        TODO("In development")
//    }
//
//    override val itemCount: Int
//        get() {
//            val leftHeaderEnabled = leftHeaderEnabled
//            val topHeaderEnabled = topHeaderEnabled
//            val rightHeaderEnabled = rightHeaderEnabled
//            val bottomHeaderEnabled = bottomHeaderEnabled
//
//            return cellCount + cornerCount
//        }
//
//    val cellCount: Int
//        get() = height * width
//
//    val cornerCount: Int
//        get() {
//            val leftHeaderEnabled = leftHeaderEnabled
//            val topHeaderEnabled = topHeaderEnabled
//            val rightHeaderEnabled = rightHeaderEnabled
//            val bottomHeaderEnabled = bottomHeaderEnabled
//
//            var verticalHeaderCount = 0
//
//            if (topHeaderEnabled) {
//                verticalHeaderCount++
//            }
//            if (bottomHeaderEnabled) {
//                verticalHeaderCount++
//            }
//
//            var horizontalHeaderCount = 0
//
//            if (leftHeaderEnabled) {
//                horizontalHeaderCount++
//            }
//            if (rightHeaderEnabled) {
//                horizontalHeaderCount++
//            }
//
//            return if (verticalHeaderCount == 0 || horizontalHeaderCount == 0) 0
//            else when {
//                verticalHeaderCount == 2 && horizontalHeaderCount == 2 -> 4
//                verticalHeaderCount == 1 && horizontalHeaderCount == 1 -> 1
//                else -> 2
//            }
//        }
//
//    var tableStyleDelegate: TableStyleDelegate = object : TableStyleDelegate {}
//
//    val leftHeaderEnabled
//        get() = tableStyleDelegate.leftHeaderEnabled
//    val topHeaderEnabled
//        get() = tableStyleDelegate.topHeaderEnabled
//    val rightHeaderEnabled
//        get() = tableStyleDelegate.rightHeaderEnabled
//    val bottomHeaderEnabled
//        get() = tableStyleDelegate.bottomHeaderEnabled
//
//    var tableSizeDelegate: TableSizeDelegate = object : TableSizeDelegate {}
//
//    protected val height: Int
//        get() = tableSizeDelegate.tableHeight
//    protected val width: Int
//        get() = tableSizeDelegate.tableWidth
//
//    override fun isKnownViewType(viewType: Int): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun performCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun performBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    interface TableStyleDelegate {
//        val leftHeaderEnabled
//            get() = false
//        val topHeaderEnabled
//            get() = false
//        val rightHeaderEnabled
//            get() = false
//        val bottomHeaderEnabled
//            get() = false
//    }
//
//    interface TableSizeDelegate {
//        val tableHeight: Int
//            get() = 0
//        val tableWidth: Int
//            get() = 0
//    }
//
//    interface ViewTypeDelegate {
//        fun getCellItemViewType(position: Int): Int = 0
//        fun getCornerItemViewType(position: Int): Int = 0
//        fun getLeftHeaderItemViewType(position: Int): Int = 0
//        fun getTopHeaderItemViewType(position: Int): Int = 0
//        fun getRightHeaderItemViewType(position: Int): Int = 0
//        fun getBottomHeaderItemViewType(position: Int): Int = 0
//
//        fun isCellItemViewType(viewType: Int): Boolean = false
//        fun isCornerItemViewType(viewType: Int): Boolean = false
//        fun isLeftHeaderItemViewType(viewType: Int): Boolean = false
//        fun isTopHeaderItemViewType(viewType: Int): Boolean = false
//        fun isRightHeaderItemViewType(viewType: Int): Boolean = false
//        fun isBottomHeaderItemViewType(viewType: Int): Boolean = false
//    }
//
//    interface BindViewHolderDelegate<TVH, TCVH, TVHVH, THHVH> {
//        fun onCreateCellItemViewHolder(parent: ViewGroup, viewType: Int): TVH =
//            throw NotImplementedError()
//
//        fun onCreateCornerItemViewHolder(parent: ViewGroup, viewType: Int): TCVH =
//            throw NotImplementedError()
//
//        fun onCreateLeftHeaderItemViewHolder(parent: ViewGroup, viewType: Int): THHVH =
//            throw NotImplementedError()
//
//        fun onCreateTopHeaderItemViewHolder(parent: ViewGroup, viewType: Int): TVHVH =
//            throw NotImplementedError()
//
//        fun onCreateRightHeaderItemViewHolder(parent: ViewGroup, viewType: Int): THHVH =
//            throw NotImplementedError()
//
//        fun onCreateBottomHeaderItemViewHolder(parent: ViewGroup, viewType: Int): TVHVH =
//            throw NotImplementedError()
//
//        fun onBindCellItemViewHolder(holder: TVH, position: Int) {}
//        fun onBindCornerItemViewHolder(holder: TCVH, position: Int) {}
//        fun onBindLeftHeaderItemViewHolder(holder: THHVH, position: Int) {}
//        fun onBindTopHeaderItemViewHolder(holder: TVHVH, position: Int) {}
//        fun onBindRightHeaderItemViewHolder(holder: THHVH, position: Int) {}
//        fun onBindBottomHeaderItemViewHolder(holder: TVHVH, position: Int) {}
//    }
//}