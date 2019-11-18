import java.time.LocalDate

sealed class GuardEvent {

    data class NewShiftEvent(val timeStamp: TimeStamp, val guardId: String) : GuardEvent()
    data class FallingAsleepEvent(val timeStamp: TimeStamp) : GuardEvent()
    data class WakingUpEvent(val timeStamp: TimeStamp) : GuardEvent()
    object UnknownEvent : GuardEvent()

    companion object {

        private val regexForDate = Regex("\\[(\\d{4}-\\d{2}-\\d{2}) \\d{2}:(\\d{2})]")
        private val regexForNewShift = Regex(".* Guard #(\\d+)")

        data class TimeStamp(val day: LocalDate, val minute: Int)

        fun fromString(input: String): GuardEvent {
            val timeStamp = input.parseTimeStamp() ?: return UnknownEvent

            return when {
                input.endsWith("wakes up") -> WakingUpEvent(timeStamp)
                input.endsWith("falls asleep") -> FallingAsleepEvent(timeStamp)
                regexForNewShift.containsMatchIn(input) -> {
                    val guardId = input.parseGuardId() ?: return UnknownEvent
                    NewShiftEvent(timeStamp, guardId)
                }
                else -> UnknownEvent
            }
        }

        private fun String.parseTimeStamp(): TimeStamp? {
            return regexForDate
                .find(this)
                ?.groupValues
                ?.let {
                    val parsedMinute = it[2].toInt()
                    val parsedDay = LocalDate.parse(it[1])

                    getCorrectedTimestamp(parsedDay, parsedMinute)
                }
        }

        private fun getCorrectedTimestamp(
            parsedDay: LocalDate,
            parsedMinute: Int
        ) = TimeStamp(
            day = if (parsedMinute > 50) parsedDay.plusDays(1) else parsedDay,
            minute = if (parsedMinute > 50) 0 else parsedMinute
        )

        private fun String.parseGuardId() =
            regexForNewShift
                .find(this)
                ?.groupValues
                ?.get(1)
    }

}