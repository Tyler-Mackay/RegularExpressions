package states

import SPECIAL_CHARACTERS

class HasCapitalState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in SPECIAL_CHARACTERS -> HasSpecialAndCapitalState()
            else -> this
    }
}