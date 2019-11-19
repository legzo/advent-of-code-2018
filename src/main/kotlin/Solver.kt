import java.io.File

fun main() {
    //solveDay01Part1()
    //solveDay01Part2()
    //solveDay02Part1()
    //solveDay02Part2()
    //solveDay03Part1()
    //solveDay03Part2()
    solveDay04Part1()
}

fun solveDay01Part1() {
    println(getLinesFromFile("day_01/frequency_changes.log")
        .calculateFrequencyDrift()) // 490
}

fun solveDay01Part2() {
    println(getLinesFromFile("day_01/frequency_changes.log")
        .findFirstRepeatingFrequency()) // 70357
}

fun solveDay02Part1() {
    println(getLinesFromFile("day_02/boxes.txt")
        .calculateChecksum()) // 9139
}

fun solveDay02Part2() {
    println(getLinesFromFile("day_02/boxes.txt")
        .findCommonLetters()) // uqcidadzwtnhsljvxyobmkfyr
}

fun solveDay03Part1() {
    println(getLinesFromFile("day_03/claims.txt")
        .countOverlapingPositions()) // 107663
}

fun solveDay03Part2() {
    println(getLinesFromFile("day_03/claims.txt")
        .getIntactClaimId()) // 1166
}

fun solveDay04Part1() {
    println(getLinesFromFile("day_04/shifts.log")
        .getChecksumForStrategy1()) // 101262
}

private fun getLinesFromFile(filename: String) =
    File("src/main/resources/$filename").readLines()
