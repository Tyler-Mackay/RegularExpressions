package states

import DIGITS

class ValidState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in DIGITS -> this
            else -> InvalidState()
    }
}