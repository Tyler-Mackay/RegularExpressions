package states

class AwaitingStart1State: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            "1" -> ReadingState()
            else -> InvalidState()
    }
}