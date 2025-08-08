package states

class ValidBinaryState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            "1" -> this
            "0" -> LookingForOneState()
            else -> InvalidState()
    }
}