import states.State

interface StateDetector {
    fun isValid(string: String): Boolean
}