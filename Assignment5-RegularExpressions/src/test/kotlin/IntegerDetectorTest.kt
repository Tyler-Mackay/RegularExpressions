import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

class IntegerDetectorTest {
    
    private val detector = IntegerDetector()
    
    @Nested
    @DisplayName("Valid Integer Tests")
    inner class ValidIntegerTests {
        
        @Test
        @DisplayName("Single digit 1-9 should be valid")
        fun testSingleDigits() {
            val validSingleDigits = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
            validSingleDigits.forEach { digit ->
                assertTrue(detector.isValid(digit), "Single digit '$digit' should be valid")
            }
        }
        
        @Test
        @DisplayName("Two digit numbers should be valid")
        fun testTwoDigitNumbers() {
            val validTwoDigits = listOf("10", "11", "23", "45", "67", "89", "99")
            validTwoDigits.forEach { number ->
                assertTrue(detector.isValid(number), "Two digit number '$number' should be valid")
            }
        }
        
        @Test
        @DisplayName("Multi-digit numbers should be valid")
        fun testMultiDigitNumbers() {
            val validMultiDigits = listOf(
                "123", 
                "456", 
                "1000", 
                "12345", 
                "999999", 
                "1234567890"
            )
            validMultiDigits.forEach { number ->
                assertTrue(detector.isValid(number), "Multi-digit number '$number' should be valid")
            }
        }
        
        @Test
        @DisplayName("Very long valid integers should be valid")
        fun testVeryLongIntegers() {
            val veryLongNumbers = listOf(
                "3452342352434534524346",
                "123456789012345678901234567890",
                "1" + "0".repeat(100), // 1 followed by 100 zeros
                "9" + "8".repeat(50) + "7".repeat(50)
            )
            veryLongNumbers.forEach { number ->
                assertTrue(detector.isValid(number), "Very long number '$number' should be valid")
            }
        }
        
        @Test
        @DisplayName("Numbers with all possible digits should be valid")
        fun testAllDigitCombinations() {
            // Test that each digit 0-9 can appear after the first digit
            for (firstDigit in 1..9) {
                for (secondDigit in 0..9) {
                    val number = "$firstDigit$secondDigit"
                    assertTrue(detector.isValid(number), "Number '$number' should be valid")
                }
            }
        }
    }
    
    @Nested
    @DisplayName("Invalid Integer Tests")
    inner class InvalidIntegerTests {
        
        @Test
        @DisplayName("Empty string should be invalid")
        fun testEmptyString() {
            assertFalse(detector.isValid(""), "Empty string should be invalid")
        }
        
        @Test
        @DisplayName("Single zero should be invalid")
        fun testSingleZero() {
            assertFalse(detector.isValid("0"), "Single zero '0' should be invalid")
        }
        
        @Test
        @DisplayName("Numbers starting with zero should be invalid")
        fun testNumbersStartingWithZero() {
            val invalidZeroStart = listOf(
                "01", 
                "02", 
                "0123", 
                "0000", 
                "0999", 
                "01234567890"
            )
            invalidZeroStart.forEach { number ->
                assertFalse(detector.isValid(number), "Number starting with zero '$number' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers with letters should be invalid")
        fun testNumbersWithLetters() {
            val invalidWithLetters = listOf(
                "1a", 
                "12b", 
                "a123", 
                "132a", 
                "1a2", 
                "123abc", 
                "1z", 
                "A123", 
                "123Z"
            )
            invalidWithLetters.forEach { number ->
                assertFalse(detector.isValid(number), "Number with letters '$number' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers with special characters should be invalid")
        fun testNumbersWithSpecialCharacters() {
            val invalidWithSpecial = listOf(
                "1!", 
                "12@", 
                "123#", 
                "1$2", 
                "12%3", 
                "1&23", 
                "123*", 
                "1+2", 
                "1-2", 
                "1.2", 
                "1,234", 
                "1 2", 
                "123 ", 
                " 123"
            )
            invalidWithSpecial.forEach { number ->
                assertFalse(detector.isValid(number), "Number with special character '$number' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Strings starting with letters should be invalid")
        fun testStringsStartingWithLetters() {
            val invalidStartingWithLetter = listOf(
                "a", 
                "abc", 
                "a123", 
                "letter123", 
                "Z999"
            )
            invalidStartingWithLetter.forEach { string ->
                assertFalse(detector.isValid(string), "String starting with letter '$string' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Strings with mixed content should be invalid")
        fun testMixedContent() {
            val invalidMixed = listOf(
                "12a34", 
                "1@2#3", 
                "123 456", 
                "1.23", 
                "12-34", 
                "12+34", 
                "1e5", 
                "123.0"
            )
            invalidMixed.forEach { string ->
                assertFalse(detector.isValid(string), "Mixed content string '$string' should be invalid")
            }
        }
    }
    
    @Nested
    @DisplayName("Edge Cases")
    inner class EdgeCaseTests {
        
        @Test
        @DisplayName("Whitespace strings should be invalid")
        fun testWhitespaceStrings() {
            val whitespaceStrings = listOf(" ", "  ", "\t", "\n", "\r", " \t\n ")
            whitespaceStrings.forEach { string ->
                assertFalse(detector.isValid(string), "Whitespace string should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers with leading/trailing whitespace should be invalid")
        fun testNumbersWithWhitespace() {
            val numbersWithWhitespace = listOf(" 123", "123 ", " 123 ", "\t123", "123\n")
            numbersWithWhitespace.forEach { string ->
                assertFalse(detector.isValid(string), "Number with whitespace '$string' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Single character edge cases")
        fun testSingleCharacterEdgeCases() {
            // Valid single characters
            for (digit in '1'..'9') {
                assertTrue(detector.isValid(digit.toString()), "Single digit '$digit' should be valid")
            }
            
            // Invalid single characters
            val invalidSingle = listOf("0", "a", "!", " ", "@", "#")
            invalidSingle.forEach { char ->
                assertFalse(detector.isValid(char), "Single character '$char' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Unicode and special number formats should be invalid")
        fun testUnicodeAndSpecialFormats() {
            val specialFormats = listOf(
                "①", // Unicode number
                "Ⅰ", // Roman numeral
                "𝟏𝟐𝟑", // Unicode mathematical digits
                "1²", // Superscript
                "₁₂₃", // Subscript
                "½", // Fraction
                "∞" // Infinity
            )
            specialFormats.forEach { string ->
                assertFalse(detector.isValid(string), "Special format '$string' should be invalid")
            }
        }
    }
    
    @Nested
    @DisplayName("Boundary Tests")
    inner class BoundaryTests {
        
        @Test
        @DisplayName("Test all digit boundaries")
        fun testAllDigitBoundaries() {
            // Test that 0 is invalid as first digit
            assertFalse(detector.isValid("0"), "Zero should be invalid as single digit")
            
            // Test that 1-9 are valid as first digits
            for (i in 1..9) {
                assertTrue(detector.isValid(i.toString()), "Digit $i should be valid as single digit")
            }
            
            // Test that 0-9 are valid as second digits when first digit is 1-9
            for (first in 1..9) {
                for (second in 0..9) {
                    val number = "$first$second"
                    assertTrue(detector.isValid(number), "Number $number should be valid")
                }
            }
        }
        
        @Test
        @DisplayName("Test character boundaries")
        fun testCharacterBoundaries() {
            // Characters just before and after digit range should be invalid
            val beforeAfterDigits = listOf(
                "/123", // '/' is ASCII 47, just before '0' (48)
                ":123", // ':' is ASCII 58, just after '9' (57)
                "1/23",
                "12:3"
            )
            beforeAfterDigits.forEach { string ->
                assertFalse(detector.isValid(string), "String with boundary character '$string' should be invalid")
            }
        }
    }
    
    @Nested
    @DisplayName("State Machine Verification")
    inner class StateMachineTests {
        
        @Test
        @DisplayName("Verify state transitions for valid inputs")
        fun testValidStateTransitions() {
            // These should follow: FirstInteger -> Valid -> Valid -> ... -> Valid
            val testCases = mapOf(
                "1" to "FirstInteger->Valid",
                "12" to "FirstInteger->Valid->Valid", 
                "123" to "FirstInteger->Valid->Valid->Valid"
            )
            
            testCases.forEach { (input, description) ->
                assertTrue(detector.isValid(input), 
                    "Input '$input' with transitions '$description' should be valid")
            }
        }
        
        @Test
        @DisplayName("Verify state transitions for invalid inputs")
        fun testInvalidStateTransitions() {
            // These should hit Invalid state and stay there
            val testCases = mapOf(
                "0" to "FirstInteger->Invalid",
                "01" to "FirstInteger->Invalid->Invalid",
                "1a" to "FirstInteger->Valid->Invalid",
                "12a3" to "FirstInteger->Valid->Valid->Invalid->Invalid"
            )
            
            testCases.forEach { (input, description) ->
                assertFalse(detector.isValid(input), 
                    "Input '$input' with transitions '$description' should be invalid")
            }
        }
    }
}