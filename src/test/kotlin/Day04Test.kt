import GuardEvent.*
import io.kotlintest.fail
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

class Day04Test {

    @Test
    fun `parse new shift event`() {
        val event = GuardEvent.fromString("[1518-11-01 00:00] Guard #10 begins shift")

        when (event) {
            is NewShiftEvent -> {
                event.day shouldBe LocalDate.of(1518, 11, 1)
                event.minute shouldBe 0
                event.guardId shouldBe "10"
            }
            else -> fail("Bad event type")
        }
    }

    @Test
    fun `parse falling asleep event`() {
        val event = GuardEvent.fromString("[1518-11-01 00:05] falls asleep")

        when (event) {
            is FallingAsleepEvent -> {

            }
            else -> fail("Bad event type")
        }

    }
}