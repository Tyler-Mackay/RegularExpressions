package states

import DIGITS

class HasDigitsState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in DIGITS -> this
            "." -> HasPeriodState()
            else -> InvalidState()
        }
}