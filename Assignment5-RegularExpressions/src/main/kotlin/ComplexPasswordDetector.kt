import states.*

class ComplexPasswordDetector: StateDetector {
    override fun isValid(string: String): Boolean {
        var state : State = AwaitingCharacterState()
        for (letter in string) {
            state = state.consumeLetter(letter.toString())
        }

        return state is HasSpecialAndCapitalState &&
                string.length >= 8
    }
}