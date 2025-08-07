import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

class BinaryDetectorTest {
    
    private val detector = BinaryDetector()
    
    @Nested
    @DisplayName("Valid Binary Number Tests")
    inner class ValidBinaryTests {
        
        @Test
        @DisplayName("Valid examples from requirements should pass")
        fun testRequirementExamples() {
            val validExamples = listOf(
                "1",
                "11", 
                "101",
                "111111",
                "10011010001"
            )
            validExamples.forEach { binary ->
                assertTrue(detector.isValid(binary), "Valid example '$binary' should pass")
            }
        }
        
        @Test
        @DisplayName("Single digit 1 should be valid")
        fun testSingleOne() {
            assertTrue(detector.isValid("1"), "Single '1' should be valid")
        }
        
        @Test
        @DisplayName("Numbers starting and ending with 1")
        fun testStartEndWithOne() {
            val validBinaries = listOf(
                "11",           // Two ones
                "111",          // Three ones
                "1111",         // Four ones
                "11111",        // Five ones
                "101",          // 1-0-1 pattern
                "111",          // All ones
                "1001",         // 1-00-1 pattern
                "1101",         // 1-10-1 pattern
                "1011",         // 1-01-1 pattern
                "10101",        // Alternating pattern
                "11011",        // Complex pattern
                "110011",       // Groups pattern
                "1010101"       // Long alternating
            )
            validBinaries.forEach { binary ->
                assertTrue(detector.isValid(binary), "Valid binary '$binary' should pass")
            }
        }
        
        @Test
        @DisplayName("Long valid binary numbers")
        fun testLongValidBinaries() {
            val longValidBinaries = listOf(
                "1" + "0".repeat(100) + "1",           // 1 followed by 100 zeros, ending with 1
                "1" + "01".repeat(50) + "1",           // Alternating pattern
                "111111111111111111111111111",         // All ones (27 ones)
                "1010101010101010101010101",           // Long alternating ending with 1
                "11111000001111100000111111",          // Groups of ones and zeros
                "1" + "1010".repeat(25) + "1"          // Repeated pattern
            )
            longValidBinaries.forEach { binary ->
                assertTrue(detector.isValid(binary), "Long valid binary '$binary' should pass")
            }
        }
        
        @Test
        @DisplayName("Edge case valid patterns")
        fun testEdgeCaseValidPatterns() {
            val edgeCases = listOf(
                "1" + "0".repeat(1000) + "1",         // Very long with zeros
                "1" + "1".repeat(500),                 // Very long all ones
                "101010101010101010101010101"          // Very long alternating ending with 1
            )
            edgeCases.forEach { binary ->
                assertTrue(detector.isValid(binary), "Edge case '$binary' should be valid")
            }
        }
    }
    
    @Nested
    @DisplayName("Invalid Binary Number Tests")
    inner class InvalidBinaryTests {
        
        @Test
        @DisplayName("Invalid examples from requirements should fail")
        fun testRequirementInvalidExamples() {
            val invalidExamples = listOf(
                "01",           // doesn't start with 1
                "10",           // doesn't end with 1
                "1000010",      // doesn't end with 1
                "100a01"        // contains invalid char
            )
            invalidExamples.forEach { binary ->
                assertFalse(detector.isValid(binary), "Invalid example '$binary' should fail")
            }
        }
        
        @Test
        @DisplayName("Empty string should be invalid")
        fun testEmptyString() {
            assertFalse(detector.isValid(""), "Empty string should be invalid")
        }
        
        @Test
        @DisplayName("Single zero should be invalid")
        fun testSingleZero() {
            assertFalse(detector.isValid("0"), "Single '0' should be invalid")
        }
        
        @Test
        @DisplayName("Numbers starting with 0 should be invalid")
        fun testStartingWithZero() {
            val startingWithZero = listOf(
                "0",            // Single zero
                "01",           // Zero then one
                "00",           // Two zeros
                "001",          // Zero-zero-one
                "010",          // Zero-one-zero
                "011",          // Zero-one-one
                "0101",         // Zero then alternating
                "01111",        // Zero then all ones
                "000001",       // Multiple zeros then one
                "0123456789"    // Zero then various chars
            )
            startingWithZero.forEach { binary ->
                assertFalse(detector.isValid(binary), "Binary starting with 0 '$binary' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers ending with 0 should be invalid")
        fun testEndingWithZero() {
            val endingWithZero = listOf(
                "10",           // One-zero
                "110",          // One-one-zero
                "100",          // One-zero-zero
                "1010",         // Alternating ending with zero
                "11110",        // Ones then zero
                "10100",        // Complex ending with zero
                "111000",       // Groups ending with zeros
                "101010"        // Long alternating ending with zero
            )
            endingWithZero.forEach { binary ->
                assertFalse(detector.isValid(binary), "Binary ending with 0 '$binary' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers with letters should be invalid")
        fun testNumbersWithLetters() {
            val withLetters = listOf(
                "1a",           // One then letter
                "a1",           // Letter then one
                "1a1",          // Letter in middle
                "10a1",         // Letter in middle of valid pattern
                "1ab1",         // Multiple letters
                "1A1",          // Capital letter
                "1z1",          // Different letter
                "abc",          // All letters
                "1x0y1",        // Multiple letters scattered
                "binary",       // Word
                "1e1",          // Scientific notation letter
                "1o1"           // Letter that looks like zero
            )
            withLetters.forEach { binary ->
                assertFalse(detector.isValid(binary), "Binary with letters '$binary' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers with special characters should be invalid")
        fun testNumbersWithSpecialCharacters() {
            val withSpecial = listOf(
                "1!1",          // Exclamation
                "1@1",          // At symbol
                "1#1",          // Hash
                "1$1",          // Dollar
                "1%1",          // Percent
                "1^1",          // Caret
                "1&1",          // Ampersand
                "1*1",          // Asterisk
                "1+1",          // Plus
                "1-1",          // Minus
                "1=1",          // Equals
                "1.1",          // Period
                "1,1",          // Comma
                "1?1",          // Question mark
                "1/1",          // Forward slash
                "1\\1",         // Backslash
                "1|1",          // Pipe
                "1 1",          // Space
                "1\t1",         // Tab
                "1\n1"          // Newline
            )
            withSpecial.forEach { binary ->
                assertFalse(detector.isValid(binary), "Binary with special chars '$binary' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers with digits 2-9 should be invalid")
        fun testNumbersWithHigherDigits() {
            val withHigherDigits = listOf(
                "121",          // Contains 2
                "131",          // Contains 3
                "141",          // Contains 4
                "151",          // Contains 5
                "161",          // Contains 6
                "171",          // Contains 7
                "181",          // Contains 8
                "191",          // Contains 9
                "1231",         // Multiple higher digits
                "19876543221",  // Many higher digits
                "12345678901"   // All digits
            )
            withHigherDigits.forEach { binary ->
                assertFalse(detector.isValid(binary), "Binary with digits 2-9 '$binary' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Mixed invalid content should be invalid")
        fun testMixedInvalidContent() {
            val mixedInvalid = listOf(
                "1a0b1",        // Letters and valid digits
                "1 0 1",        // Spaces between digits
                "1.0.1",        // Periods between digits
                "1,0,1",        // Commas between digits
                "1-0-1",        // Dashes between digits
                "101abc101",    // Valid pattern with letters
                "101 101",      // Valid patterns with space
                "1/0/1",        // Slashes between digits
                "1\\0\\1"       // Backslashes between digits
            )
            mixedInvalid.forEach { binary ->
                assertFalse(detector.isValid(binary), "Mixed invalid content '$binary' should be invalid")
            }
        }
    }
    
    @Nested
    @DisplayName("Edge Cases and Boundary Tests")
    inner class EdgeCasesAndBoundaryTests {
        
        @Test
        @DisplayName("Whitespace strings should be invalid")
        fun testWhitespaceStrings() {
            val whitespaceStrings = listOf(
                " ",            // Single space
                "  ",           // Multiple spaces
                "\t",           // Tab
                "\n",           // Newline
                "\r",           // Carriage return
                " \t\n ",       // Mixed whitespace
                "   "           // Three spaces
            )
            whitespaceStrings.forEach { string ->
                assertFalse(detector.isValid(string), "Whitespace string should be invalid")
            }
        }
        
        @Test
        @DisplayName("Numbers with leading/trailing whitespace should be invalid")
        fun testNumbersWithWhitespace() {
            val numbersWithWhitespace = listOf(
                " 1",           // Leading space
                "1 ",           // Trailing space
                " 101 ",        // Surrounded by spaces
                "\t101",        // Leading tab
                "101\n",        // Trailing newline
                " 1 0 1 "       // Spaces throughout
            )
            numbersWithWhitespace.forEach { string ->
                assertFalse(detector.isValid(string), "Number with whitespace '$string' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Unicode and special number formats should be invalid")
        fun testUnicodeAndSpecialFormats() {
            val specialFormats = listOf(
                "①①",          // Unicode numbers
                "𝟏𝟎𝟏",        // Unicode mathematical digits
                "１０１",        // Full-width digits
                "¹⁰¹",          // Superscript
                "₁₀₁"           // Subscript
            )
            specialFormats.forEach { string ->
                assertFalse(detector.isValid(string), "Special format '$string' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Very long invalid strings")
        fun testVeryLongInvalidStrings() {
            val veryLongInvalid = listOf(
                "0" + "1".repeat(1000),                // Starts with 0
                "1" + "0".repeat(1000),                 // Ends with 0
                "1" + "01".repeat(500) + "0",           // Long pattern ending with 0
                "1" + "2".repeat(100) + "1",            // Contains invalid digit 2
                "1" + "a".repeat(50) + "1"              // Contains letters
            )
            veryLongInvalid.forEach { string ->
                assertFalse(detector.isValid(string), "Very long invalid string should be invalid")
            }
        }
    }
    
    @Nested
    @DisplayName("State Machine Verification")
    inner class StateMachineTests {
        
        @Test
        @DisplayName("Verify AwaitingStart1 state transitions")
        fun testAwaitingStart1Transitions() {
            // Should only accept '1' as first character
            val testCases = mapOf(
                "1" to true,     // Valid: starts with 1, ends with 1
                "0" to false,    // Invalid: starts with 0
                "a" to false,    // Invalid: starts with letter
                "!" to false     // Invalid: starts with special char
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "AwaitingStart1 test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify Reading state transitions and end conditions")
        fun testReadingStateTransitions() {
            val testCases = mapOf(
                // Valid: proper binary ending with 1
                "11" to true,
                "101" to true,
                "111" to true,
                "1001" to true,
                "10101" to true,
                
                // Invalid: proper binary but ending with 0
                "10" to false,
                "110" to false,
                "100" to false,
                "1010" to false,
                
                // Invalid: contains invalid characters
                "1a1" to false,
                "1!1" to false,
                "121" to false,
                "1 1" to false
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "Reading state test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Complete state machine path verification")
        fun testCompleteStatePaths() {
            val pathTests = listOf(
                Triple("1", true, "AwaitingStart1->Reading (ends with 1)"),
                Triple("101", true, "AwaitingStart1->Reading->Reading->Reading (ends with 1)"),
                Triple("10", false, "AwaitingStart1->Reading->Reading (ends with 0)"),
                Triple("01", false, "AwaitingStart1->Invalid"),
                Triple("1a1", false, "AwaitingStart1->Reading->Invalid"),
                Triple("", false, "Empty string check")
            )
            
            pathTests.forEach { (input, expected, description) ->
                assertEquals(expected, detector.isValid(input), 
                    "Path test '$input' ($description) should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify both state and end condition requirements")
        fun testStateAndEndConditions() {
            // These test the combination of state machine validation AND end condition
            val dualConditionTests = mapOf(
                // Valid state + valid ending
                "1" to true,
                "111" to true,
                "101" to true,
                
                // Valid state + invalid ending  
                "10" to false,
                "1100" to false,
                
                // Invalid state + valid ending (should still be invalid)
                "1a1" to false,      // Invalid char but ends with 1
                "121" to false,      // Invalid digit but ends with 1
                
                // Invalid state + invalid ending
                "1a0" to false,      // Invalid char and ends with 0
                "120" to false       // Invalid digit and ends with 0
            )
            
            dualConditionTests.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "Dual condition test '$input' should be $expected")
            }
        }
    }
    
    @Nested
    @DisplayName("Requirements Verification")
    inner class RequirementsTests {
        
        @Test
        @DisplayName("Must start with 1 requirement")
        fun testMustStartWithOne() {
            // All valid binaries must start with 1
            val validStarting = listOf("1", "11", "101", "111", "1010101")
            validStarting.forEach { binary ->
                assertTrue(binary.startsWith("1"), "Test setup: '$binary' should start with 1")
                // Only valid if also ends with 1
                val shouldBeValid = binary.endsWith("1") && binary.all { it in "01" }
                assertEquals(shouldBeValid, detector.isValid(binary))
            }
            
            // All starting with 0 should be invalid
            val invalidStarting = listOf("0", "01", "011", "0101", "01010")
            invalidStarting.forEach { binary ->
                assertFalse(detector.isValid(binary), "Binary starting with 0 '$binary' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Must end with 1 requirement")
        fun testMustEndWithOne() {
            // Valid patterns that end with 1
            val validEnding = listOf("1", "11", "101", "111", "1011")
            validEnding.forEach { binary ->
                assertTrue(binary.endsWith("1"), "Test setup: '$binary' should end with 1")
                assertTrue(detector.isValid(binary), "Binary ending with 1 '$binary' should be valid")
            }
            
            // Valid binary patterns but ending with 0 should be invalid
            val invalidEnding = listOf("10", "110", "100", "1010", "1110")
            invalidEnding.forEach { binary ->
                assertTrue(binary.startsWith("1"), "Test setup: '$binary' should start with 1")
                assertTrue(binary.all { it in "01" }, "Test setup: '$binary' should only contain 0,1")
                assertFalse(detector.isValid(binary), "Binary ending with 0 '$binary' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Only 0 and 1 characters allowed requirement")
        fun testOnlyZeroOneAllowed() {
            // Valid: only contains 0 and 1
            val onlyZeroOne = listOf("1", "10", "01", "101", "110", "1001")
            onlyZeroOne.forEach { binary ->
                assertTrue(binary.all { it in "01" }, "Test setup: '$binary' should only contain 0,1")
                // Validity depends on start/end conditions
            }
            
            // Invalid: contains other characters
            val withOtherChars = listOf("1a1", "121", "1!1", "1 1", "1.1")
            withOtherChars.forEach { binary ->
                assertFalse(binary.all { it in "01" }, "Test setup: '$binary' should contain non-binary chars")
                assertFalse(detector.isValid(binary), "Binary with non-binary chars '$binary' should be invalid")
            }
        }
        
        @Test
        @DisplayName("All three requirements together")
        fun testAllRequirementsCombined() {
            // Valid: starts with 1, ends with 1, only contains 0,1
            val fullyValid = listOf("1", "11", "101", "111", "1001", "1011", "10101")
            fullyValid.forEach { binary ->
                assertTrue(binary.startsWith("1"), "Test setup: '$binary' should start with 1")
                assertTrue(binary.endsWith("1"), "Test setup: '$binary' should end with 1") 
                assertTrue(binary.all { it in "01" }, "Test setup: '$binary' should only contain 0,1")
                assertTrue(detector.isValid(binary), "Fully valid binary '$binary' should be valid")
            }
            
            // Invalid: fails at least one requirement
            val partiallyInvalid = listOf(
                "01",      // Wrong start
                "10",      // Wrong end  
                "1a1",     // Wrong characters
                "012",     // Wrong start + characters
                "1a0",     // Wrong characters + end
                "0a0"      // Wrong everything
            )
            partiallyInvalid.forEach { binary ->
                assertFalse(detector.isValid(binary), "Partially invalid binary '$binary' should be invalid")
            }
        }
    }
}