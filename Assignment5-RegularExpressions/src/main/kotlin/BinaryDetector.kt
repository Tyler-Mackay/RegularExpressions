import states.*

class BinaryDetector: StateDetector {
    override fun isValid(string: String): Boolean {
        var state : State = AwaitingStart1State()
        for (letter in string) {
            state = state.consumeLetter(letter.toString())
        }
        return state is ValidBinaryState
    }
}