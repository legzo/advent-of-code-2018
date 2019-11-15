import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun `calculate frequency drift`() {
        Day01.calculateFrequencyDrift(
                startingFrequency = 0,
        changes = listOf("+1", "-2", "+3", "+1")
        ) shouldBe 3

        Day01.calculateFrequencyDrift(
            startingFrequency = 0,
            changes = listOf("+1", "+1", "+1")
        ) shouldBe 3

        Day01.calculateFrequencyDrift(
            startingFrequency = 0,
            changes = listOf("+1", "+1", "-2")
        ) shouldBe 0

        Day01.calculateFrequencyDrift(
            startingFrequency = 0,
            changes = listOf("-1", "-2", "-3")
        ) shouldBe -6
    }
}