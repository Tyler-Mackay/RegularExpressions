package states

class LookingForPeriodState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in " @" -> InvalidState()
            "." -> ValidEmailState()
            else -> this
    }
}