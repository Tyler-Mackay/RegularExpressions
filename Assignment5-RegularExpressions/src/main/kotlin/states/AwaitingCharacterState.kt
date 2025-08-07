package states

import CAPITAL_LETTERS
import SPECIAL_CHARACTERS

class AwaitingCharacterState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in CAPITAL_LETTERS -> HasCapitalState()
            in SPECIAL_CHARACTERS -> HasSpecialCharacterState()
            else -> this
    }
}