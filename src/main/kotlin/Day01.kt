object Day01 {

    fun calculateFrequencyDrift(startingFrequency: Int, changes: List<String>): Int {
        return changes.fold(startingFrequency) { result, change ->
            result + change.toInt()
        }
    }

}
