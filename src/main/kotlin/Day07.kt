import Status.CLOSED
import Status.DONE
import Status.PENDING
import Status.WAITING

typealias ListOfDependencies = List<Pair<String, String>>

enum class Status { WAITING, PENDING, DONE, CLOSED }

data class Task(
    val id: String,
    val requirements: List<String> = listOf(),
    private val initialStatus: Status = WAITING,
    private val offset: Int = 0
) {

    private val stepsNeededForCompletion = offset + id.first().toInt() - 'A'.toInt()

    private var completionStep = 0

    var status = initialStatus
        private set

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
}

private fun List<Task>.allCompleted() = all { it.completed }

private fun ListOfDependencies.toRequirements(taskDurationOffset: Int): List<Task> {
    val unzipped = unzip()
    val requirements = unzipped.first.toSet()
    val unlockedSteps = unzipped.second.toSet()
    val allLetters = requirements + unlockedSteps

    return allLetters
        .map { Task(id = it, requirements = getDependenciesFor(it), offset = taskDurationOffset) }
}

private fun ListOfDependencies.getDependenciesFor(letter: String) =
    filter { (_, value) -> value == letter }
        .map { (required, _) -> required }

private fun List<Task>.nextStep(numberOfWorkers: Int) {
    getAvailableTasks()
        .sortedBy { it.id }
        .take(numberOfWorkers - getPendingTasks().size)
        .forEach { it.start() }

    forEach { it.advance() }
}

fun List<Task>.getAvailableTasks() =
    filter { it.status == WAITING && allRequirementsCompletedFor(it) }

private fun List<Task>.allRequirementsCompletedFor(task: Task): Boolean {
    return task.requirements
        .map { taskById(it) }
        .allCompleted()
}

private fun List<Task>.taskById(taskId: String) = this.first { it.id == taskId }

private fun List<Task>.getFinishedTasksIds() =
    filter { it.status == DONE }
        .joinToString(separator = "") { it.id }

private fun List<Task>.getPendingTasks() = filter { it.status == PENDING }

fun List<String>.toStepSequence(
    taskDurationOffset: Int = 0,
    numberOfWorkers: Int
): StepSequence {
    val regexForDependency = Regex("Step (\\w) must be finished before step (\\w) can begin.")

    val requirements = mapNotNull { line -> regexForDependency.find(line) }
        .map {
            val (requirement, unlockedStep) = it.destructured
            requirement to unlockedStep
        }
        .toRequirements(taskDurationOffset)

    return buildStepSequence(requirements, StepSequence(0, ""), numberOfWorkers)
}

data class StepSequence(val numberOfSteps: Int, val sequence: String)

private tailrec fun buildStepSequence(
    tasks: List<Task>,
    stepSequence: StepSequence,
    numberOfWorkers: Int
): StepSequence {
    return if (tasks.allCompleted()) stepSequence
    else {
        tasks.nextStep(numberOfWorkers)

        buildStepSequence(
            tasks,
            StepSequence(stepSequence.numberOfSteps + 1, stepSequence.sequence + tasks.getFinishedTasksIds()),
            numberOfWorkers
        )
    }
}
