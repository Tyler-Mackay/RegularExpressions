import states.FirstIntegerState
import states.FirstValueState
import states.State
import states.ValidState

class IntegerDetector: StateDetector {
    override fun isValid(string: String): Boolean {
        var state : State = FirstIntegerState()
        for (letter in string) {
            state = state.consumeLetter(letter.toString())
        }
        return state is ValidState
    }
}