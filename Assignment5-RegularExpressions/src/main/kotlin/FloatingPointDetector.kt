import states.FirstValueState
import states.State
import states.ValidState

class FloatingPointDetector : StateDetector {
    override fun isValid(string: String): Boolean {
        var state : State = FirstValueState()
            for (letter in string) {
                state = state.consumeLetter(letter.toString())
            }
            return state is ValidState
    }
}