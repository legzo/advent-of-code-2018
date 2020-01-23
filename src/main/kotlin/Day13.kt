
import CartOrientation.FACING_BOTTOM
import CartOrientation.FACING_LEFT
import CartOrientation.FACING_RIGHT
import CartOrientation.FACING_TOP
import Path.BOTTOM_TO_LEFT
import Path.BOTTOM_TO_RIGHT
import Path.HORIZONTAL
import Path.INTERSECTION
import Path.VERTICAL

data class Cart(val orientation: CartOrientation, val point: Point) {

    fun move(paths: Map<Point, Path>): Cart {

        val nextPoint = when (orientation) {
            FACING_LEFT -> Point(point.x - 1, point.y)
            FACING_RIGHT -> Point(point.x + 1, point.y)
            FACING_TOP -> Point(point.x, point.y - 1)
            FACING_BOTTOM -> Point(point.x, point.y + 1)
        }

        val nextOrientation = when (paths.getValue(nextPoint)) {
            VERTICAL -> orientation
            HORIZONTAL -> orientation
            BOTTOM_TO_RIGHT -> when (orientation) {
                FACING_LEFT -> FACING_BOTTOM
                FACING_RIGHT -> FACING_TOP
                FACING_TOP -> FACING_RIGHT
                FACING_BOTTOM -> FACING_LEFT
            }
            BOTTOM_TO_LEFT -> when (orientation) {
                FACING_LEFT -> FACING_TOP
                FACING_RIGHT -> FACING_BOTTOM
                FACING_TOP -> FACING_LEFT
                FACING_BOTTOM -> FACING_RIGHT
            }
            INTERSECTION -> orientation
        }

        return Cart(nextOrientation, nextPoint)
    }
}

enum class CartOrientation(val char: Char) {
    FACING_LEFT('<'),
    FACING_RIGHT('>'),
    FACING_TOP('^'),
    FACING_BOTTOM('v'),
    ;

    val path: Path
        get() = when (this) {
            FACING_LEFT, FACING_RIGHT -> HORIZONTAL
            FACING_TOP, FACING_BOTTOM -> VERTICAL
        }

    companion object {
        fun parse(char: Char): CartOrientation? = values().firstOrNull { it.char == char }
    }
}

enum class Path(val char: Char) {
    VERTICAL('|'),
    HORIZONTAL('-'),
    BOTTOM_TO_RIGHT('/'),
    BOTTOM_TO_LEFT('\\'),
    INTERSECTION('+'),
    ;

    companion object {
        fun parse(char: Char, cartOrientation: CartOrientation?): Path? {
            cartOrientation?.let {
                return it.path
            }

            return values().firstOrNull { it.char == char }        }
    }
}

data class Tracks(
    val paths: Map<Point, Path>,
    val carts: List<Cart>
) {
    fun move(): Tracks {

        val movedCarts = carts.map { it.move(paths) } // TODO order

        return this.copy(carts = movedCarts)
    }

    companion object {
        fun from(input: String): Tracks {
            val (carts, paths) = input
                .split("\n")
                .mapIndexed { y, line ->
                    line.toCharArray().mapIndexed { x, char ->
                        val orientation = CartOrientation.parse(char)

                        val cart = if (orientation != null) Cart(orientation, Point(x, y)) else null
                        val path = Path.parse(char, orientation)

                        val pointPathPair = if (path != null) (Point(x, y) to path) else null

                        cart to pointPathPair
                    }
                }.flatten()
                .unzip()

            return Tracks(
                carts = carts.filterNotNull(),
                paths = paths.filterNotNull().toMap()
            )
        }
    }
}

operator fun <T> Map<Point, T?>.get(x: Int, y: Int): T? = this[Point(x, y)]