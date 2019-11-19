
import GuardEvent.FallingAsleepEvent
import GuardEvent.NewShiftEvent
import GuardEvent.WakingUpEvent
import io.kotlintest.fail
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

class Day04Test {

    private val fullDataset = listOf(
        "[1518-11-01 00:00] Guard #10 begins shift",
        "[1518-11-01 00:05] falls asleep",
        "[1518-11-01 00:25] wakes up",
        "[1518-11-01 00:30] falls asleep",
        "[1518-11-01 00:55] wakes up",
        "[1518-11-01 23:58] Guard #99 begins shift",
        "[1518-11-02 00:40] falls asleep",
        "[1518-11-02 00:50] wakes up",
        "[1518-11-03 00:05] Guard #10 begins shift",
        "[1518-11-03 00:24] falls asleep",
        "[1518-11-03 00:29] wakes up",
        "[1518-11-04 00:02] Guard #99 begins shift",
        "[1518-11-04 00:36] falls asleep",
        "[1518-11-04 00:46] wakes up",
        "[1518-11-05 00:03] Guard #99 begins shift",
        "[1518-11-05 00:45] falls asleep",
        "[1518-11-05 00:55] wakes up"
    )

    @Test
    fun `parse new shift event`() {
        val event = GuardEvent.fromString("[1518-11-01 00:00] Guard #10 begins shift")

        when (event) {
            is NewShiftEvent -> {
                event.timeStamp.day shouldBe LocalDate.of(1518, 11, 1)
                event.timeStamp.minute shouldBe 0
                event.guardId shouldBe 10
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
                event.guardId shouldBe 99
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

    @Test
    fun `get asleep minutes for a list of days`() {
        fullDataset.calculateAsleepMinutes() shouldBe listOf(
            GuardReport(LocalDate.of(1518, 11, 1), 10, listOf(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54)),
            GuardReport(LocalDate.of(1518, 11, 2), 99, listOf(40, 41, 42, 43, 44, 45, 46, 47, 48, 49)),
            GuardReport(LocalDate.of(1518, 11, 3), 10, listOf(24, 25, 26, 27, 28)),
            GuardReport(LocalDate.of(1518, 11, 4), 99, listOf(36, 37, 38, 39, 40, 41, 42, 43, 44, 45)),
            GuardReport(LocalDate.of(1518, 11, 5), 99, listOf(45, 46, 47, 48, 49, 50, 51, 52, 53, 54))
        )
    }

    @Test
    fun `get guard id for a given night`() {
        listOf(
            "[1518-11-01 00:00] Guard #10 begins shift",
            "[1518-11-01 00:05] falls asleep",
            "[1518-11-01 00:25] wakes up",
            "[1518-11-01 00:30] falls asleep",
            "[1518-11-01 00:55] wakes up"
        ).getGuardId() shouldBe 10
    }

    @Test
    fun `get sleepiest guard id`() {
        fullDataset.getSleepiestGuardId() shouldBe 10
    }

    @Test
    fun `get sleepiest guard sleepiest minute`() {
        fullDataset.getSleepiestGuardSleepiestMinute() shouldBe 24
    }

    @Test
    fun `get checksum for strategy 1`() {
        fullDataset.getChecksumForStrategy1() shouldBe 240
    }

    @Test
    fun `get checksum for strategy 2`() {
        fullDataset.getChecksumForStrategy2() shouldBe 4455
    }
}
