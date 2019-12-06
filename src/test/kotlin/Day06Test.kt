import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {

    private val inputCoords = listOf(
        Coords(x = 1, y = 1),
        Coords(x = 1, y = 6),
        Coords(x = 8, y = 3),
        Coords(x = 3, y = 4),
        Coords(x = 5, y = 5),
        Coords(x = 8, y = 9)
    )

    @Test
    fun `should build grid from coordinates`() {
        val grid = Grid(inputCoords)

        grid.min shouldBe Coords(x = 1, y = 1)
        grid.max shouldBe Coords(x = 8, y = 9)
    }

    @Test
    fun `should get areas from coordinates`() {
        // TODO Grid(inputCoords).largestAreaSize shouldBe 17
    }
}

