package onl.mdw.mathkat

import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

// Keep the setRulesDir tests separate, we don't need to have setRulesDir called before, and we probably want them separate in the reports as well.
class MathKatRulesDirTests {
    @Test
    fun testExceptionForInvalidRulesDirectory() {
        val invalidRulesDir = File(System.getProperty("onl.mdw.mathkat.rulesDir"), "invalidDir").absolutePath
        val exceptionMessage = assertFailsWith(RuntimeException::class) { MathKat.setRulesDir(invalidRulesDir) }
        val expected = "MathCAT could not find a rules dir -- something failed in installation?\nCould not find rules dir in $invalidRulesDir or lacking permissions to read the dir!\n\n"
        assertEquals(expected, exceptionMessage.message)
    }
    @Test
    fun testSetRulesDirectoryCorrectlyDoesNotExcept() {
        MathKat.setRulesDir(System.getProperty("onl.mdw.mathkat.rulesDir"))
    }
}
class MathKatTest {
    @BeforeTest
    fun configureRules() {
        MathKat.setRulesDir(System.getProperty("onl.mdw.mathkat.rulesDir"))
    }
    @Test
    fun testGreeting() {
        assertEquals("Hello, World!", MathKat.hello("World"))
    }
    @Test
    fun testGetVersion() {
        assertEquals("0.1.22", MathKat.getVersion())
    }
    @Test
    fun testSetInvalidMathml() {
        val mathml = "Some random string"
        assertFailsWith(RuntimeException::class) { MathKat.setMathml(mathml) }
    }
    @Test
    fun testReturnsWhenSetValidMathml() {
        val someMathml = "<math id='mkt-0'><mrow id='mkt-1'><mn id='mkt-2'>1</mn><mo id='mkt-3'>+</mo><mi id='mkt-4'>x</mi></mrow></math>"
        val expectedMathml = " <math id='mkt-0'>\n" +
                "  <mrow id='mkt-1'>\n" +
                "    <mn id='mkt-2'>1</mn>\n" +
                "    <mo id='mkt-3'>+</mo>\n" +
                "    <mi id='mkt-4'>x</mi>\n" +
                "  </mrow>\n" +
                " </math>\n"
        assertEquals(expectedMathml, MathKat.setMathml(someMathml))
    }
    @Test
    fun testGetAllBraille() {
        val mathml = "<math><mrow><mi>y</mi><mo>=</mo><mi>x</mi><mo>+</mo><mn>2</mn></mrow></math>"
        MathKat.setMathml(mathml)
        val expected = "⠽⠀⠨⠅⠀⠭⠬⠆"
        assertEquals(expected, MathKat.getBraille())
    }
}
