import java.io.File

fun main() {
    solveDay01Part1()
}

fun solveDay01Part1() {
    val changes = File("src/main/resources/day_01/frequency_changes.log")
        .readLines()

    println(Day01.calculateFrequencyDrift(0, changes))
}
