package states

interface State {
    fun consumeLetter(letter: String): State
}