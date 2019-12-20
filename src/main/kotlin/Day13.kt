enum class Cart(val char: Char) {
    FACING_LEFT('<'),
    FACING_RIGHT('>'),
    FACING_TOP('^'),
    FACING_BOTTOM('v'),
    ;


    val path: Path
        get() = when (this) {
            FACING_LEFT, FACING_RIGHT -> Path.HORIZONTAL
            FACING_TOP, FACING_BOTTOM -> Path.VERTICAL
        }

    companion object {
        fun parse(char: Char): Cart? = Cart.values().firstOrNull { it.char == char }
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
        fun parse(char: Char, cart: Cart?): Path? {
            cart?.let {
                return it.path
            }

            return Path.values().firstOrNull { it.char == char }
        }
    }
}

data class Tracks(
    val paths: Map<Coords, Path?>,
    val carts: Map<Coords, Cart?> // TODO List<Cart> pour que chaque Cart ait un état (virages)
) {
    companion object {
        fun from(input: String): Tracks {
            val (carts, paths) = input
                .split("\n")
                .mapIndexed { y, line ->
                    line.toCharArray().mapIndexed { x, char ->
                        val cart = Cart.parse(char)
                        val path = Path.parse(char, cart)
                        (Coords(x, y) to cart) to (Coords(x, y) to path)
                    }
                }.flatten()
                .unzip()

            return Tracks(carts = carts.toMap(), paths = paths.toMap())
        }
    }
}

operator fun <T> Map<Coords, T?>.get(x: Int, y: Int): T? = this[Coords(x, y)]