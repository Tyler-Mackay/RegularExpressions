package states

import DIGITS

class HasPeriodState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in DIGITS -> ValidState()
            else -> InvalidState()
        }
}