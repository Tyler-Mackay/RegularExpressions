package states

class LookingForAtState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            " " -> InvalidState()
            "@" -> HasAtState()
            else -> this
    }
}