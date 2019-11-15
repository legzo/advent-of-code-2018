import java.io.File

fun main() {
    solveDay01Part1()
    solveDay01Part2()
}

fun solveDay01Part1() {
    println(getChangesFromFile().calculateFrequencyDrift()) // 490
}

fun solveDay01Part2() {
    println(getChangesFromFile().findFirstRepeatingFrequency()) // 70357
}

private fun getChangesFromFile() = File("src/main/resources/day_01/frequency_changes.log").readLines()
