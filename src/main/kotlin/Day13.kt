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

        val nextPoint = with(point) {
            when (orientation) {
                FACING_LEFT -> Point(x - 1, y)
                FACING_RIGHT -> Point(x + 1, y)
                FACING_TOP -> Point(x, y - 1)
                FACING_BOTTOM -> Point(x, y + 1)
            }
        }

        val nextOrientation = when (paths.getValue(nextPoint)) {
            VERTICAL -> orientation
            HORIZONTAL -> orientation
            BOTTOM_TO_RIGHT -> orientation.onBottomToRight()
            BOTTOM_TO_LEFT -> orientation.onBottomToLeft()
            INTERSECTION -> orientation // TODO tourner aux intersections
        }

        return Cart(nextOrientation, nextPoint)
    }

}

enum class CartOrientation(val char: Char) {
    FACING_LEFT('<') {
        override fun onBottomToLeft() = FACING_TOP
        override fun onBottomToRight() = FACING_BOTTOM
    },
    FACING_RIGHT('>') {
        override fun onBottomToLeft() = FACING_BOTTOM
        override fun onBottomToRight() = FACING_TOP
    },
    FACING_TOP('^') {
        override fun onBottomToLeft() = FACING_LEFT
        override fun onBottomToRight() = FACING_RIGHT
    },
    FACING_BOTTOM('v') {
        override fun onBottomToLeft() = FACING_RIGHT
        override fun onBottomToRight() = FACING_LEFT
    },
    ;

    abstract fun onBottomToLeft(): CartOrientation
    abstract fun onBottomToRight(): CartOrientation

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

            return values().firstOrNull { it.char == char }
        }
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

        private fun <T : Any> forEachPointOf(input: String, mapper: (x: Int, y: Int, char: Char) -> T?): List<T> {
            return input
                .split("\n")
                .mapIndexed { y, line ->
                    line.toCharArray().mapIndexed { x, char ->
                        mapper(x, y, char)
                    }
                }.flatten().filterNotNull()
        }

        fun from(input: String): Tracks {

            val carts = forEachPointOf(input) { x: Int, y: Int, char: Char ->
                val orientation = CartOrientation.parse(char)
                if (orientation != null) Cart(orientation, Point(x, y)) else null
            }

            val paths = forEachPointOf(input) { x: Int, y: Int, char: Char ->
                val path = Path.parse(char, CartOrientation.parse(char))
                if (path != null) (Point(x, y) to path) else null
            }

            return Tracks(paths.toMap(), carts)
        }
    }
}

operator fun <T> Map<Point, T?>.get(x: Int, y: Int): T? = this[Point(x, y)]