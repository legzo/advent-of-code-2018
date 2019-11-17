//<editor-fold desc="Part 1">

data class Position(val x: Int, val y: Int)

class Claim(stringRepresentation: String) {

    val positions: List<Position>

    init {

        val tokens = stringRepresentation
            .split('@', ',', ':', 'x')

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


fun List<String>.countOverlapingPositions() =
    flatMap { Claim(it).positions }
        .groupBy { it }
        .count { it.value.size > 1 }

//</editor-fold>