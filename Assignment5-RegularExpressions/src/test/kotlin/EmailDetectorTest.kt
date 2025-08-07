import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

class EmailDetectorTest {
    
    private val detector = EmailDetector()
    
    @Nested
    @DisplayName("Valid Email Tests")
    inner class ValidEmailTests {
        
        @Test
        @DisplayName("Valid examples from requirements should pass")
        fun testRequirementExamples() {
            val validExamples = listOf(
                "a@b.c",
                "joseph.ditton@usu.edu",
                "{}*$.&$*(@*$%&.*&*"
            )
            validExamples.forEach { email ->
                assertTrue(detector.isValid(email), "Valid example '$email' should pass")
            }
        }
        
        @Test
        @DisplayName("Simple valid email patterns")
        fun testSimpleValidEmails() {
            val simpleValid = listOf(
                "a@b.c",           // Minimal valid email
                "x@y.z",           // Single chars
                "1@2.3",           // Numbers
                "test@test.com",   // Common pattern
                "user@domain.org", // Standard format
                "admin@site.net"   // Another standard format
            )
            simpleValid.forEach { email ->
                assertTrue(detector.isValid(email), "Simple valid email '$email' should pass")
            }
        }
        
        @Test
        @DisplayName("Emails with complex part1 (before @)")
        fun testComplexPart1() {
            val complexPart1 = listOf(
                "user.name@domain.com",           // Period in part1
                "user_name@domain.com",           // Underscore in part1
                "user-name@domain.com",           // Dash in part1
                "user123@domain.com",             // Numbers in part1
                "123user@domain.com",             // Numbers first in part1
                "user+tag@domain.com",            // Plus in part1
                "user=value@domain.com",          // Equals in part1
                "user!name@domain.com",           // Exclamation in part1
                "user#hash@domain.com",           // Hash in part1
                "user\$money@domain.com",          // Dollar in part1
                "user%percent@domain.com",        // Percent in part1
                "user&and@domain.com",            // Ampersand in part1
                "user*star@domain.com",           // Asterisk in part1
                "user(paren)@domain.com",         // Parentheses in part1
                "user[bracket]@domain.com",       // Brackets in part1
                "user{brace}@domain.com",         // Braces in part1
                "user|pipe@domain.com",           // Pipe in part1
                "user\\backslash@domain.com",     // Backslash in part1
                "user/slash@domain.com",          // Forward slash in part1
                "user?question@domain.com",       // Question mark in part1
                "user~tilde@domain.com",          // Tilde in part1
                "user`backtick@domain.com",       // Backtick in part1
                "user'^quotes@domain.com"         // Various quotes in part1
            )
            complexPart1.forEach { email ->
                assertTrue(detector.isValid(email), "Complex part1 email '$email' should be valid")
            }
        }
        
        @Test
        @DisplayName("Emails with complex part2 (between @ and .)")
        fun testComplexPart2() {
            val complexPart2 = listOf(
                "user@sub.domain.com",            // Subdomain-like in part2
                "user@domain-name.com",           // Dash in part2
                "user@domain_name.com",           // Underscore in part2
                "user@123domain.com",             // Numbers in part2
                "user@domain123.com",             // Numbers at end of part2
                "user@d0m41n.com",                // Mixed numbers/letters
                "user@!#$%^&*().com",             // Special chars in part2
                "user@very-long-domain-name.com", // Long part2
                "user@a.com",                     // Single char part2
                "user@X.org",                     // Capital letter part2
                "user@MixedCase.net"              // Mixed case part2
            )
            complexPart2.forEach { email ->
                assertTrue(detector.isValid(email), "Complex part2 email '$email' should be valid")
            }
        }
        
        @Test
        @DisplayName("Emails with complex part3 (after .)")
        fun testComplexPart3() {
            val complexPart3 = listOf(
                "user@domain.com",                // Standard TLD
                "user@domain.org",                // Org TLD
                "user@domain.edu",                // Edu TLD
                "user@domain.net",                // Net TLD
                "user@domain.gov",                // Gov TLD
                "user@domain.co",                 // Short TLD
                "user@domain.info",               // Long TLD
                "user@domain.a",                  // Single char TLD
                "user@domain.123",                // Number TLD
                "user@domain.x1y2z3",             // Mixed TLD
                "user@domain.COM",                // Uppercase TLD
                "user@domain.MixedCase",          // Mixed case TLD
                "user@domain.special!chars",      // Special chars in TLD
                "user@domain.under_score",        // Underscore in TLD
                "user@domain.dash-mark",          // Dash in TLD
                "user@domain.with+plus",          // Plus in TLD
                "user@domain.very-long-extension" // Very long TLD
            )
            complexPart3.forEach { email ->
                assertTrue(detector.isValid(email), "Complex part3 email '$email' should be valid")
            }
        }
        
        @Test
        @DisplayName("Very long valid emails")
        fun testVeryLongEmails() {
            val longEmails = listOf(
                "a".repeat(100) + "@" + "b".repeat(100) + "." + "c".repeat(100),
                "very.long.user.name.with.many.dots@very.long.domain.name.here.super.long.extension",
                "user123456789012345678901234567890@domain123456789012345678901234567890.extension123456789012345678901234567890",
                "!#$%&'*+-/=?^_`{|}~@!#$%&'*+-/=?^_`{|}~.!#$%&'*+-/=?^_`{|}~"
            )
            longEmails.forEach { email ->
                assertTrue(detector.isValid(email), "Long email '$email' should be valid")
            }
        }
        
        @Test
        @DisplayName("Edge case valid emails")
        fun testEdgeCaseValidEmails() {
            val edgeCases = listOf(
                "a@b.c",                          // Minimal valid
                "1@2.3",                          // All numbers
                "!@#.$",                          // Special chars only
                "Z@Y.X",                          // All capitals
                "test@test.test",                 // Repeated words
                "x.y.z@a.b.c.d"                   // Multiple dots in part1, single in part2/3
            )
            edgeCases.forEach { email ->
                assertTrue(detector.isValid(email), "Edge case email '$email' should be valid")
            }
        }
    }
    
    @Nested
    @DisplayName("Invalid Email Tests")
    inner class InvalidEmailTests {
        
        @Test
        @DisplayName("Invalid examples from requirements should fail")
        fun testRequirementInvalidExamples() {
            val invalidExamples = listOf(
                "@b.c",                     // part1 is empty
                "a@b@c.com",               // too many @ symbols
                "a.b@b.b.c",               // too many periods after @
                "joseph ditton@usu.edu"    // space char not allowed
            )
            invalidExamples.forEach { email ->
                assertFalse(detector.isValid(email), "Invalid example '$email' should fail")
            }
        }
        
        @Test
        @DisplayName("Empty string and whitespace should be invalid")
        fun testEmptyAndWhitespace() {
            val emptyAndWhitespace = listOf(
                "",                         // Empty string
                " ",                        // Single space
                "  ",                       // Multiple spaces
                "\t",                       // Tab
                "\n",                       // Newline
                "\r",                       // Carriage return
                " \t\n "                    // Mixed whitespace
            )
            emptyAndWhitespace.forEach { email ->
                assertFalse(detector.isValid(email), "Empty/whitespace '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Emails with empty part1 should be invalid")
        fun testEmptyPart1() {
            val emptyPart1 = listOf(
                "@domain.com",              // No part1
                "@.com",                    // No part1 or part2
                "@domain.",                 // No part1 or part3
                "@."                        // No part1, part2, or part3
            )
            emptyPart1.forEach { email ->
                assertFalse(detector.isValid(email), "Empty part1 '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Emails with empty part2 should be invalid")
        fun testEmptyPart2() {
            val emptyPart2 = listOf(
                "user@.com",                // No part2
                "test@.org",                // No part2
                "admin@.net",               // No part2
                "a@.b"                      // Minimal with no part2
            )
            emptyPart2.forEach { email ->
                assertFalse(detector.isValid(email), "Empty part2 '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Emails with empty part3 should be invalid")
        fun testEmptyPart3() {
            val emptyPart3 = listOf(
                "user@domain.",             // No part3
                "test@site.",               // No part3
                "admin@server.",            // No part3
                "a@b."                      // Minimal with no part3
            )
            emptyPart3.forEach { email ->
                assertFalse(detector.isValid(email), "Empty part3 '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Emails with no @ symbol should be invalid")
        fun testNoAtSymbol() {
            val noAt = listOf(
                "userdomain.com",           // No @ at all
                "user.domain.com",          // Periods but no @
                "user-domain.com",          // Dash but no @
                "user_domain.com",          // Underscore but no @
                "userdomain",               // No @ or period
                "a.b.c.d.e"                 // Multiple periods but no @
            )
            noAt.forEach { email ->
                assertFalse(detector.isValid(email), "No @ symbol '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Emails with multiple @ symbols should be invalid")
        fun testMultipleAtSymbols() {
            val multipleAt = listOf(
                "user@domain@.com",         // Two @ symbols
                "user@@domain.com",         // Consecutive @ symbols
                "@user@domain.com",         // @ at start and middle
                "user@domain.com@",         // @ at middle and end
                "@user@domain@.com",        // Three @ symbols
                "@@@@",                     // Only @ symbols
                "a@b@c@d.e"                 // Multiple @ throughout
            )
            multipleAt.forEach { email ->
                assertFalse(detector.isValid(email), "Multiple @ symbols '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Emails with no period should be invalid")
        fun testNoPeriod() {
            val noPeriod = listOf(
                "user@domain",              // No period at all
                "test@site",                // No period
                "admin@server",             // No period
                "a@b"                       // Minimal with no period
            )
            noPeriod.forEach { email ->
                assertFalse(detector.isValid(email), "No period '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Emails with multiple periods after @ should be invalid")
        fun testMultiplePeriodsAfterAt() {
            val multiplePeriods = listOf(
                "user@domain.com.org",      // Two periods after @
                "user@site.co.uk",          // Two periods after @
                "test@domain..com",         // Consecutive periods
                "admin@server...net",       // Multiple consecutive periods
                "user@a.b.c.d.e",           // Many periods after @
                "test@domain..",            // Periods at end
                "user@.domain.com"          // Period right after @
            )
            multiplePeriods.forEach { email ->
                assertFalse(detector.isValid(email), "Multiple periods after @ '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Emails with spaces should be invalid")
        fun testEmailsWithSpaces() {
            val withSpaces = listOf(
                " user@domain.com",         // Leading space
                "user@domain.com ",         // Trailing space
                " user@domain.com ",        // Surrounded by spaces
                "user @domain.com",         // Space in part1
                "user@ domain.com",         // Space in part2
                "user@domain .com",         // Space in part2
                "user@domain. com",         // Space in part3
                "user@domain.com ",         // Space in part3
                "us er@domain.com",         // Space in middle of part1
                "user@dom ain.com",         // Space in middle of part2
                "user@domain.c om",         // Space in middle of part3
                "user @ domain . com",      // Spaces everywhere
                "user\t@domain.com",        // Tab character
                "user@domain\n.com",        // Newline character
                "user@domain.com\r"         // Carriage return
            )
            withSpaces.forEach { email ->
                assertFalse(detector.isValid(email), "Email with spaces '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Malformed email structures should be invalid")
        fun testMalformedStructures() {
            val malformed = listOf(
                ".user@domain.com",         // Starting with period
                "user.@domain.com",         // Ending part1 with period
                "user@.domain.com",         // Starting part2 with period
                "user@domain..com",         // Double period in part2
                "user@domain.com.",         // Ending with period
                "@",                        // Only @ symbol
                ".",                        // Only period
                "@.",                       // Only @ and period
                ".@",                       // Period then @
                ".@.",                      // Period @ period
                "@@",                       // Double @
                "..",                       // Double period
                "@.@",                      // @ period @
                ".@.@."                     // Complex malformed
            )
            malformed.forEach { email ->
                assertFalse(detector.isValid(email), "Malformed structure '$email' should be invalid")
            }
        }
    }
    
    @Nested
    @DisplayName("Edge Cases and Boundary Tests")
    inner class EdgeCasesAndBoundaryTests {
        
        @Test
        @DisplayName("Single character parts should be valid if properly formed")
        fun testSingleCharacterParts() {
            val singleCharParts = listOf(
                "a@b.c",                    // All single chars
                "1@2.3",                    // All single numbers
                "!@#.$",                    // All single special chars
                "A@B.C"                     // All single capitals
            )
            singleCharParts.forEach { email ->
                assertTrue(detector.isValid(email), "Single char parts '$email' should be valid")
            }
        }
        
        @Test
        @DisplayName("Very long parts should be valid")
        fun testVeryLongParts() {
            val veryLongParts = listOf(
                "a".repeat(1000) + "@b.c",                    // Very long part1
                "a@" + "b".repeat(1000) + ".c",               // Very long part2
                "a@b." + "c".repeat(1000),                    // Very long part3
                "x".repeat(500) + "@" + "y".repeat(500) + "." + "z".repeat(500)  // All parts long
            )
            veryLongParts.forEach { email ->
                assertTrue(detector.isValid(email), "Very long parts '$email' should be valid")
            }
        }
        
        @Test
        @DisplayName("Special character combinations should work correctly")
        fun testSpecialCharacterCombinations() {
            // Valid: Special chars in all parts
            val validSpecial = listOf(
                "!#$%&'*+-/=?^_`{|}~@!#$%&'*+-/=?^_`{|}~.!#$%&'*+-/=?^_`{|}~",
                "()[]{}@()[]{}.()()",
                "123!@#$%^&*()@test.456",
                "test_user-123@sub-domain_123.ext-123"
            )
            validSpecial.forEach { email ->
                assertTrue(detector.isValid(email), "Special char combo '$email' should be valid")
            }
            
            // Invalid: Space anywhere
            val invalidWithSpace = listOf(
                "test @domain.com",
                "test@ domain.com", 
                "test@domain .com",
                "test@domain. com"
            )
            invalidWithSpace.forEach { email ->
                assertFalse(detector.isValid(email), "Special char with space '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Unicode and international characters")
        fun testUnicodeCharacters() {
            // These should be valid since they're not space, @, or period in wrong places
            val unicodeEmails = listOf(
                "用户@域名.com",              // Chinese characters
                "usuário@domínio.com",       // Portuguese with accents
                "пользователь@домен.рф",     // Cyrillic characters
                "ユーザー@ドメイン.jp",        // Japanese characters
                "user@домен.рф",             // Mixed ASCII and Cyrillic
                "tëst@ëxämplë.cöm",          // Accented characters
                "user@xn--domain.com",       // Punycode-like
                "тест@test.орг"              // Mixed scripts
            )
            unicodeEmails.forEach { email ->
                assertTrue(detector.isValid(email), "Unicode email '$email' should be valid")
            }
        }
    }
    
    @Nested
    @DisplayName("State Machine Verification")
    inner class StateMachineTests {
        
        @Test
        @DisplayName("Verify AwaitingFirstCharacter state transitions")
        fun testAwaitingFirstCharacterTransitions() {
            val testCases = mapOf(
                "a@b.c" to true,            // Valid: starts with letter
                "1@b.c" to true,            // Valid: starts with number
                "!@b.c" to true,            // Valid: starts with special char
                "@b.c" to false,            // Invalid: starts with @
                " @b.c" to false            // Invalid: starts with space
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "AwaitingFirstCharacter test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify LookingForAt state transitions")
        fun testLookingForAtTransitions() {
            val testCases = mapOf(
                "abc@def.ghi" to true,      // Valid: chars then @
                "123@456.789" to true,      // Valid: numbers then @
                "a!b@c.d" to true,          // Valid: special chars then @
                "ab cd@ef.gh" to false,     // Invalid: space in part1
                "abcdefghi" to false        // Invalid: no @ symbol
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "LookingForAt test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify HasAt state transitions")
        fun testHasAtTransitions() {
            val testCases = mapOf(
                "a@b.c" to true,            // Valid: char after @
                "a@1.c" to true,            // Valid: number after @
                "a@!.c" to true,            // Valid: special char after @
                "a@ .c" to false,           // Invalid: space after @
                "a@.c" to false,            // Invalid: period right after @
                "a@@.c" to false            // Invalid: second @ right after first
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "HasAt test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify LookingForPeriod state transitions")
        fun testLookingForPeriodTransitions() {
            val testCases = mapOf(
                "a@bc.d" to true,           // Valid: chars then period
                "a@123.d" to true,          // Valid: numbers then period
                "a@b!c.d" to true,          // Valid: special chars then period
                "a@bc .d" to false,         // Invalid: space in part2
                "a@bc@d.e" to false,        // Invalid: second @ in part2
                "a@bc" to false             // Invalid: no period
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "LookingForPeriod test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Verify ValidEmail state transitions")
        fun testValidEmailTransitions() {
            val testCases = mapOf(
                "a@b.cd" to true,           // Valid: chars after period
                "a@b.123" to true,          // Valid: numbers after period
                "a@b.c!d" to true,          // Valid: special chars after period
                "a@b.c d" to false,         // Invalid: space in part3
                "a@b.c.d" to false,         // Invalid: second period in part3
                "a@b.c@d" to false,         // Invalid: @ in part3
                "a@b." to false             // Invalid: no part3
            )
            
            testCases.forEach { (input, expected) ->
                assertEquals(expected, detector.isValid(input), 
                    "ValidEmail test '$input' should be $expected")
            }
        }
        
        @Test
        @DisplayName("Complete state machine path verification")
        fun testCompleteStatePaths() {
            val pathTests = listOf(
                Triple("a@b.c", true, "AwaitingFirstChar->LookingForAt->HasAt->LookingForPeriod->ValidEmail"),
                Triple("abc@def.ghi", true, "Full path with multi-char parts"),
                Triple("@b.c", false, "AwaitingFirstChar->Invalid (starts with @)"),
                Triple("a b@c.d", false, "LookingForAt->Invalid (space in part1)"),
                Triple("a@.c", false, "HasAt->Invalid (period right after @)"),
                Triple("a@b c.d", false, "LookingForPeriod->Invalid (space in part2)"),
                Triple("a@b.c d", false, "ValidEmail->Invalid (space in part3)"),
                Triple("a@b.c.d", false, "ValidEmail->Invalid (second period)")
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
        @DisplayName("Format <part1>@<part2>.<part3> requirement")
        fun testEmailFormatRequirement() {
            // All valid emails must follow this format
            val validFormats = listOf("a@b.c", "test@domain.com", "user123@site456.org789")
            validFormats.forEach { email ->
                assertTrue(detector.isValid(email), "Valid format '$email' should pass")
                assertTrue(email.contains("@"), "Test setup: should contain @")
                assertTrue(email.indexOf(".") > email.indexOf("@"), "Test setup: period should be after @")
            }
            
            // Invalid formats
            val invalidFormats = listOf("abc", "a@b", "a.b", "@b.c", "a@.c", "a@b.")
            invalidFormats.forEach { email ->
                assertFalse(detector.isValid(email), "Invalid format '$email' should fail")
            }
        }
        
        @Test
        @DisplayName("Exactly one @ symbol requirement")
        fun testExactlyOneAtSymbol() {
            // Valid: exactly one @
            val oneAt = listOf("a@b.c", "test@domain.com", "user@site.org")
            oneAt.forEach { email ->
                assertEquals(1, email.count { it == '@' }, "Test setup: should have exactly one @")
                assertTrue(detector.isValid(email), "One @ symbol '$email' should be valid")
            }
            
            // Invalid: zero @
            val zeroAt = listOf("abc.def", "user.domain.com", "test.site.org")
            zeroAt.forEach { email ->
                assertEquals(0, email.count { it == '@' }, "Test setup: should have zero @")
                assertFalse(detector.isValid(email), "Zero @ symbols '$email' should be invalid")
            }
            
            // Invalid: multiple @
            val multipleAt = listOf("a@b@c.d", "test@@domain.com", "@user@site.org@")
            multipleAt.forEach { email ->
                assertTrue(email.count { it == '@' } > 1, "Test setup: should have multiple @")
                assertFalse(detector.isValid(email), "Multiple @ symbols '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Exactly one period after @ symbol requirement")
        fun testExactlyOnePeriodAfterAt() {
            // Valid: exactly one period after @
            val onePeriodAfterAt = listOf("a@b.c", "test@domain.com", "user@site.org")
            onePeriodAfterAt.forEach { email ->
                val atIndex = email.indexOf('@')
                val periodAfterAt = email.substring(atIndex + 1).count { it == '.' }
                assertEquals(1, periodAfterAt, "Test setup: should have exactly one period after @")
                assertTrue(detector.isValid(email), "One period after @ '$email' should be valid")
            }
            
            // Invalid: zero periods after @
            val zeroPeriodAfterAt = listOf("a@b", "test@domain", "user@site")
            zeroPeriodAfterAt.forEach { email ->
                val atIndex = email.indexOf('@')
                val periodAfterAt = email.substring(atIndex + 1).count { it == '.' }
                assertEquals(0, periodAfterAt, "Test setup: should have zero periods after @")
                assertFalse(detector.isValid(email), "Zero periods after @ '$email' should be invalid")
            }
            
            // Invalid: multiple periods after @
            val multiplePeriodAfterAt = listOf("a@b.c.d", "test@domain.co.uk", "user@site..org")
            multiplePeriodAfterAt.forEach { email ->
                val atIndex = email.indexOf('@')
                val periodAfterAt = email.substring(atIndex + 1).count { it == '.' }
                assertTrue(periodAfterAt > 1, "Test setup: should have multiple periods after @")
                assertFalse(detector.isValid(email), "Multiple periods after @ '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("No empty parts requirement")
        fun testNoEmptyPartsRequirement() {
            // Valid: all parts non-empty
            val nonEmptyParts = listOf("a@b.c", "test@domain.com", "user123@site456.org789")
            nonEmptyParts.forEach { email ->
                val parts = email.split('@', '.')
                assertTrue(parts.all { it.isNotEmpty() }, "Test setup: all parts should be non-empty")
                assertTrue(detector.isValid(email), "Non-empty parts '$email' should be valid")
            }
            
            // Invalid: empty part1
            val emptyPart1 = listOf("@b.c", "@domain.com", "@site.org")
            emptyPart1.forEach { email ->
                assertFalse(detector.isValid(email), "Empty part1 '$email' should be invalid")
            }
            
            // Invalid: empty part2
            val emptyPart2 = listOf("a@.c", "test@.com", "user@.org")
            emptyPart2.forEach { email ->
                assertFalse(detector.isValid(email), "Empty part2 '$email' should be invalid")
            }
            
            // Invalid: empty part3
            val emptyPart3 = listOf("a@b.", "test@domain.", "user@site.")
            emptyPart3.forEach { email ->
                assertFalse(detector.isValid(email), "Empty part3 '$email' should be invalid")
            }
        }
        
        @Test
        @DisplayName("Character restrictions requirement")
        fun testCharacterRestrictions() {
            // Valid: no spaces or misplaced @ symbols
            val validChars = listOf(
                "test123!@#$%^&*()@domain789!@#$%^&*().com123!@#$%^&*()",
                "user_name-123@domain-name_123.extension-123_name",
                "!#$%&'*+-/=?^_`{|}~@!#$%&'*+-/=?^_`{|}~.!#$%&'*+-/=?^_`{|}~"
            )
            validChars.forEach { email ->
                assertFalse(email.contains(' '), "Test setup: should not contain spaces")
                assertEquals(1, email.count { it == '@' }, "Test setup: should have exactly one @")
                assertTrue(detector.isValid(email), "Valid characters '$email' should be valid")
            }
            
            // Invalid: contains spaces
            val withSpaces = listOf("a b@c.d", "a@b c.d", "a@b.c d", "a @b.c", "a@ b.c", "a@b. c")
            withSpaces.forEach { email ->
                assertTrue(email.contains(' '), "Test setup: should contain space")
                assertFalse(detector.isValid(email), "With spaces '$email' should be invalid")
            }
            
            // Invalid: multiple @ symbols
            val multipleAt = listOf("a@b@c.d", "a@@b.c", "@a@b.c")
            multipleAt.forEach { email ->
                assertTrue(email.count { it == '@' } > 1, "Test setup: should have multiple @")
                assertFalse(detector.isValid(email), "Multiple @ '$email' should be invalid")
            }
        }
    }
}