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

    @Test
    fun `get asleep minutes for one night`() {
        listOf(
            "[1518-11-01 23:58] Guard #99 begins shift",
            "[1518-11-02 00:40] falls asleep",
            "[1518-11-02 00:50] wakes up"
        ).getAsleepMinutes() shouldBe listOf(40, 41, 42, 43, 44, 45, 46, 47, 48, 49)

        listOf(
            "[1518-11-01 00:00] Guard #10 begins shift",
            "[1518-11-01 00:05] falls asleep",
            "[1518-11-01 00:10] wakes up",
            "[1518-11-01 00:30] falls asleep",
            "[1518-11-01 00:32] wakes up"
        ).getAsleepMinutes() shouldBe listOf(5, 6, 7, 8, 9, 30, 31)
    }

}
