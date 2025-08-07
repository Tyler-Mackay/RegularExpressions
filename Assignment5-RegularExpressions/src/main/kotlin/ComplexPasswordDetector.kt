import states.*

class ComplexPasswordDetector: StateDetector {
    override fun isValid(string: String): Boolean {
        if (string.isEmpty()) return false

        var state : State = AwaitingCharacterState()
        var password = ""
        for (letter in string) {
            state = state.consumeLetter(letter.toString())
            password += letter
        }

        return state is HasSpecialAndCapitalState &&
                string.length >= 8 &&
                string.last() !in SPECIAL_CHARACTERS
    }
}