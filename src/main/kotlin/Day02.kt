//<editor-fold desc="Part 1">
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
//</editor-fold>


//<editor-fold desc="Part 2">
fun List<String>.findCommonLetters(): String? {
    return mapNotNull { boxId ->
        findMatchingBox(boxId)?.commonCharsWith(boxId)
    }.firstOrNull()
}

private fun List<String>.findMatchingBox(boxId: String) =
    firstOrNull {
        boxId differByOnlyOneCharFrom it
    }


infix fun String.differByOnlyOneCharFrom(other: String) =
    zip(other).count { (first, second) ->
        first != second
    } == 1

fun String.commonCharsWith(other: String) =
    zip(other).filter { (first, second) ->
        first == second
    }
    .joinToString(separator = "") { it.first.toString() }

//</editor-fold>