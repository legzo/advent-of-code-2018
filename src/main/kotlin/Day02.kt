fun List<String>.calculateChecksum(): Int {

    val occurences = this
        .fold(mapOf(2 to 0, 3 to 0)) { map, word ->
            val count = word.toCharArray()
                .toList()
                .groupingBy {
                    it
                }
                .eachCount()
                .values
                .groupBy { it }
                .mapValues { it.value.size }

            mapOf(
                2 to map.getFor(2) + count.oneIfPresent(2),
                3 to map.getFor(3) + count.oneIfPresent(3)
            )
        }

    return occurences.getFor(2) * occurences.getFor(3)
}

private fun Map<Int, Int>.oneIfPresent(key: Int) = if (containsKey(key)) 1 else 0

private fun Map<Int, Int>.getFor(i: Int) = this.getOrDefault(i, 0)

