import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

class BinaryDetectorTest {
    
    private val detector = BinaryDetector()
    
    @Nested
    @DisplayName("Core Requirements Tests")
    inner class CoreRequirementsTests {
        
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
        @DisplayName("Must start with 1 requirement")
        fun testMustStartWithOne() {
            // Valid cases starting with 1
            val validStarting = listOf("1", "11", "101", "111", "1011")
            validStarting.forEach { binary ->
                assertTrue(detector.isValid(binary), "Binary starting with 1 '$binary' should be valid")
            }
            
            // Invalid cases starting with 0
            val invalidStarting = listOf("0", "01", "011", "0101")
            invalidStarting.forEach { binary ->
                assertFalse(detector.isValid(binary), "Binary starting with 0 '$binary' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Must end with 1 requirement")
        fun testMustEndWithOne() {
            // Valid cases ending with 1
            val validEnding = listOf("1", "11", "101", "111", "1011")
            validEnding.forEach { binary ->
                assertTrue(detector.isValid(binary), "Binary ending with 1 '$binary' should be valid")
            }
            
            // Invalid cases ending with 0
            val invalidEnding = listOf("10", "110", "100", "1010")
            invalidEnding.forEach { binary ->
                assertFalse(detector.isValid(binary), "Binary ending with 0 '$binary' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Only 0 and 1 characters allowed")
        fun testOnlyBinaryDigitsAllowed() {
            // Valid: only contains 0 and 1
            val onlyBinary = listOf("1", "101", "111", "1001", "10101")
            onlyBinary.forEach { binary ->
                assertTrue(detector.isValid(binary), "Valid binary '$binary' should pass")
            }
            
            // Invalid: contains other characters
            val withInvalidChars = listOf("1a1", "121", "1!1", "1 1", "1.1")
            withInvalidChars.forEach { binary ->
                assertFalse(detector.isValid(binary), "Binary with invalid chars '$binary' should fail")
            }
        }
    }
    
    @Nested
    @DisplayName("Edge Cases Tests")
    inner class EdgeCasesTests {
        
        @Test
        @DisplayName("Empty and single character tests")
        fun testEmptyAndSingleChars() {
            assertFalse(detector.isValid(""), "Empty string should be invalid")
            assertFalse(detector.isValid("0"), "Single '0' should be invalid")
            assertTrue(detector.isValid("1"), "Single '1' should be valid")
        }
        
        @Test
        @DisplayName("ASCII character boundary tests")
        fun testCharacterBoundaries() {
            // Characters immediately adjacent to '0' and '1' in ASCII
            val boundaryTests = listOf(
                "1/1",  // '/' is ASCII 47, just before '0' (48)
                "1:1",  // ':' is ASCII 58, just after '9' (57)
                "1.1",  // '.' is ASCII 46, just before '/'
                "1;1",  // ';' is ASCII 59, just after ':'
                "1,1",  // ',' is ASCII 44
                "1-1"   // '-' is ASCII 45
            )
            boundaryTests.forEach { binary ->
                assertFalse(detector.isValid(binary), 
                    "Binary with boundary character '$binary' should be invalid")
            }
        }
        
        
        @Test
        @DisplayName("Whitespace and special character tests")
        fun testWhitespaceAndSpecialChars() {
            val invalidChars = listOf(
                "1 1",   // Space
                "1\t1",  // Tab  
                "1\n1",  // Newline
                "1!1",   // Special characters
                "1@1",
                "1#1",
                "1$1"
            )
            invalidChars.forEach { binary ->
                assertFalse(detector.isValid(binary), 
                    "Binary with invalid character '$binary' should be invalid")
            }
        }
        
        
        @Test
        @DisplayName("Invalid digits 2-9 tests")
        fun testInvalidDigits() {
            val invalidDigits = listOf(
                "121", "131", "141", "151",
                "161", "171", "181", "191"
            )
            invalidDigits.forEach { binary ->
                assertFalse(detector.isValid(binary), 
                    "Binary with digit 2-9 '$binary' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Letter character tests")
        fun testLetterCharacters() {
            val withLetters = listOf(
                "1a1", "1A1", "1z1", "1Z1",
                "abc", "1letter1"
            )
            withLetters.forEach { binary ->
                assertFalse(detector.isValid(binary), 
                    "Binary with letters '$binary' should be invalid")
            }
        }
    }
    
    @Nested
    @DisplayName("Performance and Long String Tests")
    inner class PerformanceTests {
        
        @Test
        @DisplayName("Long valid binary strings")
        fun testLongValidStrings() {
            val longValid = listOf(
                "1" + "0".repeat(1000) + "1",
                "1" + "1".repeat(1000),
                "1" + "01".repeat(500) + "1"
            )
            longValid.forEach { binary ->
                assertTrue(detector.isValid(binary), 
                    "Long valid binary should pass")
            }
        }
        
        @Test
        @DisplayName("Long invalid binary strings")
        fun testLongInvalidStrings() {
            val longInvalid = listOf(
                "0" + "1".repeat(1000),    // Starts with 0
                "1" + "0".repeat(1000),    // Ends with 0
                "1" + "2".repeat(100) + "1" // Contains invalid digit
            )
            longInvalid.forEach { binary ->
                assertFalse(detector.isValid(binary), 
                    "Long invalid binary should fail")
            }
        }
    }
    
    @Nested
    @DisplayName("State Machine Verification")
    inner class StateMachineTests {
        
        @Test
        @DisplayName("AwaitingStart1State transitions")
        fun testAwaitingStart1StateTransitions() {
            // Test first character handling
            assertTrue(detector.isValid("1"), "Should accept '1' as first char")
            assertFalse(detector.isValid("0"), "Should reject '0' as first char")
            assertFalse(detector.isValid("a"), "Should reject letter as first char")
            assertFalse(detector.isValid("!"), "Should reject special char as first char")
        }
        
        @Test
        @DisplayName("ValidBinaryState transitions")
        fun testValidBinaryStateTransitions() {
            // From ValidBinaryState: '1' stays valid, '0' goes to LookingForOne
            assertTrue(detector.isValid("11"), "'1' after '1' should stay in valid state")
            assertFalse(detector.isValid("10"), "'0' after '1' should go to LookingForOne (invalid end)")
            assertTrue(detector.isValid("101"), "'1' after '10' should return to valid state")
            assertFalse(detector.isValid("1a"), "Invalid char should go to Invalid state")
        }
        
        @Test
        @DisplayName("LookingForOneState transitions")
        fun testLookingForOneStateTransitions() {
            // Test state where we need a '1' to become valid again
            assertTrue(detector.isValid("1001"), "Should handle 100->1 transition")
            assertTrue(detector.isValid("10001"), "Should handle 1000->1 transition")
            assertFalse(detector.isValid("1000"), "Should reject when ending in LookingForOne state")
            assertFalse(detector.isValid("100a1"), "Invalid char should transition to Invalid")
        }
        
        @Test
        @DisplayName("InvalidState behavior")
        fun testInvalidStateBehavior() {
            // Once in Invalid state, should stay invalid
            assertFalse(detector.isValid("1a1"), "Should stay invalid after hitting invalid char")
            assertFalse(detector.isValid("a111"), "Should stay invalid from start")
            assertFalse(detector.isValid("121"), "Should stay invalid after invalid digit")
        }
        
        
        @Test
        @DisplayName("Complete state machine flows")
        fun testCompleteStateMachineFlows() {
            // Test specific state transitions with detailed flows
            val flowTests = mapOf(
                "1" to true,        // AwaitingStart1 -> ValidBinary (end)
                "11" to true,       // AwaitingStart1 -> ValidBinary -> ValidBinary (end)
                "10" to false,      // AwaitingStart1 -> ValidBinary -> LookingForOne (end invalid)
                "101" to true,      // AwaitingStart1 -> ValidBinary -> LookingForOne -> ValidBinary (end)
                "1001" to true,     // ... -> LookingForOne -> LookingForOne -> ValidBinary (end)
                "10101" to true,    // Complex valid pattern
                "01" to false,      // AwaitingStart1 -> Invalid -> Invalid (end)
                "1a" to false,      // AwaitingStart1 -> ValidBinary -> Invalid (end)
                "" to false         // Empty string
            )
            
            flowTests.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "State flow for '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("End state validation")
        fun testEndStateValidation() {
            // Only ValidBinaryState should be considered valid at the end
            assertTrue(detector.isValid("1"), "Should end in ValidBinaryState")
            assertTrue(detector.isValid("111"), "Should end in ValidBinaryState")
            assertTrue(detector.isValid("101"), "Should end in ValidBinaryState")
            
            // These end in LookingForOneState (invalid)
            assertFalse(detector.isValid("10"), "Should end in LookingForOneState (invalid)")
            assertFalse(detector.isValid("1100"), "Should end in LookingForOneState (invalid)")
            
            // These end in InvalidState
            assertFalse(detector.isValid("1a"), "Should end in InvalidState")
            assertFalse(detector.isValid("12"), "Should end in InvalidState")
        }
    }
    
    @Nested
    @DisplayName("Comprehensive Integration Tests")
    inner class IntegrationTests {
        
        @Test
        @DisplayName("Complex valid patterns")
        fun testComplexValidPatterns() {
            val complexValid = listOf(
                "1010101",           // Long alternating
                "1100110011",        // Pattern groups
                "111000111",         // Grouped ones and zeros
                "10011001101"        // Mixed pattern
            )
            complexValid.forEach { binary ->
                assertTrue(detector.isValid(binary), 
                    "Complex valid pattern '$binary' should pass")
            }
        }
        
        @Test
        @DisplayName("All requirements combined verification")
        fun testAllRequirementsCombined() {
            // Test that all three requirements work together:
            // 1. Must start with 1
            // 2. Must end with 1  
            // 3. Only contain 0 and 1
            
            val perfectlyValid = listOf("1", "11", "101", "111", "1001", "1011")
            perfectlyValid.forEach { binary ->
                assertTrue(detector.isValid(binary), 
                    "Perfect binary '$binary' should be valid")
            }
            
            // Each violates exactly one requirement
            assertFalse(detector.isValid("01"), "Violates start requirement")
            assertFalse(detector.isValid("10"), "Violates end requirement")
            assertFalse(detector.isValid("1a1"), "Violates character requirement")
        }
    }
}