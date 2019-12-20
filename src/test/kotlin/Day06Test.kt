import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {

    private val inputCoords = listOf(
        Point(x = 1, y = 1),
        Point(x = 1, y = 6),
        Point(x = 8, y = 3),
        Point(x = 3, y = 4),
        Point(x = 5, y = 5),
        Point(x = 8, y = 9)
    )

    @Test
    fun `should build grid from coordinates`() {
        val grid = Grid(inputCoords)

        grid.min shouldBe Point(x = 1, y = 1)
        grid.max shouldBe Point(x = 8, y = 9)
    }

    @Test
    fun `should get areas from coordinates`() {
        Grid(inputCoords).largestAreaSize shouldBe 17
    }

    @Test
    fun `should get safeAreas from coordinates`() {
        Grid(inputCoords).getSafestAreaSizeFor(32) shouldBe 16
    }
}

