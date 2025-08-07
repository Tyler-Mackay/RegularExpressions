import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

class FloatingPointDetectorTest {
    
    private val detector = FloatingPointDetector()
    
    @Nested
    @DisplayName("Valid Floating Point Tests")
    inner class ValidFloatingPointTests {
        
        @Test
        @DisplayName("Valid examples from requirements should pass")
        fun testRequirementExamples() {
            val validExamples = listOf(
                "1.0",
                "123.34", 
                "0.20000",
                "12349871234.12340981234098",
                ".123"
            )
            validExamples.forEach { number ->
                assertTrue(detector.isValid(number), "Valid example '$number' should pass")
            }
        }
        
        @Test
        @DisplayName("Numbers starting with 1-9 followed by period and digits")
        fun testNonZeroStartWithPeriod() {
            val validNumbers = listOf(
                "1.0", "1.1", "1.23", "1.999",
                "2.0", "2.5", "2.123456",
                "9.0", "9.99999",
                "5.1234567890"
            )
            validNumbers.forEach { number ->
                assertTrue(detector.isValid(number), "Number '$number' should be valid")
            }
        }
        
        @Test
        @DisplayName("Numbers starting with 0 followed by period and digits")
        fun testZeroStartWithPeriod() {
            val validZeroStart = listOf(
                "0.0", "0.1", "0.123", "0.999",
                "0.00001", "0.123456789",
                "0.999999999", "0.5"
            )
            validZeroStart.forEach { number ->
                assertTrue(detector.isValid(number), "Zero-start number '$number' should be valid")
            }
        }
        
        @Test
        @DisplayName("Numbers starting with period (decimal only)")
        fun testPeriodStartNumbers() {
            val validPeriodStart = listOf(
                ".0", ".1", ".123", ".999",
                ".5", ".25", ".75",
                ".123456789", ".999999999",
                ".00001", ".12345"
            )
            validPeriodStart.forEach { number ->
                assertTrue(detector.isValid(number), "Period-start number '$number' should be valid")
            }
        }
        
        @Test
        @DisplayName("Multi-digit integer parts with decimals")
        fun testMultiDigitIntegerParts() {
            val validMultiDigit = listOf(
                "10.0", "11.1", "123.456",
                "999.999", "1000.0001",
                "12345.67890", "999999.123456",
                "123456789.987654321"
            )
            validMultiDigit.forEach { number ->
                assertTrue(detector.isValid(number), "Multi-digit number '$number' should be valid")
            }
        }
        
        @Test
        @DisplayName("Numbers with many decimal places")
        fun testManyDecimalPlaces() {
            val manyDecimals = listOf(
                "1.123456789012345",
                "0.000000000001",
                "123.999999999999999",
                ".123456789012345678901234567890"
            )
            manyDecimals.forEach { number ->
                assertTrue(detector.isValid(number), "Number with many decimals '$number' should be valid")
            }
        }
        
        @Test
        @DisplayName("Edge case valid numbers")
        fun testEdgeCaseValidNumbers() {
            val edgeCases = listOf(
                "0.0",           // Simplest zero decimal
                ".0",            // Simplest period start
                "9.9",           // Max single digits
                "1.000000000",   // Many trailing zeros
                ".999999999"     // Many nines after period
            )
            edgeCases.forEach { number ->
                assertTrue(detector.isValid(number), "Edge case '$number' should be valid")
            }
        }
    }
    
    @Nested
    @DisplayName("Invalid Floating Point Tests")
    inner class InvalidFloatingPointTests {
        
        @Test
        @DisplayName("Invalid examples from requirements should fail")
        fun testRequirementInvalidExamples() {
            val invalidExamples = listOf(
                "123",           // no period
                "123.123.",      // too many periods  
                "123.02a",       // invalid char
                "123.",          // nothing follows period
                "012.4"          // starting 0 not followed by period
            )
            invalidExamples.forEach { number ->
                assertFalse(detector.isValid(number), "Invalid example '$number' should fail")
            }
        }
        
        @Test
        @DisplayName("Numbers without periods should be invalid")
        fun testNumbersWithoutPeriods() {
            val noPeriod = listOf(
                "1", "12", "123", "1234567890",
                "0", "00", "999", "123456"
            )
            noPeriod.forEach { number ->
                assertFalse(detector.isValid(number), "Number without period '$number' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers with multiple periods should be invalid")
        fun testMultiplePeriods() {
            val multiplePeriods = listOf(
                "1.2.3", "12.34.56", "0.1.2",
                ".1.2", "1..2", "123..",
                "..123", ".123.", "1.2.3.4"
            )
            multiplePeriods.forEach { number ->
                assertFalse(detector.isValid(number), "Number with multiple periods '$number' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers with letters should be invalid")
        fun testNumbersWithLetters() {
            val withLetters = listOf(
                "1.2a", "a1.2", "1a.2", "1.a2",
                "123.45b", "x.123", "1.2e3",
                "1.2f", "abc.def", "1.2z3"
            )
            withLetters.forEach { number ->
                assertFalse(detector.isValid(number), "Number with letters '$number' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers with special characters should be invalid")
        fun testNumbersWithSpecialCharacters() {
            val withSpecial = listOf(
                "1.2!", "1@.2", "1.2#3", "1.$2",
                "1.2%", "1.2&3", "1.2*", "1+.2",
                "1-.2", "1.2=3", "1.2/3", "1.2\\3"
            )
            withSpecial.forEach { number ->
                assertFalse(detector.isValid(number), "Number with special chars '$number' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers ending with period only should be invalid")
        fun testNumbersEndingWithPeriod() {
            val endingWithPeriod = listOf(
                "1.", "12.", "123.", "0.",
                "999.", "123456.", "9."
            )
            endingWithPeriod.forEach { number ->
                assertFalse(detector.isValid(number), "Number ending with period '$number' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Invalid zero-start patterns")
        fun testInvalidZeroStartPatterns() {
            val invalidZeroStart = listOf(
                "01.2",      // 0 followed by digit instead of period
                "012.34",    // 0 followed by digits then period
                "0123",      // 0 followed by digits, no period
                "09.5",      // 0 followed by digit then period
                "00.1"       // multiple zeros
            )
            invalidZeroStart.forEach { number ->
                assertFalse(detector.isValid(number), "Invalid zero-start '$number' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Empty and whitespace strings should be invalid")
        fun testEmptyAndWhitespace() {
            val emptyAndWhitespace = listOf(
                "",           // empty string
                " ",          // space
                "  ",         // multiple spaces
                "\t",         // tab
                "\n",         // newline
                " 1.2",       // leading space
                "1.2 ",       // trailing space
                " 1.2 "       // surrounded by spaces
            )
            emptyAndWhitespace.forEach { string ->
                assertFalse(detector.isValid(string), "Empty/whitespace string '$string' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Period-only and invalid period patterns")
        fun testInvalidPeriodPatterns() {
            val invalidPeriodPatterns = listOf(
                ".",          // period only
                "..",         // double period
                "...",        // triple period
                ".a",         // period followed by letter
                ".!",         // period followed by special char
                ". "          // period followed by space
            )
            invalidPeriodPatterns.forEach { pattern ->
                assertFalse(detector.isValid(pattern), "Invalid period pattern '$pattern' should be invalid")
            }
        }
    }
    
    @Nested
    @DisplayName("Edge Cases and Boundary Tests")
    inner class EdgeCasesAndBoundaryTests {
        
        @Test
        @DisplayName("Single character inputs")
        fun testSingleCharacterInputs() {
            // Valid single characters - none should be valid (all need period + digit)
            for (digit in '0'..'9') {
                assertFalse(detector.isValid(digit.toString()), 
                    "Single digit '$digit' should be invalid (no period)")
            }
            
            // Period alone should be invalid
            assertFalse(detector.isValid("."), "Single period should be invalid")
            
            // Letters and special chars should be invalid
            val invalidSingle = listOf("a", "!", "@", "#", "$", "%", "&", "*")
            invalidSingle.forEach { char ->
                assertFalse(detector.isValid(char), "Single character '$char' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Two character combinations")
        fun testTwoCharacterCombinations() {
            // Valid two-character combinations
            val validTwoChar = listOf(".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9")
            validTwoChar.forEach { combo ->
                assertTrue(detector.isValid(combo), "Two-char combo '$combo' should be valid")
            }
            
            // Invalid two-character starting with 0
            val invalidZeroTwo = listOf("01", "02", "03", "04", "05", "06", "07", "08", "09")
            invalidZeroTwo.forEach { combo ->
                assertFalse(detector.isValid(combo), "Two-char combo '$combo' should be invalid")
            }
            
            // 0. should be invalid (needs digit after period)
            assertFalse(detector.isValid("0."), "0. should be invalid (no digit after period)")
        }
        
        @Test
        @DisplayName("Character boundary testing")
        fun testCharacterBoundaries() {
            // Characters just before and after digit range should be invalid
            val beforeAfterDigits = listOf(
                "1./2",       // '/' is ASCII 47, just before '0' (48)
                "1.:2",       // ':' is ASCII 58, just after '9' (57)
                "/1.2",
                ":1.2"
            )
            beforeAfterDigits.forEach { string ->
                assertFalse(detector.isValid(string), 
                    "String with boundary character '$string' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Very long valid floating point numbers")
        fun testVeryLongNumbers() {
            val veryLongNumbers = listOf(
                "1234567890123456789.0123456789012345",
                "0." + "1".repeat(100),                    // 0. followed by 100 ones
                "9".repeat(50) + "." + "8".repeat(50),     // 50 nines, period, 50 eights
                "." + "5".repeat(200)                      // Period followed by 200 fives
            )
            veryLongNumbers.forEach { number ->
                assertTrue(detector.isValid(number), "Very long number '$number' should be valid")
            }
        }
    }
    
    @Nested
    @DisplayName("State Machine Verification")
    inner class StateMachineTests {
        
        @Test
        @DisplayName("Verify FirstValue state transitions")
        fun testFirstValueTransitions() {
            // Should transition to ZeroFirst on '0'
            // Should transition to HasDigits on '1'-'9'  
            // Should transition to HasPeriod on '.'
            // Should transition to Invalid on anything else
            
            val testCases = mapOf(
                "0.1" to "FirstValue->ZeroFirst->HasPeriod->Valid",
                "1.0" to "FirstValue->HasDigits->HasPeriod->Valid",
                ".5" to "FirstValue->HasPeriod->Valid",
                "a" to "FirstValue->Invalid"
            )
            
            testCases.forEach { (input, description) ->
                val expected = !input.equals("a") // Only 'a' should be invalid
                assertEquals(expected, detector.isValid(input), 
                    "Input '$input' with transitions '$description' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify ZeroFirst state transitions")
        fun testZeroFirstTransitions() {
            // After '0', only '.' should be valid
            val testCases = mapOf(
                "0.1" to true,   // Valid: 0 -> . -> digit
                "01" to false,   // Invalid: 0 -> digit  
                "0a" to false,   // Invalid: 0 -> letter
                "0." to false    // Invalid: 0 -> . (but no digit after)
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "ZeroFirst transition test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify HasDigits state transitions")
        fun testHasDigitsTransitions() {
            // Should stay in HasDigits for more digits
            // Should go to HasPeriod on '.'
            // Should go to Invalid on anything else
            
            val testCases = mapOf(
                "123.4" to true,     // Valid: digits -> period -> digit
                "123" to false,      // Invalid: digits only (no period)
                "123a" to false,     // Invalid: digits -> letter
                "123." to false      // Invalid: digits -> period (no digit after)
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "HasDigits transition test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify HasPeriod state transitions")  
        fun testHasPeriodTransitions() {
            // Should go to Valid on first digit
            // Should go to Invalid on anything else
            
            val testCases = mapOf(
                ".5" to true,        // Valid: period -> digit
                ".a" to false,       // Invalid: period -> letter
                "." to false,        // Invalid: period only
                "1." to false        // Invalid: digit -> period (no digit after)
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "HasPeriod transition test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify Valid state transitions")
        fun testValidStateTransitions() {
            // Should stay in Valid for more digits
            // Should go to Invalid for anything else
            
            val testCases = mapOf(
                "1.23456" to true,   // Valid: stay in Valid for more digits
                "1.2a" to false,     // Invalid: Valid -> letter
                "1.2." to false,     // Invalid: Valid -> period (second period)
                "1.2!" to false      // Invalid: Valid -> special char
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "Valid state transition test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Complete state machine path verification")
        fun testCompleteStatePaths() {
            val pathTests = listOf(
                Triple("1.5", true, "FirstValue->HasDigits->HasPeriod->Valid"),
                Triple("0.7", true, "FirstValue->ZeroFirst->HasPeriod->Valid"), 
                Triple(".9", true, "FirstValue->HasPeriod->Valid"),
                Triple("12.34", true, "FirstValue->HasDigits->HasDigits->HasPeriod->Valid->Valid"),
                Triple("01.2", false, "FirstValue->ZeroFirst->Invalid"),
                Triple("1.2.3", false, "FirstValue->HasDigits->HasPeriod->Valid->Invalid"),
                Triple("123", false, "FirstValue->HasDigits->HasDigits->HasDigits (not Valid)")
            )
            
            pathTests.forEach { (input, expected, path) ->
                assertEquals(expected, detector.isValid(input), 
                    "Complete path '$input' ($path) should be $expected")
            }
        }
    }
}