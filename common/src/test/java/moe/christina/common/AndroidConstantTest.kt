package moe.christina.common

import moe.christina.common.android.AndroidConstant
import org.junit.Assert.assertEquals
import org.junit.Test

class AndroidConstantTest {
    @Test
    fun logTag_checkValue() {
        val actual = AndroidConstant.logTag<AndroidConstantTest>()
        val expected = AndroidConstantTest::class.qualifiedName

        assertEquals(expected, actual)
    }

    @Test
    fun savedStateKey_checkValue() {
        val keyName = "saved_state"
        val actual = AndroidConstant.savedStateKey<AndroidConstantTest>(keyName)
        val expected = "${AndroidConstantTest::class.qualifiedName}:$keyName"

        assertEquals(expected, actual)
    }
}
