package states

import NON_ZERO_DIGITS

class FirstValueState: State {
    override fun consumeLetter(letter: String) =
        when (letter) {
            "0" -> ZeroFirstState()
            in NON_ZERO_DIGITS -> HasDigitsState()
            "." -> HasPeriodState()
            else -> InvalidState()
        }
}