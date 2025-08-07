package states

class ValidEmailState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in " .@" -> InvalidState()
            else -> this
    }
}