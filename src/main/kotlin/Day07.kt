import Status.CLOSED
import Status.DONE
import Status.PENDING
import Status.WAITING

typealias Requirements = Map<String, Task>
typealias ListOfDependencies = List<Pair<String, String>>

data class Step(val stepLetter: String, val newRequirements: Requirements)

enum class Status { WAITING, PENDING, DONE, CLOSED }

data class Task(
    val id: String,
    val requirements: MutableList<Task> = mutableListOf(),
    var completionStep: Int = 0,
    var status: Status = WAITING,
    val offset: Int = 0
) {
    private val stepsNeededForCompletion = offset + id.first().toInt() - 'A'.toInt()

    val completed
        get() = status in listOf(DONE, CLOSED)

    fun start() {
        status = PENDING
    }

    fun advance() {
        if (status == DONE) {
            status = CLOSED
        }
        if (status == PENDING) {
            completionStep++
        }
        if (status == PENDING && completionStep > stepsNeededForCompletion) {
            status = DONE
        }
    }

    companion object {
        private val tasks: MutableMap<String, Task> = mutableMapOf()

        fun create(id: String, durationOffset: Int): Task {
            val newTask = Task(id, offset = durationOffset)
            tasks[id] = newTask
            return newTask
        }

        fun forId(id: String) = tasks[id]
    }
}

fun Requirements.allCompleted() = values.all { it.completed }

val regexForDependency = Regex("Step (\\w) must be finished before step (\\w) can begin.")

fun ListOfDependencies.toRequirements(taskDurationOffset: Int): Requirements {
    val unzipped = unzip()
    val requirements = unzipped.first.toSet()
    val unlockedSteps = unzipped.second.toSet()
    val allLetters = requirements + unlockedSteps

    val tasksWithoutDependencies = allLetters
        .map { Task.create(it, taskDurationOffset) }

    tasksWithoutDependencies
        .forEach { task ->
            task.requirements += getDependenciesFor(task.id)
        }

    return tasksWithoutDependencies
        .map { task -> task.id to task }
        .toMap()
}

private fun ListOfDependencies.getDependenciesFor(
    letter: String
): List<Task> {
    return filter { (_, value) -> value == letter }
        .mapNotNull { (required, _) ->
            Task.forId(required)
        }
}

fun Requirements.nextStep(numberOfWorkers: Int) {
    getAvailableTasks()
        .sortedBy { it.id }
        .take(numberOfWorkers - getPendingTasks().size)
        .forEach { it.start() }

    values
        .forEach { it.advance() }
}

fun Requirements.getAvailableTasks(): List<Task> {
    return values
        .filter {
            it.requirements.all { req -> req.completed }
                    && it.status == WAITING
        }
}

fun Requirements.getFinishedTasksIds() =
    values
        .filter { it.status == DONE }
        .joinToString(separator = "") { it.id }

fun Requirements.getPendingTasks() = values.filter { it.status == PENDING }

fun List<String>.toStepSequence(
    taskDurationOffset: Int = 0,
    numberOfWorkers: Int
): StepSequence {
    val requirements = mapNotNull { line -> regexForDependency.find(line) }
        .map {
            val (requirement, unlockedStep) = it.destructured
            requirement to unlockedStep
        }
        .toRequirements(taskDurationOffset)

    return buildStepSequence(requirements, StepSequence(0, ""), numberOfWorkers)
}

data class StepSequence(val numberOfSteps: Int, val sequence: String)

tailrec fun buildStepSequence(
    requirements: Requirements,
    stepSequence: StepSequence,
    numberOfWorkers: Int
): StepSequence {
    return if (requirements.allCompleted()) stepSequence
    else {
        requirements.nextStep(numberOfWorkers)

        buildStepSequence(
            requirements,
            StepSequence(stepSequence.numberOfSteps + 1, stepSequence.sequence + requirements.getFinishedTasksIds()),
            numberOfWorkers
        )
    }
}
