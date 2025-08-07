package states

class AwaitingFirstCharacterState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in " @" -> InvalidState()
            else -> LookingForAtState()
    }
}