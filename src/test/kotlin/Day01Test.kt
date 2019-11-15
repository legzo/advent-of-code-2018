import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun `calculate frequency drift`() {
        listOf("+1", "-2", "+3", "+1").calculateFrequencyDrift() shouldBe 3
        listOf("+1", "+1", "+1").calculateFrequencyDrift() shouldBe 3
        listOf("+1", "+1", "-2").calculateFrequencyDrift() shouldBe 0
        listOf("-1", "-2", "-3").calculateFrequencyDrift() shouldBe -6
    }

    @Test
    fun `calibrate device`() {
        listOf("+1", "-1").findFirstRepeatingFrequency() shouldBe 0
        listOf("+3", "+3", "+4", "-2", "-4").findFirstRepeatingFrequency() shouldBe 10
        listOf("-6", "+3", "+8", "+5", "-6").findFirstRepeatingFrequency() shouldBe 5
        listOf("+7", "+7", "-2", "-7", "-4").findFirstRepeatingFrequency() shouldBe 14
    }
}