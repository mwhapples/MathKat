package onl.mdw.mathkat

import kotlin.test.Test
import kotlin.test.assertEquals

class MathKatTest {
    @Test
    fun testGreeting() {
        assertEquals("Hello, World!", MathKat.hello("World"))
    }
    @Test
    fun testGetVersion() {
        assertEquals("0.1.22", MathKat.getVersion())
    }
}