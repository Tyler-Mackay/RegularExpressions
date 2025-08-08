package states

import SPECIAL_CHARACTERS

class LookingForNormalCharacterState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in SPECIAL_CHARACTERS -> this
            else -> HasSpecialAndCapitalState()
    }
}