package states

import SPECIAL_CHARACTERS

class HasSpecialAndCapitalState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in SPECIAL_CHARACTERS -> LookingForNormalCharacterState()
            else -> this
        }
}