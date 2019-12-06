data class Coords(val x: Int, val y: Int) {

}

typealias Area = List<Coords>

data class Grid(val coords: List<Coords>) {

    val min = Coords(
        x = coords.minBy { it.x }!!.x,
        y = coords.minBy { it.y }!!.y
    )

    val max = Coords(
        x = coords.maxBy { it.x }!!.x,
        y = coords.maxBy { it.y }!!.y
    )

    val largestAreaSize: Int = 0

}