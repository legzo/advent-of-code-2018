import java.io.File

fun main() {
    //solveDay01Part1()
    //solveDay01Part2()
    //solveDay02Part1()
    //solveDay02Part2()
    //solveDay03Part1()
    //solveDay03Part2()
    //solveDay04Part1()
    //solveDay04Part2()
    //solveDay05Part1()
    //solveDay05Part2()
    solveDay06Part1And2()
    //solveDay07Part1()
    //solveDay07Part2()
    //solveDay08Part1()
    //solveDay08Part2()
    //solveDay10Part1And2()
}

fun solveDay01Part1() {
    println(
        getLinesFromFile("day_01/frequency_changes.log")
            .calculateFrequencyDrift()
    ) // 490
}

fun solveDay01Part2() {
    println(
        getLinesFromFile("day_01/frequency_changes.log")
            .findFirstRepeatingFrequency()
    ) // 70357
}

fun solveDay02Part1() {
    println(
        getLinesFromFile("day_02/boxes.txt")
            .calculateChecksum()
    ) // 9139
}

fun solveDay02Part2() {
    println(
        getLinesFromFile("day_02/boxes.txt")
            .findCommonLetters()
    ) // uqcidadzwtnhsljvxyobmkfyr
}

fun solveDay03Part1() {
    println(
        getLinesFromFile("day_03/claims.txt")
            .countOverlapingPositions()
    ) // 107663
}

fun solveDay03Part2() {
    println(
        getLinesFromFile("day_03/claims.txt")
            .getIntactClaimId()
    ) // 1166
}

fun solveDay04Part1() {
    println(
        getLinesFromFile("day_04/shifts.log")
            .getChecksumForStrategy1()
    ) // 101262
}

fun solveDay04Part2() {
    println(
        getLinesFromFile("day_04/shifts.log")
            .getChecksumForStrategy2()
    ) // 71976
}

fun solveDay05Part1() {
    println(
        Polymer(getLinesFromFile("day_05/polymer.struct")[0])
            .reactFully().size
    ) // 10878
}

fun solveDay05Part2() {
    println(
        Polymer(getLinesFromFile("day_05/polymer.struct")[0])
            .findShortestPolymerLength()
    ) // 71976
}

fun solveDay06Part1And2() {
    val grid = Grid(getLinesFromFile("day_06/input").map {
        val (x, y) = it.split(", ")
        Point(x.toInt(), y.toInt())
    })
    println(grid.largestAreaSize) // 5532
    println(grid.getSafestAreaSizeFor(10000)) // 36216
}


fun solveDay07Part1() {
    println(
        getLinesFromFile("day_07/steps")
            .toStepSequence(0, 1)
            .sequence
    ) // GNJOCHKSWTFMXLYDZABIREPVUQ
}

fun solveDay07Part2() {
    println(
        getLinesFromFile("day_07/steps")
            .toStepSequence(60, 5)
            .numberOfSteps
    ) // 886
}

fun solveDay08Part1() {
    val input = getListOfIntsFromFile("day_08/input")
    println(MyTreeNode.decodeMeta(input).totalMetadataSum)
}

fun solveDay08Part2() {
    val input = getListOfIntsFromFile("day_08/input")
    println(MyTreeNode.parseNode(input).node.value)
}

fun solveDay10Part1And2() {
    val particles = Particle.loadList(getLinesFromFile("day_10/particles"))

    val (finalParticles, steps) = particles.getStateWithMessage()

    println(finalParticles.draw()) // GFNKCGGH
    println(steps) // 10274
}

private fun getListOfIntsFromFile(filename: String) =
    getLinesFromFile(filename)
        .first()
        .split(" ")
        .map { it.toInt() }

private fun getLinesFromFile(filename: String) =
    File("src/main/resources/$filename").readLines()

