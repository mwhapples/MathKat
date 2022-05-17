package onl.mdw.mathkat

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MathKatTest {
    @Test
    fun testGreeting() {
        assertEquals("Hello, World!", MathKat.hello("World"))
    }
    @Test
    fun testGetVersion() {
        assertEquals("0.1.22", MathKat.getVersion())
    }
    @Test
    fun testExceptionForInvalidRulesDirectory() {
        val exceptionMessage = assertFailsWith(RuntimeException::class) { MathKat.setRulesDir("someinvalidDirectory") }
        val expected = "MathCAT could not find a rules dir -- something failed in installation?\nCould not find rules dir in someinvalidDirectory or lacking permissions to read the dir!\n\n"
        assertEquals(expected, exceptionMessage.message)
    }
    @Test
    fun testSetRulesDirectoryCorrectlyDoesNotExcept() {
        MathKat.setRulesDir(System.getProperty("onl.mdw.mathkat.rulesDir"))
    }
}