import java.time.LocalDate
import java.time.LocalDateTime

sealed class GuardEvent() {

    data class NewShiftEvent(
        val day: LocalDate,
        val minute: Int,
        val guardId: String
    ) : GuardEvent()

    object FallingAsleepEvent : GuardEvent()

    object UnknownEvent : GuardEvent()

    companion object {

        private const val datePatter = "\\[(\\d{4}\\-\\d{2}\\-\\d{2}) \\d{2}:(\\d{2})\\]"

        private val regexForNewShift = Regex("$datePatter Guard #(\\d+)")
        private val regexForFallingAsleepEvent = Regex("$datePatter falls asleep")

        data class TimeStamp(val day: LocalDateTime, val minute: Int)

        fun fromString(string: String): GuardEvent {

            return when {
                regexForFallingAsleepEvent.containsMatchIn(string) -> FallingAsleepEvent
                regexForNewShift.containsMatchIn(string) -> {
                    val matchResult = regexForNewShift.find(string)

                    matchResult?.groupValues?.let {
                        NewShiftEvent(
                            day = LocalDate.parse(it[1]),
                            minute = it[2].toInt(),
                            guardId = it[3]
                        )
                    } ?: UnknownEvent
                }

                else -> UnknownEvent
            }
        }
    }

}