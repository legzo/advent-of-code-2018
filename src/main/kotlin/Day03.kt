//<editor-fold desc="Part 1">

data class Position(val x: Int, val y: Int)

class Claim(stringRepresentation: String) {

    val id: String
    val positions: List<Position>

    init {

        val tokens = stringRepresentation
            .split('@', ',', ':', 'x')

        id = tokens[0].replace("#", "").trim()
        val topLeftX = tokens.getInt(1)
        val topLeftY = tokens.getInt(2)
        val width = tokens.getInt(3)
        val height = tokens.getInt(4)
        val bottomRightX = topLeftX + width
        val bottomRightY = topLeftY + height

        positions = (topLeftX until bottomRightX).flatMap { x ->
            (topLeftY until bottomRightY).map { y ->
                Position(x, y)
            }
        }
    }

    private fun List<String>.getInt(position: Int) = this[position].trim().toInt()
}

fun List<String>.getOverlapingPositions() =
    flatMap { Claim(it).positions }
        .groupBy { it }
        .filter { it.value.size > 1 }
        .keys

fun List<String>.countOverlapingPositions() =
    getOverlapingPositions().count()

//</editor-fold>

//<editor-fold desc="Part 2">

fun List<String>.getIntactClaimId(): String? {
    val overlapingPositions = getOverlapingPositions()
    return map { Claim(it) }
        .firstOrNull {
            it.positions.all { position ->
                position !in overlapingPositions
            }
        }?.id
}

//</editor-fold>
