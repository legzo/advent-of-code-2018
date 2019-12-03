import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day07Test {

    private val input = listOf(
        "C" to "A",
        "C" to "F",
        "A" to "B",
        "A" to "D",
        "B" to "E",
        "D" to "E",
        "F" to "E"
    )

    @Test
    fun `should build requirements`() {
        input.toRequirements() shouldBe mapOf(
            "C" to setOf(),
            "A" to setOf("C"),
            "B" to setOf("A"),
            "D" to setOf("A"),
            "E" to setOf("B", "D", "F"),
            "F" to setOf("C")
        )
    }

    @Test
    fun `should remove requirement and get next step`() {
        mapOf(
            "A" to setOf("C"),
            "B" to setOf("A"),
            "D" to setOf("A"),
            "E" to setOf("B", "D", "F"),
            "F" to setOf("C")
        ).getNextStep('C') shouldBe Step(
            stepLetter = "A",
            newRequirements = mapOf(
                "B" to setOf("A"),
                "D" to setOf("A"),
                "E" to setOf("B", "D", "F"),
                "F" to setOf()
            )
        )
    }

    @Test
    fun `should get step sequence`() {
        input.toStepSequence() shouldBe "CABDFE"
    }
}


