package states

class LookingForOneState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            "1" -> ValidBinaryState()
            "0" -> this
            else -> InvalidState()
    }
}