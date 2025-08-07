package states

class ZeroFirstState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            "." -> HasPeriodState()
            else -> InvalidState()
        }
}