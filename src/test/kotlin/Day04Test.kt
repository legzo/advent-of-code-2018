
import GuardEvent.FallingAsleepEvent
import GuardEvent.NewShiftEvent
import GuardEvent.WakingUpEvent
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
                event.timeStamp.day shouldBe LocalDate.of(1518, 11, 1)
                event.timeStamp.minute shouldBe 0
                event.guardId shouldBe "10"
            }
            else -> fail("Bad event type")
        }
    }

    @Test
    fun `parse new shift event and correct date for early start`() {
        val event = GuardEvent.fromString("[1518-11-01 23:58] Guard #99 begins shift")

        when (event) {
            is NewShiftEvent -> {
                event.timeStamp.day shouldBe LocalDate.of(1518, 11, 2)
                event.timeStamp.minute shouldBe 0
                event.guardId shouldBe "99"
            }
            else -> fail("Bad event type")
        }
    }

    @Test
    fun `parse falling asleep event`() {
        val event = GuardEvent.fromString("[1518-11-01 00:05] falls asleep")

        when (event) {
            is FallingAsleepEvent -> {
                event.timeStamp.day shouldBe LocalDate.of(1518, 11, 1)
                event.timeStamp.minute shouldBe 5
            }
            else -> fail("Bad event type")
        }
    }

    @Test
    fun `parse waking up event`() {
        val event = GuardEvent.fromString("[1518-11-01 00:25] wakes up")

        when (event) {
            is WakingUpEvent -> {
                event.timeStamp.day shouldBe LocalDate.of(1518, 11, 1)
                event.timeStamp.minute shouldBe 25
            }
            else -> fail("Bad event type")
        }
    }

}