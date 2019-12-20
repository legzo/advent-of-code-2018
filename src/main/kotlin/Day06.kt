import kotlin.math.abs

data class Coords(val x: Int, val y: Int)

data class Grid(val centers: List<Coords>) {

    val min = Coords(
        x = centers.minBy { it.x }!!.x,
        y = centers.minBy { it.y }!!.y
    )

    val max = Coords(
        x = centers.maxBy { it.x }!!.x,
        y = centers.maxBy { it.y }!!.y
    )

    val largestAreaSize: Int
        get() {
            val map = mutableMapOf<Coords, Coords?>()
            for (y in min.y..max.y) {
                for (x in min.x..max.x) {
                    val point = Coords(x, y)
                    val listMinCenter = distanceFromCenters(point)
                        .minBy { it.key }!!
                        .value
                    map[point] =
                        if (listMinCenter.size == 1) {
                            listMinCenter[0]
                        } else {
                            null
                        }
                }
            }

            val biggestRegion = map.toList()
                .groupBy { it.second }
                .mapValues { it.value.map { pair -> pair.first } }
                .filter { !it.value.any { coords -> coords.x == min.x || coords.x == max.x || coords.y == min.y || coords.y == max.y } }
                .maxBy { it.value.size }


            return biggestRegion?.value?.size ?: 0
        }

    val safeAreaSize: Int
        get() {
            var areaSize = 0
            for (y in min.y..max.y) {
                for (x in min.x..max.x) {
                    val point = Coords(x, y)
                    val distanceSum = centers
                        .sumBy { abs(it.x - point.x) + abs(it.y - point.y) }
                    if (distanceSum < 10000) {
                        areaSize++
                    }
                }
            }
            return areaSize
        }

    private fun distanceFromCenters(point: Coords) = centers
        .groupBy { abs(it.x - point.x) + abs(it.y - point.y) }

}