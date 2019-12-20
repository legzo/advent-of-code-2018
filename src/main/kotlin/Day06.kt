import kotlin.math.abs

data class Point(val x: Int, val y: Int)

typealias Center = Point

data class Grid(val centers: List<Center>) {

    val min = Point(
        x = centers.minBy { it.x }!!.x,
        y = centers.minBy { it.y }!!.y
    )

    val max = Point(
        x = centers.maxBy { it.x }!!.x,
        y = centers.maxBy { it.y }!!.y
    )

    val largestAreaSize: Int
        get() = pointsToClosestCenter()
            .groupBy { it.second }
            .mapValues { it.value.map { (point, _) -> point } }
            .filter { (_, pointsOfRegion) -> pointsOfRegion.none(::isTouchingTheBorder) }
            .map { it.value.size }
            .max() ?: 0

    fun getSafestAreaSizeFor(maxDistance: Int) =
        (min.y..max.y).flatMap { y ->
            (min.x..max.x).map { x ->
                centers.sumBy { Point(x, y).distanceTo(it) }
            }
        }.count { it < maxDistance }


    private fun pointsToClosestCenter(): List<Pair<Point, Center>> {
        return (min.y..max.y).flatMap { y ->
            (min.x..max.x).mapNotNull { x ->
                val point = Point(x, y)
                val closestCenters = point.distancesToCenters()
                    .minBy { it.key }!!
                    .value

                if (closestCenters.size == 1) {
                    point to closestCenters[0]
                } else null
            }
        }
    }

    private fun isTouchingTheBorder(point: Point) =
        point.x == min.x || point.x == max.x || point.y == min.y || point.y == max.y

    private fun Point.distancesToCenters(): Map<Int, List<Center>> = centers.groupBy { distanceTo(it) }

    private fun Point.distanceTo(other: Point) = abs(other.x - x) + abs(other.y - y)

}