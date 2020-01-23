import CartOrientation.FACING_BOTTOM
import CartOrientation.FACING_LEFT
import CartOrientation.FACING_RIGHT
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day13Test {

    @Test
    fun `should create tracks from string`() {
        val (paths, carts) = Tracks.from(
            """
             /----\
             |    |
             v    |
             \----/
             """.trimIndent()
        )

        paths[0, 0] shouldBe Path.BOTTOM_TO_RIGHT
        paths[1, 0] shouldBe Path.HORIZONTAL
        paths[0, 1] shouldBe Path.VERTICAL
        paths[5, 3] shouldBe Path.BOTTOM_TO_RIGHT
        paths[0, 2] shouldBe Path.VERTICAL

        carts shouldHaveSize 1

        carts[0] shouldBe Cart(orientation = FACING_BOTTOM, point = Point(0, 2))
    }

    @Test
    fun `should move a single cart`() {
        val initialTracks = Tracks.from(
            """
             /----\
             |    |
             v    |
             \----/
             """.trimIndent()
        )

        val sequence = generateSequence(initialTracks) {
            it.move()
        }

        val (pathsAfter1Move, cartsAfter1Move) = sequence.move(1)

        checkPathsHaveNotChanged(pathsAfter1Move)
        cartsAfter1Move shouldHaveSize 1
        cartsAfter1Move[0] shouldBe Cart(orientation = FACING_RIGHT, point = Point(0, 3))


        val (pathsAfter10Moves, cartsAfter10Moves) = sequence.move(10)

        checkPathsHaveNotChanged(pathsAfter1Move)
        cartsAfter10Moves shouldHaveSize 1
        cartsAfter10Moves[0] shouldBe Cart(orientation = FACING_LEFT, point = Point(4, 0))
    }

    private fun Sequence<Tracks>.move(times: Int) = take(times + 1).last()

    private fun checkPathsHaveNotChanged(paths: Map<Point, Path>) {
        paths[0, 0] shouldBe Path.BOTTOM_TO_RIGHT
        paths[1, 0] shouldBe Path.HORIZONTAL
        paths[0, 1] shouldBe Path.VERTICAL
        paths[5, 3] shouldBe Path.BOTTOM_TO_RIGHT
        paths[0, 2] shouldBe Path.VERTICAL
    }
}
