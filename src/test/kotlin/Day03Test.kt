import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
    fun `load claim from string`() {
        Claim("#1 @ 1,3: 4x4").positions shouldContainExactlyInAnyOrder listOf(
            Position(1, 3),
            Position(2, 3),
            Position(3, 3),
            Position(4, 3),
            Position(1, 4),
            Position(2, 4),
            Position(3, 4),
            Position(4, 4),
            Position(1, 5),
            Position(2, 5),
            Position(3, 5),
            Position(4, 5),
            Position(1, 6),
            Position(2, 6),
            Position(3, 6),
            Position(4, 6)
        )

        Claim("#1 @ 9,28: 2x1").positions shouldContainExactlyInAnyOrder listOf(
            Position(9, 28),
            Position(10, 28)
        )
    }

    @Test
    fun `get overlaping positions`() {
        listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
        ).countOverlapingPositions() shouldBe 4
    }
}




