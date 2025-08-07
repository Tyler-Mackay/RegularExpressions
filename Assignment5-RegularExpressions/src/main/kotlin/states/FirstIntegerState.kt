package states

import NON_ZERO_DIGITS
import StateDetector

class FirstIntegerState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in NON_ZERO_DIGITS -> ValidState()
            else -> InvalidState()
    }
}