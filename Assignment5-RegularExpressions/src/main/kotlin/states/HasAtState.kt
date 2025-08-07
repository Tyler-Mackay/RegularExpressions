package states

class HasAtState: State {
    override fun consumeLetter(letter: String) =
        when(letter) {
            in " .@" -> InvalidState()
            else -> LookingForPeriodState()
    }
}