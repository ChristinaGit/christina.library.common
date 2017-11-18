package christina.common.test

import christina.common.position_adapter.PositionAdapter
import christina.common.position_adapter.PositionContext
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PositionAdapterTest {
    @Test
    fun `getPosition_correct`() {
        val context = mock(PositionContext::class.java)
        `when`(context.getPositionStart()).thenReturn(4)
        `when`(context.getPositionEnd()).thenReturn(15)

        val adapter = PositionAdapter(context)

        val position4 = adapter.getPosition(0)
        val position5 = adapter.getPosition(1)
        val position15 = adapter.getPosition(11)

        assertEquals(4, position4)
        assertEquals(5, position5)
        assertEquals(15, position15)
    }

    @Test
    fun `getPosition_incorrect`() {
        val context = mock(PositionContext::class.java)
        `when`(context.getPositionStart()).thenReturn(4)
        `when`(context.getPositionEnd()).thenReturn(15)

        val adapter = PositionAdapter(context)

        var errorsCount = 0
        try {
            adapter.getPosition(-1)
        } catch (e: IllegalArgumentException) {
            errorsCount++
        }
        try {
            adapter.getPosition(12)
        } catch (e: IllegalArgumentException) {
            errorsCount++
        }

        assertEquals(2, errorsCount)
    }

    @Test
    fun `getIndex_correct`() {
        val context = mock(PositionContext::class.java)
        `when`(context.getPositionStart()).thenReturn(4)
        `when`(context.getPositionEnd()).thenReturn(15)

        val adapter = PositionAdapter(context)

        val index0 = adapter.getIndex(4)
        val index1 = adapter.getIndex(5)
        val index11 = adapter.getIndex(15)

        assertEquals(0, index0)
        assertEquals(1, index1)
        assertEquals(11, index11)
    }

    @Test
    fun `getIndex_incorrect`() {
        val context = mock(PositionContext::class.java)
        `when`(context.getPositionStart()).thenReturn(4)
        `when`(context.getPositionEnd()).thenReturn(15)

        val adapter = PositionAdapter(context)

        var errorsCount = 0
        try {
            adapter.getIndex(3)
        } catch (e: IllegalArgumentException) {
            errorsCount++
        }
        try {
            adapter.getIndex(16)
        } catch (e: IllegalArgumentException) {
            errorsCount++
        }

        assertEquals(2, errorsCount)
    }
}