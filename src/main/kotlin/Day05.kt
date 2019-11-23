import Polymer.Status.*
import java.time.LocalDateTime

class Polymer(val stringRepresentation: String) {

    fun react(): Polymer {
        val accStart = Accumulator(LookingForMatch, '*', listOf())

        val (_, _, chars) = stringRepresentation
            .plus('*')
            .fold(accStart) { acc, char ->
                when (acc.status) {
                    is LookingForMatch -> {
                        if (acc.previousChar.toLowerCase() == char.toLowerCase()) {
                            Accumulator(FoundMatch, char, acc.chars)
                        } else Accumulator(LookingForMatch, char, acc.chars + acc.previousChar)

                    }
                    is FoundMatch -> Accumulator(RemovedMatch, char, acc.chars)
                    is RemovedMatch -> Accumulator(RemovedMatch, char, acc.chars + acc.previousChar)
                }
            }

        return Polymer(chars.joinToString(separator = "").drop(1))
    }

    fun fullyReact(): Polymer {
        val reacted = this.react()

        return if (reacted.stringRepresentation.length != this.stringRepresentation.length) {
            println("${LocalDateTime.now()} Reacted ! size = ${reacted.stringRepresentation.length}")
            reacted.fullyReact()
        } else reacted
    }

    data class Accumulator(val status: Status, val previousChar: Char, val chars: List<Char>)

    sealed class Status {
        object FoundMatch : Status()
        object RemovedMatch : Status()
        object LookingForMatch : Status()
    }
}

