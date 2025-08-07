import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

class ComplexPasswordDetectorTest {
    
    private val detector = ComplexPasswordDetector()
    
    @Nested
    @DisplayName("Valid Complex Password Tests")
    inner class ValidComplexPasswordTests {
        
        @Test
        @DisplayName("Valid examples from requirements should pass")
        fun testRequirementExamples() {
            val validExamples = listOf(
                "aaaaH!aa",
                "1234567*9J",
                "asdpoihj;loikjasdf;ijp;lij2309jasd;lfkm20ij@aH"
            )
            validExamples.forEach { password ->
                assertTrue(detector.isValid(password), "Valid example '$password' should pass")
            }
        }
        
        @Test
        @DisplayName("Minimal valid passwords (exactly 8 chars)")
        fun testMinimalValidPasswords() {
            val minimalValid = listOf(
                "aaaaH!aa",        // Capital then special
                "aaaa!Haa",        // Special then capital
                "A!aaaaaa",        // Capital and special at start
                "aaaaaA!a",        // Capital and special near end
                "A123456!",        // Would end with special but last char is not
                "A1234567",        // Wait, this has no special char - fix below
                "A123456*a",       // Capital, special, ends with letter
                "1234567Ab",       // Special missing - fix below
                "12345A!b",        // Has all requirements
                "!A123456",        // Special and capital at start
                "12345!Az"         // Everything at the end area
            )
            // Let me fix the list to ensure all have capital, special, ≥8 chars, don't end with special
            val correctedMinimalValid = listOf(
                "aaaaH!aa",        // 8 chars, has H, has !, doesn't end with special
                "aaaa!Haa",        // 8 chars, has H, has !, doesn't end with special  
                "A!aaaaaa",        // 8 chars, has A, has !, doesn't end with special
                "aaaaaA!a",        // 8 chars, has A, has !, doesn't end with special
                "A123456b!a",      // 9 chars, has A, has !, doesn't end with special
                "12345A!b",        // 8 chars, has A, has !, doesn't end with special
                "!A123456",        // 8 chars, has A, has !, doesn't end with special
                "12345!Az",        // 8 chars, has A, has !, doesn't end with special
                "B*cccccc",        // 8 chars, has B, has *, doesn't end with special
                "ccccccB*c"        // 9 chars, has B, has *, doesn't end with special
            )
            correctedMinimalValid.forEach { password ->
                assertTrue(detector.isValid(password), "Minimal valid password '$password' should pass")
            }
        }
        
        @Test
        @DisplayName("Passwords with different capital letter positions")
        fun testCapitalLetterPositions() {
            val capitalPositions = listOf(
                "Aaaaaaaa!b",       // Capital at start
                "aAaaaaaa!b",       // Capital at position 2
                "aaAaaaaa!b",       // Capital at position 3
                "aaaAaaaa!b",       // Capital in middle
                "aaaaaAaa!b",       // Capital near end
                "aaaaaaaA!b",       // Capital just before special
                "aaaaaaaa!B",       // Capital at end
                "AaAaAaAa!b",       // Multiple capitals
                "aaaZZZaa!b",       // Multiple consecutive capitals
                "ABC12345!d"        // Multiple capitals at start
            )
            capitalPositions.forEach { password ->
                assertTrue(detector.isValid(password), "Password with capital '$password' should be valid")
            }
        }
        
        @Test
        @DisplayName("Passwords with different special character positions")
        fun testSpecialCharacterPositions() {
            val specialPositions = listOf(
                "!aaaaaaAb",        // Special at start
                "a!aaaaaAb",        // Special at position 2
                "aa!aaaaAb",        // Special at position 3
                "aaa!aaaAb",        // Special in middle
                "aaaaa!aAb",        // Special near end
                "aaaaaaa!Ab",       // Special just before capital
                "aaaaaaAa!b",       // Special just before last
                "!!aaaAAab",        // Multiple specials
                "aaa!!AAab",        // Multiple consecutive specials
                "@#$12A45b"         // Multiple specials at start
            )
            specialPositions.forEach { password ->
                assertTrue(detector.isValid(password), "Password with special '$password' should be valid")
            }
        }
        
        @Test
        @DisplayName("Passwords with all different special characters")
        fun testAllSpecialCharacters() {
            val allSpecialChars = listOf(
                "aaaaaaA!b",        // !
                "aaaaaaA@b",        // @
                "aaaaaaA#b",        // #
                "aaaaaaA\$b",       // $ (escaped)
                "aaaaaaA%b",        // %
                "aaaaaaA&b",        // &
                "aaaaaaA*b",        // *
                "A!@#\$%&*b",       // All special chars (escaped $)
                "mixed!A@b",        // Multiple different specials
                "test@Apassword"    // Common @ symbol
            )
            allSpecialChars.forEach { password ->
                assertTrue(detector.isValid(password), "Password with special char '$password' should be valid")
            }
        }
        
        @Test
        @DisplayName("Long valid passwords")
        fun testLongValidPasswords() {
            val longPasswords = listOf(
                "A!" + "a".repeat(100),                                // Very long with requirements at start
                "a".repeat(50) + "A!" + "a".repeat(50),                // Requirements in middle
                "a".repeat(98) + "A!",                                 // Requirements at end but doesn't end with special
                "a".repeat(97) + "A!b",                                // Requirements near end
                "A".repeat(50) + "!" + "a".repeat(50),                 // Many capitals
                "!" + "A".repeat(50) + "a".repeat(50),                 // Many capitals after special
                "asdpoihj;loikjasdf;ijp;lij2309jasd;lfkm20ij@aH",     // From requirements (very long)
                "ThisIsAVeryLongPasswordWith!ManyCharactersAndNumbers123" // Realistic long password
            )
            longPasswords.forEach { password ->
                assertTrue(detector.isValid(password), "Long password '$password' should be valid")
            }
        }
        
        @Test
        @DisplayName("Complex valid patterns")
        fun testComplexValidPatterns() {
            val complexPatterns = listOf(
                "Password123!A",      // Mixed case, numbers, special
                "MyP@ssw0rdIsG00d",   // Realistic password pattern
                "SuperSecret#123A",   // Another realistic pattern
                "Admin!User2024A",    // Admin-style password
                "Test*Case123Z",      // Test pattern
                "Abc123!def456",      // Mixed requirements
                "!@#$%&*ABCDEFGa",   // All specials, multiple capitals
                "aB!aB!aB!aB!abc",   // Repeating pattern
                "123456789!Aa",      // Numbers with requirements
                "!A" + "x".repeat(100) + "y" // Long with minimal requirements
            )
            complexPatterns.forEach { password ->
                assertTrue(detector.isValid(password), "Complex pattern '$password' should be valid")
            }
        }
    }
    
    @Nested
    @DisplayName("Invalid Complex Password Tests")
    inner class InvalidComplexPasswordTests {
        
        @Test
        @DisplayName("Invalid examples from requirements should fail")
        fun testRequirementInvalidExamples() {
            val invalidExamples = listOf(
                "a",                // basically missing everything and too short
                "aaaaaaa!",         // no capital letter and ends with special char
                "aaaHaaaaa",        // no special char
                "Abbbbbbb!"         // ends with special char
            )
            invalidExamples.forEach { password ->
                assertFalse(detector.isValid(password), "Invalid example '$password' should fail")
            }
        }
        
        @Test
        @DisplayName("Empty string and very short passwords should be invalid")
        fun testEmptyAndShortPasswords() {
            val emptyAndShort = listOf(
                "",                 // Empty string
                "a",                // 1 char
                "ab",               // 2 chars
                "abc",              // 3 chars
                "abcd",             // 4 chars
                "abcde",            // 5 chars
                "abcdef",           // 6 chars
                "abcdefg",          // 7 chars (just under minimum)
                "A!",               // 2 chars with all requirements
                "A!a",              // 3 chars with all requirements
                "A!abcd",           // 6 chars with all requirements
                "A!abcde"           // 7 chars with all requirements
            )
            emptyAndShort.forEach { password ->
                assertFalse(detector.isValid(password), "Short password '$password' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Passwords missing capital letters should be invalid")
        fun testMissingCapitalLetters() {
            val missingCapital = listOf(
                "aaaaaaaa!b",       // 9 chars, has special, no capital
                "12345678!b",       // Numbers, special, no capital
                "abcdefgh!i",       // Long enough, special, no capital
                "!@#$%&*abc",       // All specials, no capital
                "test123!password", // Realistic but no capital
                "mypassword!123",   // Common pattern, no capital
                "hello@world123",   // @ special, no capital
                "password#12345",   // Hash special, no capital
                "user*name*12345",  // Multiple specials, no capital
                "a".repeat(100) + "!b" // Very long, special, no capital
            )
            missingCapital.forEach { password ->
                assertFalse(detector.isValid(password), "Password missing capital '$password' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Passwords missing special characters should be invalid")
        fun testMissingSpecialCharacters() {
            val missingSpecial = listOf(
                "aaaaaaaaAb",       // 10 chars, has capital, no special
                "12345678Ab",       // Numbers, capital, no special
                "abcdefghAi",       // Long enough, capital, no special
                "ABCDEFGHijk",      // Multiple capitals, no special
                "TestPassword123",  // Realistic but no special
                "MyPassword2024",   // Common pattern, no special
                "HelloWorldABC",    // All letters, capitals, no special
                "UserNameABC123",   // Mixed, capitals, no special
                "AdminUserABC123",  // Admin style, no special
                "A" + "a".repeat(100) + "b" // Very long, capital, no special
            )
            missingSpecial.forEach { password ->
                assertFalse(detector.isValid(password), "Password missing special '$password' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Passwords missing both capital and special should be invalid")
        fun testMissingBothCapitalAndSpecial() {
            val missingBoth = listOf(
                "aaaaaaaa",         // 8 chars, lowercase only
                "12345678",         // 8 chars, numbers only
                "abcdefgh",         // 8 chars, lowercase letters
                "testpassword",     // Long lowercase
                "usernamelong",     // Another long lowercase
                "passwordhere",     // Common pattern, lowercase
                "myaccountname",    // Account style, lowercase
                "a".repeat(100),    // Very long lowercase
                "123456789012345",  // Very long numbers
                "abcdefghijklmnop"  // Long lowercase letters
            )
            missingBoth.forEach { password ->
                assertFalse(detector.isValid(password), "Password missing both '$password' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Passwords ending with special characters should be invalid")
        fun testEndingWithSpecialCharacters() {
            val endingWithSpecial = listOf(
                "aaaaaaAa!",        // 9 chars, has capital, ends with !
                "aaaaaaAa@",        // 9 chars, has capital, ends with @
                "aaaaaaAa#",        // 9 chars, has capital, ends with #
                "aaaaaaAa\$",       // 9 chars, has capital, ends with $ (escaped)
                "aaaaaaAa%",        // 9 chars, has capital, ends with %
                "aaaaaaAa&",        // 9 chars, has capital, ends with &
                "aaaaaaAa*",        // 9 chars, has capital, ends with *
                "Password123!",     // Realistic ending with !
                "MyPassword@",      // Realistic ending with @
                "TestCase#",        // Realistic ending with #
                "AdminUser*",       // Realistic ending with *
                "A!" + "a".repeat(97) + "!", // Very long ending with special
                "SuperLongPasswordWithManyCharsA!@#$%&*" // Multiple specials at end
            )
            endingWithSpecial.forEach { password ->
                assertFalse(detector.isValid(password), "Password ending with special '$password' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Passwords with only one requirement met should be invalid")
        fun testOnlyOneRequirementMet() {
            // Only has capital (missing special, length may vary)
            val onlyCapital = listOf(
                "Aaaaaaa",          // 7 chars, capital, no special
                "Aaaaaaaa",         // 8 chars, capital, no special
                "ABCDEFGHabc",      // Multiple capitals, no special
                "TestPassword"      // Realistic, capital, no special
            )
            onlyCapital.forEach { password ->
                assertFalse(detector.isValid(password), "Only capital '$password' should be invalid")
            }
            
            // Only has special (missing capital, length may vary)
            val onlySpecial = listOf(
                "aaaaaaa!",         // 8 chars, special, no capital
                "!aaaaaaa",         // 8 chars, special at start, no capital
                "test!password",    // Realistic, special, no capital
                "hello@world"       // Realistic with @, no capital
            )
            onlySpecial.forEach { password ->
                assertFalse(detector.isValid(password), "Only special '$password' should be invalid")
            }
            
            // Only has length (missing capital and special)
            val onlyLength = listOf(
                "abcdefgh",         // 8 chars, no capital, no special
                "12345678",         // 8 chars numbers, no capital, no special
                "verylongpassword", // Long, no capital, no special
                "testaccountname"   // Long realistic, no capital, no special
            )
            onlyLength.forEach { password ->
                assertFalse(detector.isValid(password), "Only length '$password' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Passwords with two requirements but missing one should be invalid")
        fun testTwoRequirementsMet() {
            // Has capital and special but too short
            val capitalSpecialShort = listOf(
                "A!",               // 2 chars
                "A!a",              // 3 chars
                "A!abc",            // 5 chars
                "Test!",            // 6 chars
                "Pass@",            // 6 chars
                "A!abcde"           // 7 chars (just under)
            )
            capitalSpecialShort.forEach { password ->
                assertFalse(detector.isValid(password), "Capital+Special but short '$password' should be invalid")
            }
            
            // Has capital and length but no special
            val capitalLengthNoSpecial = listOf(
                "Aaaaaaaa",         // 8 chars, capital, no special
                "TestPassword",     // Long, capital, no special
                "MyAccountName",    // Long, capital, no special
                "AdminUserABC123"   // Long, capital, no special
            )
            capitalLengthNoSpecial.forEach { password ->
                assertFalse(detector.isValid(password), "Capital+Length but no special '$password' should be invalid")
            }
            
            // Has special and length but no capital
            val specialLengthNoCapital = listOf(
                "aaaaaaaa!",        // 8 chars, special, no capital - but ends with special too!
                "test!password",    // Long, special, no capital  
                "myaccount@name",   // Long, special, no capital
                "user#password123"  // Long, special, no capital
            )
            specialLengthNoCapital.forEach { password ->
                assertFalse(detector.isValid(password), "Special+Length but no capital '$password' should be invalid")
            }
            
            // Has capital and length but ends with special
            val capitalLengthEndsSpecial = listOf(
                "Aaaaaaaa!",        // 9 chars, capital, ends with special
                "TestPassword!",    // Long, capital, ends with special
                "MyPassword@",      // Long, capital, ends with special
                "AdminUser#"        // Long, capital, ends with special
            )
            capitalLengthEndsSpecial.forEach { password ->
                assertFalse(detector.isValid(password), "Capital+Length but ends special '$password' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Edge cases and boundary conditions")
        fun testEdgeCases() {
            val edgeCases = listOf(
                "A!abcdef",         // Exactly 8 chars but ends with special? No, ends with 'f'
                "A!abcde!",         // 8 chars, has both, ends with special - invalid
                "!Aabcdef",         // 8 chars, has both, doesn't end with special - should be valid
                "abcdefA!",         // 8 chars, has both, ends with special - invalid
                "1234567A",         // 8 chars, has capital, no special - invalid
                "1234567!",         // 8 chars, has special, no capital, ends with special - invalid
                "A1234567",         // 8 chars, has capital, no special - invalid
                "!1234567",         // 8 chars, has special, no capital - invalid
            )
            
            // Let me manually verify each case
            assertFalse(detector.isValid("A!abcde!"), "Should be invalid: ends with special")
            assertTrue(detector.isValid("!Aabcdef"), "Should be valid: has all requirements")
            assertFalse(detector.isValid("abcdefA!"), "Should be invalid: ends with special")
            assertFalse(detector.isValid("1234567A"), "Should be invalid: no special char")
            assertFalse(detector.isValid("1234567!"), "Should be invalid: no capital and ends with special")
            assertFalse(detector.isValid("A1234567"), "Should be invalid: no special char")
            assertFalse(detector.isValid("!1234567"), "Should be invalid: no capital")
        }
    }
    
    @Nested
    @DisplayName("State Machine Verification")
    inner class StateMachineTests {
        
        @Test
        @DisplayName("Verify AwaitingCharacter state transitions")
        fun testAwaitingCharacterTransitions() {
            val testCases = mapOf(
                "AAAAaaaa!b" to true,       // Capital first -> HasCapital -> HasSpecialAndCapital
                "!!!!AAAAb" to true,        // Special first -> HasSpecial -> HasSpecialAndCapital  
                "aaaAAAA!b" to true,        // Other chars first, then capital, then special
                "aaa!AAA" to false,         // Valid path but ends with special (A) - wait that's not special
                "aaa!AAAb" to true,         // Valid path, doesn't end with special
                "aaaaaaaa" to false         // Never leaves AwaitingCharacter
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "AwaitingCharacter test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify HasSpecialCharacter state transitions")
        fun testHasSpecialCharacterTransitions() {
            // Passwords that go through HasSpecialCharacter state
            val testCases = mapOf(
                "!aaaAAAAb" to true,        // Special -> HasSpecial -> capital -> HasSpecialAndCapital
                "!aaaaaaaA" to true,        // Special -> HasSpecial -> stays -> capital -> HasSpecialAndCapital
                "!aaaaaaaa" to false,       // Special -> HasSpecial -> stays -> never gets capital
                "!AAaaaaab" to true,        // Special -> HasSpecial -> capital immediately
                "!aaaaAAA" to false         // Has special and capital but ends with capital? Wait, that should be valid
            )
            
            // Fix the test - ending with capital letter is fine
            assertFalse(detector.isValid("!aaaaaaaa"), "Should be invalid: no capital")
            assertTrue(detector.isValid("!AAaaaaab"), "Should be valid: has both requirements")
            assertTrue(detector.isValid("!aaaaAAAA"), "Should be valid: has both requirements, ends with capital letter")
        }
        
        @Test
        @DisplayName("Verify HasCapital state transitions") 
        fun testHasCapitalTransitions() {
            // Passwords that go through HasCapital state
            val testCases = mapOf(
                "AaaaAAAA!b" to true,       // Capital -> HasCapital -> special -> HasSpecialAndCapital
                "AaaaaaAA!" to false,       // Capital -> HasCapital -> special but ends with special
                "Aaaaaaaaa" to false,       // Capital -> HasCapital -> stays -> never gets special
                "A!!aaaaab" to true,        // Capital -> HasCapital -> special immediately
                "A!aaAAAAb" to true         // Capital -> HasCapital -> special -> HasSpecialAndCapital
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "HasCapital test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify HasSpecialAndCapital state transitions")
        fun testHasSpecialAndCapitalTransitions() {
            // Once in HasSpecialAndCapital, only length and end char matter
            val testCases = mapOf(
                "A!aaaaaa" to true,         // 8 chars, has both, doesn't end with special
                "A!aaaaa" to false,         // 7 chars, has both, doesn't end with special
                "A!aaaaa!" to false,        // 8 chars, has both, ends with special
                "A!aaaaaab" to true,        // 9 chars, has both, doesn't end with special
                "!Aaaaaaa" to true,         // 8 chars, has both, doesn't end with special
                "!Aaaaaa!" to false         // 8 chars, has both, ends with special
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "HasSpecialAndCapital test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Complete state machine path verification")
        fun testCompleteStatePaths() {
            val pathTests = listOf(
                Triple("A!aaaaaa", true, "AwaitingChar->HasCapital->HasSpecialAndCapital"),
                Triple("!Aaaaaaa", true, "AwaitingChar->HasSpecial->HasSpecialAndCapital"),
                Triple("aA!aaaaa", true, "AwaitingChar->AwaitingChar->HasCapital->HasSpecialAndCapital"),
                Triple("a!Aaaaaa", true, "AwaitingChar->AwaitingChar->HasSpecial->HasSpecialAndCapital"),
                Triple("aaaA!aaa", true, "AwaitingChar(4x)->HasCapital->HasSpecialAndCapital"),
                Triple("aaa!Aaaa", true, "AwaitingChar(4x)->HasSpecial->HasSpecialAndCapital"),
                Triple("aaaaaaaa", false, "AwaitingChar(8x) - never gets capital or special"),
                Triple("AAAAaaaa", false, "AwaitingChar->HasCapital(7x) - never gets special"),
                Triple("!!!!aaaa", false, "AwaitingChar->HasSpecial(7x) - never gets capital"),
                Triple("A!aaaaa!", false, "Gets to HasSpecialAndCapital but ends with special"),
                Triple("A!aaaaa", false, "Gets to HasSpecialAndCapital but only 7 chars")
            )
            
            pathTests.forEach { (input, expected, description) ->
                assertEquals(expected, detector.isValid(input), 
                    "Path test '$input' ($description) should be $expected")
            }
        }
    }
    
    @Nested
    @DisplayName("Requirements Verification")
    inner class RequirementsTests {
        
        @Test
        @DisplayName("At least 1 capital letter requirement")
        fun testCapitalLetterRequirement() {
            // Valid: has capital letters
            val withCapitals = listOf(
                "A!aaaaaa", "aA!aaaaa", "aaA!aaaa", "aaaA!aaa", "aaaaA!aa", "aaaaaA!a",
                "Multiple!CAPS", "ManyCapitals!Here", "ABC!defgh"
            )
            withCapitals.forEach { password ->
                assertTrue(password.any { it.isUpperCase() }, "Test setup: '$password' should have capitals")
                assertTrue(detector.isValid(password), "Password with capital '$password' should be valid")
            }
            
            // Invalid: no capital letters
            val withoutCapitals = listOf(
                "!aaaaaaaa", "aa!aaaaaa", "aaaa!aaaa", "test!password", 
                "hello@world123", "mypassword#456"
            )
            withoutCapitals.forEach { password ->
                assertFalse(password.any { it.isUpperCase() }, "Test setup: '$password' should have no capitals")
                assertFalse(detector.isValid(password), "Password without capital '$password' should be invalid")
            }
        }
        
        @Test
        @DisplayName("At least 1 special character requirement")
        fun testSpecialCharacterRequirement() {
            val specialChars = "!@#\$%&*"
            
            // Valid: has special characters
            val withSpecials = listOf(
                "A!aaaaaa", "A@aaaaaa", "A#aaaaaa", "A\$aaaaaa", 
                "A%aaaaaa", "A&aaaaaa", "A*aaaaaa", "Multiple@Special!Chars"
            )
            withSpecials.forEach { password ->
                assertTrue(password.any { it in specialChars }, "Test setup: '$password' should have specials")
                assertTrue(detector.isValid(password), "Password with special '$password' should be valid")
            }
            
            // Invalid: no special characters
            val withoutSpecials = listOf(
                "Aaaaaaaa", "TestPassword", "MyAccount123", "AdminUserABC",
                "HelloWorld", "UserName123ABC"
            )
            withoutSpecials.forEach { password ->
                assertFalse(password.any { it in specialChars }, "Test setup: '$password' should have no specials")
                assertFalse(detector.isValid(password), "Password without special '$password' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Cannot end with special character requirement")
        fun testCannotEndWithSpecialRequirement() {
            val specialChars = "!@#\$%&*"
            
            // Valid: doesn't end with special character
            val notEndingSpecial = listOf(
                "A!aaaaaa", "A!aaaaaab", "Password!123A", "Test@CaseA",
                "Admin#UserA", "Multiple!@#A", "A!bcdefgh"
            )
            notEndingSpecial.forEach { password ->
                assertFalse(password.last() in specialChars, "Test setup: '$password' should not end with special")
                assertTrue(detector.isValid(password), "Password not ending with special '$password' should be valid")
            }
            
            // Invalid: ends with special character
            val endingSpecial = listOf(
                "AAAAaaaa!", "AAAAaaaa@", "AAAAaaaa#", "AAAAaaaa\$",
                "AAAAaaaa%", "AAAAaaaa&", "AAAAaaaa*", "Password123!",
                "TestCase@", "AdminUser#"
            )
            endingSpecial.forEach { password ->
                assertTrue(password.last() in specialChars, "Test setup: '$password' should end with special")
                assertFalse(detector.isValid(password), "Password ending with special '$password' should be invalid")
            }
        }
        
        @Test
        @DisplayName("At least 8 characters requirement")
        fun testLengthRequirement() {
            // Valid: at least 8 characters  
            val longEnough = listOf(
                "A!aaaaaa",             // Exactly 8
                "A!aaaaaaaa",           // 9 chars
                "A!aaaaaaaaaa",         // 11 chars
                "VeryLongPassword!A",   // Much longer
                "A!" + "a".repeat(100)  // Very long
            )
            longEnough.forEach { password ->
                assertTrue(password.length >= 8, "Test setup: '$password' should be ≥8 chars")
                assertTrue(detector.isValid(password), "Long enough password '$password' should be valid")
            }
            
            // Invalid: less than 8 characters
            val tooShort = listOf(
                "A!",       // 2 chars
                "A!a",      // 3 chars
                "A!aa",     // 4 chars
                "A!aaa",    // 5 chars
                "A!aaaa",   // 6 chars
                "A!aaaaa"   // 7 chars
            )
            tooShort.forEach { password ->
                assertTrue(password.length < 8, "Test setup: '$password' should be <8 chars")
                assertFalse(detector.isValid(password), "Too short password '$password' should be invalid")
            }
        }
        
        @Test
        @DisplayName("All requirements together")
        fun testAllRequirementsTogether() {
            // Valid: meets all requirements
            val meetsAll = listOf(
                "A!aaaaaa",                 // Minimal valid
                "Password123!A",            // Realistic with all requirements
                "Complex@Pass1",            // Another realistic example
                "Admin#User2024A",          // Admin style
                "Super!Secret@123A",        // Multiple specials
                "A!" + "a".repeat(100)      // Very long with requirements
            )
            meetsAll.forEach { password ->
                assertTrue(password.any { it.isUpperCase() }, "Test setup: should have capital")
                assertTrue(password.any { it in "!@#\$%&*" }, "Test setup: should have special")
                assertTrue(password.length >= 8, "Test setup: should be ≥8 chars")
                assertFalse(password.last() in "!@#\$%&*", "Test setup: should not end with special")
                assertTrue(detector.isValid(password), "All requirements met '$password' should be valid")
            }
            
            // Invalid: fails at least one requirement
            val failsOne = listOf(
                "a!aaaaaa",             // No capital
                "Aaaaaaaa",             // No special
                "A!aaaaa",              // Too short (7 chars)
                "Aaaaaaaa!",            // Ends with special
                "aaaaaaaa",             // No capital, no special
                "A!",                   // Too short, multiple issues
                "shortA!"               // Still too short
            )
            failsOne.forEach { password ->
                assertFalse(detector.isValid(password), "Fails requirement '$password' should be invalid")
            }
        }
    }
}