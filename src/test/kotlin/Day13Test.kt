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

        carts[0, 2] shouldBe Cart.FACING_BOTTOM

    }
}
