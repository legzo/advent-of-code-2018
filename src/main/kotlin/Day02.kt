fun List<String>.calculateChecksum() =
    fold(Counters.empty()) { counters, word ->
        counters.incrementFor(word)
    }.checksum()

data class Counters(val exactlyTwo: Int, val exactlyThree: Int) {

    class ResultForWord(word: String) {
        private val letterOccurences = word
            .toCharArray()
            .toList()
            .groupingBy { it }
            .eachCount()
            .values
            .groupBy { it }
            .mapValues { it.value.size }

        val exactlyTwo = letterOccurences.containsKey(2)
        val exactlyThree = letterOccurences.containsKey(3)
    }

    fun checksum() = exactlyTwo * exactlyThree

    fun incrementFor(word: String): Counters {
        val resultForWord = ResultForWord(word)

        return Counters(
            if (resultForWord.exactlyTwo) exactlyTwo + 1 else exactlyTwo,
            if (resultForWord.exactlyThree) exactlyThree + 1 else exactlyThree
        )
    }

    companion object {
        fun empty() = Counters(0, 0)
    }
}

