package christina.common.position_adapter

import christina.common.exception.ReasonableException
import christina.common.exception.hasReason
import christina.common.position_adapter.core.PositionAdapter
import christina.common.position_adapter.core.context.PositionContext
import christina.common.position_adapter.core.exception.PositionAdapterErrorReasons
import christina.common.position_adapter.core.exception.PositionAdapterException
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PositionAdapterTests {
    companion object {
        fun generatePositionContext(positionStart: Int = -10, positionEnd: Int = 10) =
            mockk<PositionContext>().apply {
                every { getPositionStart() } returns positionStart
                every { getPositionEnd() } returns positionEnd
            }

        fun generatePositionAdapter(positionStart: Int = -10, positionEnd: Int = 10) =
            PositionAdapter(generatePositionContext(positionStart, positionEnd))
    }

    @Test
    fun `getIndex`() {
        //region Arrange
        val adapter = generatePositionAdapter()
        //endregion

        //region Act
        val `indexForPosition-10` = adapter.getIndex(-10)
        val indexForPosition0 = adapter.getIndex(0)
        val indexForPosition10 = adapter.getIndex(10)
        //endregion

        //region Assert
        assertEquals(0, `indexForPosition-10`)
        assertEquals(10, indexForPosition0)
        assertEquals(20, indexForPosition10)
        //endregion
    }

    @Test
    fun `getPosition`() {
        //region Arrange
        val adapter = generatePositionAdapter()
        //endregion

        //region Act
        val positionForIndex0 = adapter.getPosition(0)
        val positionForIndex10 = adapter.getPosition(10)
        val positionForIndex20 = adapter.getPosition(20)
        //endregion

        //region Assert
        assertEquals(-10, positionForIndex0)
        assertEquals(0, positionForIndex10)
        assertEquals(10, positionForIndex20)
        //endregion
    }

    @Test
    fun `requirePositionLegal`() {
        //region Arrange
        val adapter = generatePositionAdapter()
        //endregion

        //region Act
        adapter.requirePosition(-10)
        adapter.requirePosition(0)
        adapter.requirePosition(10)
        //endregion

        //region Assert
        //endregion
    }

    @Test
    fun `requirePositionIllegal_start`() {
        //region Arrange
        val adapter = generatePositionAdapter()
        val exception: ReasonableException?
        //endregion

        //region Act
        exception = try {
            adapter.requirePosition(-11)
            null
        } catch (e: PositionAdapterException) {
            e.cause as ReasonableException
        }
        //endregion

        //region Assert
        assertTrue(exception !== null && exception.hasReason(PositionAdapterErrorReasons.ILLEGAL_POSITION))
        //endregion
    }

    @Test
    fun `requirePositionIllegal_end`() {
        //region Arrange
        val adapter = generatePositionAdapter()
        val exception: ReasonableException?
        //endregion

        //region Act
        exception = try {
            adapter.requirePosition(11)
            null
        } catch (e: PositionAdapterException) {
            e.cause as ReasonableException
        }
        //endregion

        //region Assert
        assertTrue(exception !== null && exception.hasReason(PositionAdapterErrorReasons.ILLEGAL_POSITION))
        //endregion
    }

    @Test
    fun `requireIndexLegal`() {
        //region Arrange
        val adapter = generatePositionAdapter()
        //endregion

        //region Act
        adapter.requireIndex(0)
        adapter.requireIndex(10)
        adapter.requireIndex(20)
        //endregion

        //region Assert
        //endregion
    }

    @Test
    fun `requireIndexIllegal_start`() {
        //region Arrange
        val adapter = generatePositionAdapter()
        val exception: ReasonableException?
        //endregion

        //region Act
        exception = try {
            adapter.requireIndex(-1)
            null
        } catch (e: PositionAdapterException) {
            e.cause as ReasonableException
        }
        //endregion

        //region Assert
        assertTrue(exception !== null && exception.hasReason(PositionAdapterErrorReasons.ILLEGAL_INDEX))
        //endregion
    }

    @Test
    fun `requireIndexIllegal_end`() {
        //region Arrange
        val adapter = generatePositionAdapter()
        val exception: ReasonableException?
        //endregion

        //region Act
        exception = try {
            adapter.requireIndex(21)
            null
        } catch (e: PositionAdapterException) {
            e.cause as ReasonableException
        }
        //endregion

        //region Assert
        assertTrue(exception !== null && exception.hasReason(PositionAdapterErrorReasons.ILLEGAL_INDEX))
        //endregion
    }
}