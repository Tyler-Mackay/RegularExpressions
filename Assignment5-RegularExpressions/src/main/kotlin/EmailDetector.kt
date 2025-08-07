import states.*

class EmailDetector: StateDetector {
    override fun isValid(string: String): Boolean {
        var state : State = AwaitingFirstCharacterState()
        for (letter in string) {
            state = state.consumeLetter(letter.toString())
        }
        return state is ValidEmailState
    }
}