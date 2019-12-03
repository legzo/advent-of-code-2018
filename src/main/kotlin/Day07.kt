typealias Requirements = Map<String, Set<String>>
typealias ListOfDependencies = List<Pair<String, String>>

data class Step(val stepLetter: String, val newRequirements: Requirements)

val regexForDependency = Regex("Step (\\w) must be finished before step (\\w) can begin.")

fun ListOfDependencies.toRequirements(): Requirements {
    val unzipped = unzip()
    val requirements = unzipped.first.toSet()
    val unlockedSteps = unzipped.second.toSet()
    val allLetters = requirements + unlockedSteps

    return allLetters
        .map { it to getDependenciesFor(it) }
        .toMap()
}

private fun ListOfDependencies.getDependenciesFor(letter: String) =
    filter { (_, value) -> value == letter }
        .toMap()
        .keys

private fun Requirements.removeRequirement(requirement: Char) =
    mapValues { (_, value) -> value.minus(requirement.toString()) }

fun Requirements.getNextStep(unlocked: Char?): Step {
    val newRequirements = when (unlocked) {
        null -> this
        else -> removeRequirement(unlocked)
    }

    val nextLetter = newRequirements
        .filter { (_, value) -> value.isEmpty() }
        .keys
        .min() // alpha order
        ?: throw IllegalStateException("Oh no!")

    return Step(nextLetter, newRequirements.minus(nextLetter))
}

fun List<String>.toSteps() =
    mapNotNull { line -> regexForDependency.find(line) }
        .map {
            val (requirement, unlockedStep) = it.destructured
            requirement to unlockedStep
        }
        .toStepSequence()

fun ListOfDependencies.toStepSequence() =
    buildStepSequence(requirements = this.toRequirements())

tailrec fun buildStepSequence(sequence: String = "", requirements: Requirements): String {
    return if (requirements.isEmpty()) sequence
    else {
        val justUnlocked = sequence.lastOrNull()
        val (nextStep, newRequirements) = requirements.getNextStep(justUnlocked)
        buildStepSequence(sequence + nextStep, newRequirements)
    }
}
