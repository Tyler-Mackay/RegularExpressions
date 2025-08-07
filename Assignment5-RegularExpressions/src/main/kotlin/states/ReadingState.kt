package states

class ReadingState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in "01" -> this
            else -> InvalidState()
    }
}