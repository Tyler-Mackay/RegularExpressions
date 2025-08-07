package states

import CAPITAL_LETTERS

class HasSpecialCharacterState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in CAPITAL_LETTERS -> HasSpecialAndCapitalState()
            else -> this
    }
}