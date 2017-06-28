package moe.christina.common

import android.support.v7.widget.RecyclerView.ViewHolder
import moe.christina.common.android.view.recycler.adapter.WrappedRecyclerViewAdapter
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class WrappedRecyclerViewAdapterTest {
    private class TestWrappedRecyclerViewAdapter : WrappedRecyclerViewAdapter<ViewHolder>() {
        override val headerItemCount: Int
            get() = test_headerItemCount
        override var innerItemCount: Int = 0
            get() = test_innerItemCount
        override val footerItemCount: Int
            get() = test_footerItemCount

        var test_headerItemCount: Int = 0
        var test_innerItemCount: Int = 0
        var test_footerItemCount: Int = 0
    }

    @Test
    fun itemCount_checkInitialItemCount() {
        val adapter = TestWrappedRecyclerViewAdapter()

        assertEquals("Wrong initial item count", 0, adapter.itemCount)
        assertEquals("Wrong initial header item count", 0, adapter.headerItemCount)
        assertEquals("Wrong initial inner item count", 0, adapter.innerItemCount)
        assertEquals("Wrong initial footer item count", 0, adapter.footerItemCount)
    }

    @Test
    fun itemCount_checkAllItemCount() {
        val adapter = TestWrappedRecyclerViewAdapter()

        for (headerItemCount in 0..10)
            for (innerItemCount in 0..10)
                for (footerItemCount in 0..10)
                    checkItemCount(adapter, headerItemCount, innerItemCount, footerItemCount)
    }

    @Test
    fun getItemViewType_checkIllegalAdapterPosition_Initial() {
        var exceptionCount = 0
        var testCount = 0

        val adapter = TestWrappedRecyclerViewAdapter()

        for (position in -1..1) {
            testCount++
            try {
                adapter.getItemViewType(position)
            } catch (e: IllegalArgumentException) {
                exceptionCount++
            }
        }

        Assert.assertEquals(testCount, exceptionCount)
    }

    @Test
    fun getItemViewType_checkIllegalAdapterPosition_() {
        val adapter = TestWrappedRecyclerViewAdapter()

        for (headerItemCount in 0..10)
            for (innerItemCount in 0..10)
                for (footerItemCount in 0..10)
                    checkViewTypes(adapter, headerItemCount, innerItemCount, footerItemCount)
    }

    @Test
    fun getHeaderItemRelativePosition_checkIllegalAdapterPosition() {
        var exceptionCount = 0
        var testCount = 0

        val adapter = TestWrappedRecyclerViewAdapter()

        val headerItemCount = 5
        val innerItemCount = 2
        val footerItemCount = 10
        adapter.test_headerItemCount = headerItemCount
        adapter.test_innerItemCount = innerItemCount
        adapter.test_footerItemCount = footerItemCount

        testCount++
        try {
            adapter.getHeaderItemRelativePosition(-1)
        } catch (e: IllegalArgumentException) {
            exceptionCount++
        }

        testCount++
        try {
            adapter.getHeaderItemRelativePosition(headerItemCount)
        } catch (e: IllegalArgumentException) {
            exceptionCount++
        }

        testCount++
        try {
            adapter.getHeaderItemRelativePosition(headerItemCount + 1)
        } catch (e: IllegalArgumentException) {
            exceptionCount++
        }

        Assert.assertEquals(testCount, exceptionCount)
    }

    @Test
    fun getInnerItemRelativePosition_checkIllegalAdapterPosition() {
        var exceptionCount = 0
        var testCount = 0

        val adapter = TestWrappedRecyclerViewAdapter()

        val headerItemCount = 5
        val innerItemCount = 2
        val footerItemCount = 10
        adapter.test_headerItemCount = headerItemCount
        adapter.test_innerItemCount = innerItemCount
        adapter.test_footerItemCount = footerItemCount

        testCount++
        try {
            adapter.getInnerItemRelativePosition(-1)
        } catch (e: IllegalArgumentException) {
            exceptionCount++
        }

        testCount++
        try {
            adapter.getInnerItemRelativePosition(headerItemCount - 1)
        } catch (e: IllegalArgumentException) {
            exceptionCount++
        }

        testCount++
        try {
            adapter.getInnerItemRelativePosition(headerItemCount + innerItemCount)
        } catch (e: IllegalArgumentException) {
            exceptionCount++
        }

        testCount++
        try {
            adapter.getInnerItemRelativePosition(headerItemCount + innerItemCount + 1)
        } catch (e: IllegalArgumentException) {
            exceptionCount++
        }


        Assert.assertEquals(testCount, exceptionCount)
    }

    @Test
    fun getFooterItemRelativePosition_checkIllegalAdapterPosition() {
        var exceptionCount = 0
        var testCount = 0

        val adapter = TestWrappedRecyclerViewAdapter()

        val headerItemCount = 5
        val innerItemCount = 2
        val footerItemCount = 10
        adapter.test_headerItemCount = headerItemCount
        adapter.test_innerItemCount = innerItemCount
        adapter.test_footerItemCount = footerItemCount

        testCount++
        try {
            adapter.getFooterItemRelativePosition(-1)
        } catch (e: IllegalArgumentException) {
            exceptionCount++
        }

        testCount++
        try {
            adapter.getFooterItemRelativePosition(headerItemCount + innerItemCount - 1)
        } catch (e: IllegalArgumentException) {
            exceptionCount++
        }

        testCount++
        try {
            adapter.getFooterItemRelativePosition(headerItemCount + innerItemCount + footerItemCount)
        } catch (e: IllegalArgumentException) {
            exceptionCount++
        }

        testCount++
        try {
            adapter.getFooterItemRelativePosition(headerItemCount + innerItemCount + footerItemCount + 1)
        } catch (e: IllegalArgumentException) {
            exceptionCount++
        }


        Assert.assertEquals(testCount, exceptionCount)
    }

    @Test
    fun getHeaderItemRelativePosition_checkIllegalAdapterPosition_Initial() {
        var exceptionCount = 0
        var testCount = 0

        val adapter = TestWrappedRecyclerViewAdapter()

        for (position in -1..1) {
            testCount++
            try {
                adapter.getHeaderItemRelativePosition(position)
            } catch (e: IllegalArgumentException) {
                exceptionCount++
            }
        }

        Assert.assertEquals(testCount, exceptionCount)
    }

    @Test
    fun getInnerItemRelativePosition_checkIllegalAdapterPosition_Initial() {
        var exceptionCount = 0
        var testCount = 0

        val adapter = TestWrappedRecyclerViewAdapter()

        for (position in -1..1) {
            testCount++
            try {
                adapter.getInnerItemRelativePosition(position)
            } catch (e: IllegalArgumentException) {
                exceptionCount++
            }
        }

        Assert.assertEquals(testCount, exceptionCount)
    }

    @Test
    fun getFooterItemRelativePosition_checkIllegalAdapterPosition_Initial() {
        var exceptionCount = 0
        var testCount = 0

        val adapter = TestWrappedRecyclerViewAdapter()

        for (position in -1..1) {
            testCount++
            try {
                adapter.getFooterItemRelativePosition(position)
            } catch (e: IllegalArgumentException) {
                exceptionCount++
            }
        }

        Assert.assertEquals(testCount, exceptionCount)
    }

    private fun checkItemCount(adapter: TestWrappedRecyclerViewAdapter,
                               headerItemCount: Int,
                               innerItemCount: Int,
                               footerItemCount: Int) {
        adapter.apply {
            test_headerItemCount = headerItemCount
            test_innerItemCount = innerItemCount
            test_footerItemCount = footerItemCount
        }

        assertEquals("Wrong item count", headerItemCount + innerItemCount + footerItemCount, adapter.itemCount)
        assertEquals("Wrong header item count", headerItemCount, adapter.headerItemCount)
        assertEquals("Wrong inner item count", innerItemCount, adapter.innerItemCount)
        assertEquals("Wrong footer item count", footerItemCount, adapter.footerItemCount)
    }

    private fun checkViewTypes(
            adapter: TestWrappedRecyclerViewAdapter,
            headerItemCount: Int,
            innerItemCount: Int,
            footerItemCount: Int) {
        adapter.test_headerItemCount = headerItemCount
        adapter.test_innerItemCount = innerItemCount
        adapter.test_footerItemCount = footerItemCount

        for (position in 0..(headerItemCount - 1)) {
            val expected = WrappedRecyclerViewAdapter.VIEW_TYPE_HEADER
            val actual = adapter.getItemViewType(position)
            assertEquals(expected, actual)
        }

        for (position in headerItemCount..(headerItemCount + innerItemCount - 1)) {
            val expected = WrappedRecyclerViewAdapter.VIEW_TYPE_INNER
            val actual = adapter.getItemViewType(position)
            assertEquals(expected, actual)
        }

        for (position in (headerItemCount + innerItemCount)..((headerItemCount + innerItemCount + footerItemCount - 1))) {
            val expected = WrappedRecyclerViewAdapter.VIEW_TYPE_FOOTER
            val actual = adapter.getItemViewType(position)
            assertEquals(expected, actual)
        }
    }
}