import Polymer.Status.FoundMatch
import Polymer.Status.LookingForMatch
import Polymer.Status.RemovedMatch
import java.time.LocalDateTime

infix fun Char.reactsWith(other: Char): Boolean {
    return this.toLowerCase() == other.toLowerCase()
        && this != other
}

class Polymer(val stringRepresentation: String) {

    val size
        get() = stringRepresentation.length

    fun reactFully(): Polymer {
        findReactingPart() ?: return this
        return this.react().reactFully()
    }

    fun react(): Polymer {
        val reactingPart = this.findReactingPart()
        return if (reactingPart != null) Polymer(this.stringRepresentation.replaceFirst(reactingPart, ""))
        else this
    }

    private fun findReactingPart(): String? {
        stringRepresentation.zipWithNext { current, next ->
            if (current reactsWith next) return listOf(current, next).joinToString(separator = "")
        }

        return null
    }

    fun findShortestPolymerLength() =
        ('A'..'Z')
            .map { this.copyWithoutIgnoreCase(it).reactFully().size }
            .min()

    private fun copyWithoutIgnoreCase(char: Char) = Polymer(
        stringRepresentation
            .replace(char.toUpperCase().toString(), "")
            .replace(char.toLowerCase().toString(), "")
    )

    //<editor-fold desc="old">
    fun oldReact(): Polymer {
        val accStart = Accumulator(LookingForMatch, '*', listOf())

        val (_, _, chars) = stringRepresentation
            .plus('*')
            .fold(accStart) { acc, char ->
                when (acc.status) {
                    is LookingForMatch -> {
                        if (acc.previousChar reactsWith char) {
                            Accumulator(FoundMatch, char, acc.chars)
                        } else Accumulator(LookingForMatch, char, acc.chars + acc.previousChar)

                    }
                    is FoundMatch -> Accumulator(RemovedMatch, char, acc.chars)
                    is RemovedMatch -> Accumulator(RemovedMatch, char, acc.chars + acc.previousChar)
                }
            }

        return Polymer(chars.joinToString(separator = "").drop(1))
    }

    fun oldFullyReact(): Polymer {
        val reacted = this.oldReact()

        return if (reacted.stringRepresentation.length != this.stringRepresentation.length) {
            println("${LocalDateTime.now()} Reacted ! size = ${reacted.stringRepresentation.length}")
            reacted.oldReact()
        } else reacted
    }

    data class Accumulator(val status: Status, val previousChar: Char, val chars: List<Char>)

    sealed class Status {
        object FoundMatch : Status()
        object RemovedMatch : Status()
        object LookingForMatch : Status()
    }
    //</editor-fold>

}
