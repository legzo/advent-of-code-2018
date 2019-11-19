import GuardEvent.FallingAsleepEvent
import GuardEvent.WakingUpEvent
import java.time.LocalDate

sealed class GuardEvent(val timeStamp: TimeStamp) {

    class NewShiftEvent(timeStamp: TimeStamp, val guardId: Int) : GuardEvent(timeStamp)
    class FallingAsleepEvent(timeStamp: TimeStamp) : GuardEvent(timeStamp)
    class WakingUpEvent(timeStamp: TimeStamp) : GuardEvent(timeStamp)

    companion object {

        private val regexForDate = Regex("\\[(\\d{4}-\\d{2}-\\d{2}) \\d{2}:(\\d{2})]")
        private val regexForNewShift = Regex(".* Guard #(\\d+)")

        data class TimeStamp(val day: LocalDate, val minute: Int)

        fun fromString(input: String): GuardEvent? {
            val timeStamp = input.parseTimeStamp() ?: return null

            return when {
                input.endsWith("wakes up") -> WakingUpEvent(timeStamp)
                input.endsWith("falls asleep") -> FallingAsleepEvent(timeStamp)
                regexForNewShift.containsMatchIn(input) -> {
                    val guardId = input.parseGuardId() ?: return null
                    NewShiftEvent(timeStamp.correctForEarlyStart(), guardId.toInt())
                }
                else -> null
            }
        }

        //<editor-fold desc="Utils">

        private fun String.parseTimeStamp() =
            regexForDate
                .find(this)
                ?.groupValues
                ?.let {
                    TimeStamp(
                        day = LocalDate.parse(it[1]),
                        minute = it[2].toInt()
                    )
                }

        private fun String.parseGuardId() =
            regexForNewShift
                .find(this)
                ?.groupValues
                ?.get(1)

        private fun TimeStamp.correctForEarlyStart() =
            TimeStamp(
                day = if (minute > 40) day.plusDays(1) else day,
                minute = if (minute > 40) 0 else minute
            )

        //</editor-fold>
    }

}

data class GuardReport(val day: LocalDate, val guardId: Int, val asleepMinutes: List<Int>)

fun List<String>.parseEvents(): List<GuardEvent> =
    mapNotNull { GuardEvent.fromString(it) }
        .sortedBy { it.timeStamp.minute }
        .sortedBy { it.timeStamp.day }

fun List<GuardEvent>.wakingUpEvents() = filterIsInstance<WakingUpEvent>()

fun List<GuardEvent>.fallingAsleepEvents() = filterIsInstance<FallingAsleepEvent>()

private fun List<GuardEvent>.getAsleepMinutesForEvents(): List<Int> {
    return fallingAsleepEvents()
        .zip(wakingUpEvents())
        .flatMap { (fallingAsleepEvent, wakingUpEvent) ->
            (0..60).filter {
                fallingAsleepEvent.timeStamp.minute <= it
                        && it < wakingUpEvent.timeStamp.minute
            }
        }
}

fun List<String>.getGuardId() = parseEvents().getGuardIdForEvents()

private fun List<GuardEvent>.getGuardIdForEvents() = filterIsInstance<GuardEvent.NewShiftEvent>().first().guardId

fun List<String>.getAsleepMinutes() = parseEvents().getAsleepMinutesForEvents()

fun List<String>.calculateAsleepMinutes() =
    parseEvents()
        .groupBy { it.timeStamp.day }
        .map { GuardReport(it.key, it.value.getGuardIdForEvents(), it.value.getAsleepMinutesForEvents()) }


fun List<String>.getSleepiestGuardId() = getSleepiestGuardRecords()?.key ?: 0

fun List<String>.getSleepiestGuardSleepiestMinute() =
    getSleepiestGuardRecords()
        ?.value
        ?.flatMap { it.asleepMinutes }
        ?.groupBy { it }
        ?.maxBy { it.value.size }
        ?.key
        ?: 0

private fun List<String>.getSleepiestGuardRecords() =
    calculateAsleepMinutes()
        .groupBy { it.guardId }
        .maxBy { it.value.sumBy { report -> report.asleepMinutes.size } }

fun List<String>.getChecksumForStrategy1() = getSleepiestGuardId() * getSleepiestGuardSleepiestMinute()

fun List<String>.getChecksumForStrategy2(): Int? {
    val sleepiestMinuteByGuardId = calculateAsleepMinutes()
        .groupBy { it.guardId }
        .mapValues { (_, reports) ->
            reports.findSleepiestMinute()
        }

    val (guardId, sleepiestMinuteForThisGuard) = sleepiestMinuteByGuardId
        .maxBy { it.value.second }
        ?: return null

    return guardId * sleepiestMinuteForThisGuard.first
}

private fun List<GuardReport>.findSleepiestMinute() =
    flatMap { report -> report.asleepMinutes }
        .groupingBy { it }
        .eachCount()
        .maxBy { it.value }
        ?.toPair()
        ?: 0 to 0

